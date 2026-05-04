package br.com.fiap.ClyvoPet.appointment.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AppointmentRequest {
    @NotNull(message = "Data da consulta é obrigatória")
    @Future(message = "Data da consulta deve ser futura")
    private LocalDate time;
    private String obs;
    @NotNull(message = "ID do pet é obrigatório") private Long petId;
}
