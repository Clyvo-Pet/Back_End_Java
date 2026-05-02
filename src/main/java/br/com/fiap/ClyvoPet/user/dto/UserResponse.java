package br.com.fiap.ClyvoPet.user.dto;

import br.com.fiap.ClyvoPet.user.model.User;

import java.time.LocalDate;

public record UserResponse(
        Long id,
        String name,
        String email,
        String telefone,
        LocalDate createdDate
) {
    public static UserResponse fromEntity(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getTelephone(),
                user.getCreatedDate()
        );
    }
}
