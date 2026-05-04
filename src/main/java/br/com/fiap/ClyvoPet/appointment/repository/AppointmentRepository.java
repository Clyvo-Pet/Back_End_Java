package br.com.fiap.ClyvoPet.appointment.repository;

import br.com.fiap.ClyvoPet.appointment.model.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPetId(Long petId);
    Page<Appointment> findByStatus(String status, Pageable pageable);
    boolean existsByPetIdAndTime(Long petId, LocalDate time);
}
