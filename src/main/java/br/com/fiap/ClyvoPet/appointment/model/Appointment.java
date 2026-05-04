package br.com.fiap.ClyvoPet.appointment.model;

import br.com.fiap.ClyvoPet.pet.model.Pet;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "QUERIES")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appt_seq")
    @SequenceGenerator(name = "appt_seq", sequenceName = "appt_seq", allocationSize = 1)
    private Long id;

    @Column(name = "time")
    private LocalDate time;

    @NotBlank(message = "Status é obrigatório") @Size(max = 20)
    @Column(nullable = false, length = 20)
    private String status;

    @Size(max = 200)
    @Column(length = 200)
    private String obs;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    @PrePersist
    public void prePersist() { this.status = "Agendado"; }
}
