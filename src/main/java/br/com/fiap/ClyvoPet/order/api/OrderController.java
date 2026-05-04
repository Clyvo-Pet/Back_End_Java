package br.com.fiap.ClyvoPet.order.api;

import br.com.fiap.ClyvoPet.order.dto.OrderRequest;
import br.com.fiap.ClyvoPet.order.dto.OrderResponse;
import br.com.fiap.ClyvoPet.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Pedidos", description = "Pedidos com desconto automático por assinatura ativa")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "Criar pedido — aplica desconto automático se usuário tem assinatura ativa")
    public ResponseEntity<OrderResponse> create(@Valid @RequestBody OrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.create(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pedido por ID")
    public ResponseEntity<OrderResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Listar pedidos de um usuário")
    public ResponseEntity<List<OrderResponse>> findByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.findByUserId(userId));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Listar pedidos por status com paginação")
    public ResponseEntity<Page<OrderResponse>> findByStatus(@PathVariable String status,
            @PageableDefault(size = 10, sort = "crateDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(orderService.findByStatus(status, pageable));
    }
}
