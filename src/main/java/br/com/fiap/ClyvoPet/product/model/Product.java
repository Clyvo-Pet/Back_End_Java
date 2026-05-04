package br.com.fiap.ClyvoPet.product.model;

import br.com.fiap.ClyvoPet.itemOrder.model.ItemOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "PRODUCT")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(name = "product_seq", sequenceName = "product_seq", allocationSize = 1)
    private Long id;

    @NotBlank @Size(max = 80)
    @Column(nullable = false, length = 80)
    private String name;

    @NotBlank @Size(max = 150)
    @Column(nullable = false, length = 150)
    private String description;

    @NotBlank @Size(max = 30)
    @Column(nullable = false, length = 30)
    private String category;

    @NotBlank @Size(max = 30)
    @Column(name = "target_species", nullable = false, length = 30)
    private String targetSpecies;

    @NotNull @Positive
    @Column(nullable = false)
    private Double price;

    @Size(max = 180)
    @Column(name = "img_url", length = 180)
    private String imgUrl;

    @NotBlank
    @Column(nullable = false, length = 1)
    private String active;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ItemOrder> itemOrders;
}
