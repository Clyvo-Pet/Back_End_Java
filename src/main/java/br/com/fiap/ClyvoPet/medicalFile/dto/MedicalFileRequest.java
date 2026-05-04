package br.com.fiap.ClyvoPet.medicalFile.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MedicalFileRequest {
    @NotBlank @Size(max = 80) private String allergies;
    @NotBlank @Size(max = 80) private String chronicDiseases;
    @NotBlank @Size(max = 80) private String medicines;
    @NotNull private LocalDate lastVaccine;
    @NotNull private LocalDate nextVaccine;
    private String obs;
    @NotNull(message = "ID do pet é obrigatório") private Long petId;
}
