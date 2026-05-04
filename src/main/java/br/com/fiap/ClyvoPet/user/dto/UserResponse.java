package br.com.fiap.ClyvoPet.user.dto;

import br.com.fiap.ClyvoPet.user.model.User;
import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String telephone;
    private LocalDate createDate;

    public static UserResponse from(User u) {
        return UserResponse.builder()
                .id(u.getId()).name(u.getName())
                .email(u.getEmail()).telephone(u.getTelephone())
                .createDate(u.getCreateDate()).build();
    }
}
