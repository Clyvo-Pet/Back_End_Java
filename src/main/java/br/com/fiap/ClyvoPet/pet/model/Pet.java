package br.com.fiap.ClyvoPet.pet.model;

import br.com.fiap.ClyvoPet.appointment.model.Appointment;
import br.com.fiap.ClyvoPet.medicalFile.model.MedicalFile;
import br.com.fiap.ClyvoPet.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "PET")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pet_seq")
    @SequenceGenerator(name = "pet_seq", sequenceName = "pet_seq", allocationSize = 1)
    private Long id;

    @NotBlank(message = "Nome é obrigatório") @Size(max = 20)
    @Column(nullable = false, length = 20)
    private String name;

    @NotBlank(message = "Espécie é obrigatória") @Size(max = 20)
    @Column(nullable = false, length = 20)
    private String species;

    @NotBlank(message = "Raça é obrigatória") @Size(max = 20)
    @Column(nullable = false, length = 20)
    private String race;

    @NotNull(message = "Data de nascimento é obrigatória")
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @NotNull(message = "Peso é obrigatório") @Positive
    @Column(nullable = false)
    private Double weight;

    @Size(max = 120)
    @Column(name = "photo_url", length = 120)
    private String photoUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(mappedBy = "pet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private MedicalFile medicalFile;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Appointment> appointments;
}
