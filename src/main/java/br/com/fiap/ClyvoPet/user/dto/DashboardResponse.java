package br.com.fiap.ClyvoPet.user.dto;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DashboardResponse {
    private String ownerName;
    private String ownerEmail;
    private int totalPets;
    private int totalOrders;
    private Double totalSpent;
    private List<PetSummary> pets;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class PetSummary {
        private String petName;
        private String species;
        private Integer ageYears;
        private String allergies;
        private String medicines;
        private LocalDate nextVaccine;
        private boolean vaccineAlertIn30Days;
        private int totalAppointments;
        private String lastAppointmentStatus;
    }
}
