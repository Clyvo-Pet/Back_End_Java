package br.com.fiap.ClyvoPet.user.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UserRequest {
    @NotBlank(message = "Nome é obrigatório") @Size(max = 80)
    private String name;
    @NotBlank @Email(message = "Email inválido") @Size(max = 100)
    private String email;
    @NotBlank @Size(max = 13)
    private String telephone;
    @NotBlank @Size(min = 6, max = 20, message = "Senha deve ter entre 6 e 20 caracteres")
    private String password;
}
