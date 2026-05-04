package br.com.fiap.ClyvoPet.order.dto;

import br.com.fiap.ClyvoPet.order.model.Order;
import lombok.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderResponse {
    private Long id;
    private String status;
    private Double totalPrice;
    private Double discountApplied;
    private String deliveryAddress;
    private LocalDate crateDate;
    private String userName;
    private List<ItemResponse> items;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class ItemResponse {
        private Long productId;
        private String productName;
        private Integer quantity;
        private Double unitPrice;
        private Double subtotal;
    }

    public static OrderResponse from(Order o) {
        List<ItemResponse> items = o.getItems().stream().map(i ->
            ItemResponse.builder()
                .productId(i.getProduct().getId())
                .productName(i.getProduct().getName())
                .quantity(i.getQuantity())
                .unitPrice(i.getUnitPrice())
                .subtotal(i.getUnitPrice() * i.getQuantity())
                .build()
        ).collect(Collectors.toList());

        return OrderResponse.builder()
                .id(o.getId()).status(o.getStatus())
                .totalPrice(o.getTotalPrice()).discountApplied(o.getDiscountApplied())
                .deliveryAddress(o.getDeliveryAddress()).crateDate(o.getCrateDate())
                .userName(o.getUser().getName()).items(items).build();
    }
}
