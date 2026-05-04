package br.com.fiap.ClyvoPet.appointment.service;

import br.com.fiap.ClyvoPet.appointment.dto.AppointmentRequest;
import br.com.fiap.ClyvoPet.appointment.dto.AppointmentResponse;
import br.com.fiap.ClyvoPet.appointment.model.Appointment;
import br.com.fiap.ClyvoPet.appointment.repository.AppointmentRepository;
import br.com.fiap.ClyvoPet.exception.BusinessException;
import br.com.fiap.ClyvoPet.exception.ResourceNotFoundException;
import br.com.fiap.ClyvoPet.pet.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PetService petService;

    @Transactional
    public AppointmentResponse schedule(AppointmentRequest request) {
        var pet = petService.findOrThrow(request.getPetId());
        if (appointmentRepository.existsByPetIdAndTime(request.getPetId(), request.getTime()))
            throw new BusinessException("Já existe uma consulta para este pet nesta data.");
        Appointment appt = Appointment.builder()
                .pet(pet).time(request.getTime()).obs(request.getObs()).build();
        return AppointmentResponse.from(appointmentRepository.save(appt));
    }

    public AppointmentResponse findById(Long id) {
        return AppointmentResponse.from(findOrThrow(id));
    }

    public List<AppointmentResponse> findByPetId(Long petId) {
        petService.findOrThrow(petId);
        return appointmentRepository.findByPetId(petId).stream()
                .map(AppointmentResponse::from).collect(Collectors.toList());
    }

    public Page<AppointmentResponse> findByStatus(String status, Pageable pageable) {
        return appointmentRepository.findByStatus(status, pageable).map(AppointmentResponse::from);
    }

    @Transactional
    public AppointmentResponse updateStatus(Long id, String status) {
        Appointment appt = findOrThrow(id);
        List<String> valid = List.of("Agendado", "Realizado", "Cancelado");
        if (!valid.contains(status))
            throw new BusinessException("Status inválido. Use: Agendado, Realizado ou Cancelado");
        if ("Cancelado".equals(appt.getStatus()))
            throw new BusinessException("Consulta cancelada não pode ser atualizada.");
        appt.setStatus(status);
        return AppointmentResponse.from(appointmentRepository.save(appt));
    }

    @Transactional
    public void cancel(Long id) {
        Appointment appt = findOrThrow(id);
        if ("Cancelado".equals(appt.getStatus()))
            throw new BusinessException("Consulta já está cancelada.");
        appt.setStatus("Cancelado");
        appointmentRepository.save(appt);
    }

    public Appointment findOrThrow(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consulta não encontrada: " + id));
    }
}
