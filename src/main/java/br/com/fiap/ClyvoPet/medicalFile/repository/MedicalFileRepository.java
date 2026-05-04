package br.com.fiap.ClyvoPet.medicalFile.repository;

import br.com.fiap.ClyvoPet.medicalFile.model.MedicalFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MedicalFileRepository extends JpaRepository<MedicalFile, Long> {
    Optional<MedicalFile> findByPetId(Long petId);
    boolean existsByPetId(Long petId);
}
