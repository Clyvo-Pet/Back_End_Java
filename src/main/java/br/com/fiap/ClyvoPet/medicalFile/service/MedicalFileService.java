package br.com.fiap.ClyvoPet.medicalFile.service;

import br.com.fiap.ClyvoPet.exception.BusinessException;
import br.com.fiap.ClyvoPet.exception.ResourceNotFoundException;
import br.com.fiap.ClyvoPet.medicalFile.dto.MedicalFileRequest;
import br.com.fiap.ClyvoPet.medicalFile.model.MedicalFile;
import br.com.fiap.ClyvoPet.medicalFile.repository.MedicalFileRepository;
import br.com.fiap.ClyvoPet.pet.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MedicalFileService {

    private final MedicalFileRepository medicalFileRepository;
    private final PetService petService;

    @Transactional
    public MedicalFile create(MedicalFileRequest request) {
        if (medicalFileRepository.existsByPetId(request.getPetId()))
            throw new BusinessException("Este pet já possui uma ficha médica.");
        var pet = petService.findOrThrow(request.getPetId());
        MedicalFile mf = MedicalFile.builder()
                .allergies(request.getAllergies()).chronicDiseases(request.getChronicDiseases())
                .medicines(request.getMedicines()).lastVaccine(request.getLastVaccine())
                .nextVaccine(request.getNextVaccine()).obs(request.getObs()).pet(pet).build();
        return medicalFileRepository.save(mf);
    }

    public MedicalFile findByPetId(Long petId) {
        return medicalFileRepository.findByPetId(petId)
                .orElseThrow(() -> new ResourceNotFoundException("Ficha médica não encontrada para o pet: " + petId));
    }

    public MedicalFile findById(Long id) {
        return medicalFileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ficha médica não encontrada: " + id));
    }

    @Transactional
    public MedicalFile update(Long id, MedicalFileRequest request) {
        MedicalFile mf = findById(id);
        mf.setAllergies(request.getAllergies()); mf.setChronicDiseases(request.getChronicDiseases());
        mf.setMedicines(request.getMedicines()); mf.setLastVaccine(request.getLastVaccine());
        mf.setNextVaccine(request.getNextVaccine()); mf.setObs(request.getObs());
        return medicalFileRepository.save(mf);
    }

    @Transactional
    public void delete(Long id) {
        findById(id);
        medicalFileRepository.deleteById(id);
    }
}
