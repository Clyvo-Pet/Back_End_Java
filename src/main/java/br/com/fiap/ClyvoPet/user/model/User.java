package br.com.fiap.ClyvoPet.user.model;

import br.com.fiap.ClyvoPet.order.model.Order;
import br.com.fiap.ClyvoPet.pet.model.Pet;
import br.com.fiap.ClyvoPet.signature.model.Signature;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "\"USER\"")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 80)
    @Column(nullable = false, length = 80)
    private String name;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    @Size(max = 100)
    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @NotBlank(message = "Telefone é obrigatório")
    @Size(max = 13)
    @Column(nullable = false, length = 13)
    private String telephone;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, max = 20, message = "Senha deve ter entre 6 e 20 caracteres")
    @Column(nullable = false, length = 20)
    private String password;

    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Pet> pets;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Signature> signatures;

    @PrePersist
    public void prePersist() {
        this.createDate = LocalDate.now();
    }
}
