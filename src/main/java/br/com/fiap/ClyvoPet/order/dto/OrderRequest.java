package br.com.fiap.ClyvoPet.order.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class OrderRequest {
    @NotBlank(message = "Endereço de entrega é obrigatório")
    private String deliveryAddress;
    @NotNull(message = "ID do usuário é obrigatório")
    private Long userId;
    @NotEmpty(message = "Pedido deve ter ao menos um item")
    private List<ItemRequest> items;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class ItemRequest {
        @NotNull private Long productId;
        @NotNull @Min(1) private Integer quantity;
    }
}
