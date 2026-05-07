package br.com.fiap.ClyvoPet.plan.model;

import br.com.fiap.ClyvoPet.signature.model.Signature;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "PLAN_DATA")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plan_seq")
    @SequenceGenerator(name = "plan_seq", sequenceName = "plan_seq", allocationSize = 1)
    private Long id;

    @NotBlank(message = "Nome do plano é obrigatório") @Size(max = 30)
    @Column(nullable = false, length = 30)
    private String name;

    @NotNull @Positive
    @Column(name = "monthly_price", nullable = false)
    private Double monthlyPrice;

    @Column(name = "consultations_month")
    private Integer consultationsMonth;

    @Column(name = "mkt_discount")
    private Double mktDiscount;

    @NotBlank @Size(max = 150)
    @Column(nullable = false, length = 150)
    private String benefits;

    @NotBlank
    @Column(nullable = false, length = 1)
    private String active;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "signature_id", nullable = false, unique = true)
    private Signature signature;
}
