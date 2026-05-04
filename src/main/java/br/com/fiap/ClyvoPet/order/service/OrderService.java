package br.com.fiap.ClyvoPet.order.service;

import br.com.fiap.ClyvoPet.exception.BusinessException;
import br.com.fiap.ClyvoPet.exception.ResourceNotFoundException;
import br.com.fiap.ClyvoPet.itemOrder.model.ItemOrder;
import br.com.fiap.ClyvoPet.order.dto.OrderRequest;
import br.com.fiap.ClyvoPet.order.dto.OrderResponse;
import br.com.fiap.ClyvoPet.order.model.Order;
import br.com.fiap.ClyvoPet.order.repository.OrderRepository;
import br.com.fiap.ClyvoPet.product.service.ProductService;
import br.com.fiap.ClyvoPet.signature.service.SignatureService;
import br.com.fiap.ClyvoPet.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductService productService;
    private final SignatureService signatureService;

    @Transactional
    public OrderResponse create(OrderRequest request) {
        var user = userService.findOrThrow(request.getUserId());

        // Verifica desconto por assinatura ativa
        double discountPct = signatureService.findActiveByUserId(user.getId())
                .filter(s -> s.getPlan() != null && s.getPlan().getMktDiscount() != null)
                .map(s -> s.getPlan().getMktDiscount())
                .orElse(0.0);

        Order order = Order.builder()
                .user(user).deliveryAddress(request.getDeliveryAddress()).build();

        List<ItemOrder> items = new ArrayList<>();
        double total = 0.0;

        for (OrderRequest.ItemRequest itemReq : request.getItems()) {
            var product = productService.findOrThrow(itemReq.getProductId());
            if (!"S".equals(product.getActive()))
                throw new BusinessException("Produto inativo: " + product.getName());

            double unitPrice = product.getPrice() - (product.getPrice() * discountPct / 100);
            ItemOrder item = ItemOrder.builder()
                    .order(order).product(product)
                    .quantity(itemReq.getQuantity()).unitPrice(unitPrice).build();
            items.add(item);
            total += unitPrice * itemReq.getQuantity();
        }

        order.setItems(items);
        order.setTotalPrice(total);
        order.setDiscountApplied(discountPct);
        return OrderResponse.from(orderRepository.save(order));
    }

    public OrderResponse findById(Long id) {
        return OrderResponse.from(orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado: " + id)));
    }

    public List<OrderResponse> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(OrderResponse::from).collect(Collectors.toList());
    }

    public Page<OrderResponse> findByStatus(String status, Pageable pageable) {
        return orderRepository.findByStatus(status, pageable).map(OrderResponse::from);
    }
}
