package br.com.fiap.ClyvoPet.appointment.api;

import br.com.fiap.ClyvoPet.appointment.dto.AppointmentRequest;
import br.com.fiap.ClyvoPet.appointment.dto.AppointmentResponse;
import br.com.fiap.ClyvoPet.appointment.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
@Tag(name = "Consultas", description = "Agendamento inteligente de consultas veterinárias")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    @Operation(summary = "Agendar consulta — valida conflitos de data")
    public ResponseEntity<AppointmentResponse> schedule(@Valid @RequestBody AppointmentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.schedule(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar consulta por ID")
    public ResponseEntity<AppointmentResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.findById(id));
    }

    @GetMapping("/pet/{petId}")
    @Operation(summary = "Listar consultas de um pet")
    public ResponseEntity<List<AppointmentResponse>> findByPet(@PathVariable Long petId) {
        return ResponseEntity.ok(appointmentService.findByPetId(petId));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Listar consultas por status com paginação")
    public ResponseEntity<Page<AppointmentResponse>> findByStatus(@PathVariable String status,
            @PageableDefault(size = 10, sort = "time", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(appointmentService.findByStatus(status, pageable));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status da consulta")
    public ResponseEntity<AppointmentResponse> updateStatus(@PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(appointmentService.updateStatus(id, status));
    }

    @DeleteMapping("/{id}/cancel")
    @Operation(summary = "Cancelar consulta")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        appointmentService.cancel(id);
        return ResponseEntity.noContent().build();
    }
}
