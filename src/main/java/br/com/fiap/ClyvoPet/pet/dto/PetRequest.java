package br.com.fiap.ClyvoPet.pet.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PetRequest {
    @NotBlank @Size(max = 20) private String name;
    @NotBlank @Size(max = 20) private String species;
    @NotBlank @Size(max = 20) private String race;
    @NotNull private LocalDate birthDate;
    @NotNull @Positive private Double weight;
    private String photoUrl;
    @NotNull(message = "ID do usuário é obrigatório") private Long userId;
}
