package br.com.fiap.ClyvoPet.plan.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PlanRequest {
    @NotBlank @Size(max = 30) private String name;
    @NotNull @Positive private Double monthlyPrice;
    private Integer consultationsMonth;
    private Double mktDiscount;
    @NotBlank @Size(max = 150) private String benefits;
    @NotBlank private String active;
    @NotNull(message = "ID da assinatura é obrigatório") private Long signatureId;
}
