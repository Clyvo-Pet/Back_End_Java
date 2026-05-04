package br.com.fiap.ClyvoPet.pet.dto;

import br.com.fiap.ClyvoPet.pet.model.Pet;
import lombok.*;
import java.time.LocalDate;
import java.time.Period;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PetResponse {
    private Long id;
    private String name;
    private String species;
    private String race;
    private LocalDate birthDate;
    private Integer ageYears;
    private Double weight;
    private String photoUrl;
    private Long userId;
    private String ownerName;

    public static PetResponse from(Pet p) {
        return PetResponse.builder()
                .id(p.getId()).name(p.getName()).species(p.getSpecies())
                .race(p.getRace()).birthDate(p.getBirthDate())
                .ageYears(Period.between(p.getBirthDate(), LocalDate.now()).getYears())
                .weight(p.getWeight()).photoUrl(p.getPhotoUrl())
                .userId(p.getUser().getId()).ownerName(p.getUser().getName())
                .build();
    }
}
