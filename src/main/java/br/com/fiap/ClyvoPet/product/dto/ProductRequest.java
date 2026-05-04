package br.com.fiap.ClyvoPet.product.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ProductRequest {
    @NotBlank @Size(max = 80) private String name;
    @NotBlank @Size(max = 150) private String description;
    @NotBlank @Size(max = 30) private String category;
    @NotBlank @Size(max = 30) private String targetSpecies;
    @NotNull @Positive private Double price;
    private String imgUrl;
    @NotBlank private String active;
}
