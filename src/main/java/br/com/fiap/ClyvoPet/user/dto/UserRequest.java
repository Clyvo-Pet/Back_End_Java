package br.com.fiap.ClyvoPet.user.dto;

import br.com.fiap.ClyvoPet.user.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @NotBlank(message = "Nome é obrigatório!")
        @Size(min = 3, max = 80, message = "Nome deve ter entre 3 e 80 caracteres")
        String name,

        @NotBlank(message = "Email é obrigatório!")
        @Email(message = "Email inválido")
        String email,

        @NotBlank(message = "Telefone é obrigatório!")
        @Pattern(
                regexp = "^\\d{10,11}$",
                message = "Telefone deve conter DDD + número (10 ou 11 dígitos!)"
        )
        String telephone,

        @NotBlank(message = "Senha é obrigatória!")
        @Size(min = 6, max = 20, message = "Senha deve conter entre 6 e 20 caracteres!")
        String password
) {
    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .telephone(telephone)
                .password(password)
                .build();
    }

}
