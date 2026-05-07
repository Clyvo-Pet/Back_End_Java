package br.com.fiap.ClyvoPet.itemOrder.model;

import br.com.fiap.ClyvoPet.order.model.Order;
import br.com.fiap.ClyvoPet.product.model.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "ITEM_ORDER")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ItemOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_seq")
    @SequenceGenerator(name = "item_seq", sequenceName = "item_seq", allocationSize = 1)
    private Long id;

    @NotNull @Min(1)
    @Column(nullable = false)
    private Integer quantity;

    @NotNull @Positive
    @Column(name = "unit_price", nullable = false)
    private Double unitPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
