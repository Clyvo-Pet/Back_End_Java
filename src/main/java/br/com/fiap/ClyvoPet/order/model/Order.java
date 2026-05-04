package br.com.fiap.ClyvoPet.order.model;

import br.com.fiap.ClyvoPet.itemOrder.model.ItemOrder;
import br.com.fiap.ClyvoPet.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "\"ORDER\"")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
    @SequenceGenerator(name = "order_seq", sequenceName = "order_seq", allocationSize = 1)
    private Long id;

    @NotBlank @Size(max = 30)
    @Column(nullable = false, length = 30)
    private String status;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @Column(name = "discount_applied")
    private Double discountApplied;

    @NotBlank @Size(max = 120)
    @Column(name = "delivery_address", nullable = false, length = 120)
    private String deliveryAddress;

    @Column(name = "crate_date", nullable = false)
    private LocalDate crateDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ItemOrder> items;

    @PrePersist
    public void prePersist() {
        this.crateDate = LocalDate.now();
        this.status = "Pendente";
    }
}
