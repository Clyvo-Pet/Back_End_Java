package br.com.fiap.ClyvoPet.appointment.dto;

import br.com.fiap.ClyvoPet.appointment.model.Appointment;
import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AppointmentResponse {
    private Long id;
    private LocalDate time;
    private String status;
    private String obs;
    private Long petId;
    private String petName;
    private String ownerName;

    public static AppointmentResponse from(Appointment a) {
        return AppointmentResponse.builder()
                .id(a.getId()).time(a.getTime()).status(a.getStatus()).obs(a.getObs())
                .petId(a.getPet().getId()).petName(a.getPet().getName())
                .ownerName(a.getPet().getUser().getName()).build();
    }
}
