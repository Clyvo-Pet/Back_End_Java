package br.com.fiap.ClyvoPet.medicalFile.model;

import br.com.fiap.ClyvoPet.pet.model.Pet;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "MEDICAL_FILE")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MedicalFile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mf_seq")
    @SequenceGenerator(name = "mf_seq", sequenceName = "mf_seq", allocationSize = 1)
    private Long id;

    @NotBlank(message = "Alergias é obrigatório") @Size(max = 80)
    @Column(nullable = false, length = 80)
    private String allergies;

    @NotBlank(message = "Doenças crônicas é obrigatório") @Size(max = 80)
    @Column(name = "chronic_diseases", nullable = false, length = 80)
    private String chronicDiseases;

    @NotBlank(message = "Medicamentos é obrigatório") @Size(max = 80)
    @Column(nullable = false, length = 80)
    private String medicines;

    @NotNull(message = "Última vacina é obrigatória")
    @Column(name = "last_vaccine", nullable = false)
    private LocalDate lastVaccine;

    @NotNull(message = "Próxima vacina é obrigatória")
    @Column(name = "next_vaccine", nullable = false)
    private LocalDate nextVaccine;

    @Size(max = 200)
    @Column(length = 200)
    private String obs;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false, unique = true)
    private Pet pet;
}
