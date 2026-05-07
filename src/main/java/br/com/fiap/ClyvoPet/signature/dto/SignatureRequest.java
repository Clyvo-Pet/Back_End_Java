package br.com.fiap.ClyvoPet.signature.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SignatureRequest {
    @NotNull private LocalDate startDate;
    @NotNull private LocalDate endDate;
    @NotNull(message = "ID do usuário é obrigatório") private Long userId;
}
