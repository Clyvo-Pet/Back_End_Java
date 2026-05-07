package br.com.fiap.ClyvoPet.signature.model;

import br.com.fiap.ClyvoPet.plan.model.Plan;
import br.com.fiap.ClyvoPet.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "SIGNATURE")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Signature {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sig_seq")
    @SequenceGenerator(name = "sig_seq", sequenceName = "sig_seq", allocationSize = 1)
    private Long id;

    @NotBlank @Size(max = 30)
    @Column(nullable = false, length = 30)
    private String status;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(mappedBy = "signature", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Plan plan;
}
