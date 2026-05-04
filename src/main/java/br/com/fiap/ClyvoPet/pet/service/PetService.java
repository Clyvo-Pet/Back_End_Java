package br.com.fiap.ClyvoPet.pet.service;

import br.com.fiap.ClyvoPet.exception.ResourceNotFoundException;
import br.com.fiap.ClyvoPet.pet.dto.PetRequest;
import br.com.fiap.ClyvoPet.pet.dto.PetResponse;
import br.com.fiap.ClyvoPet.pet.model.Pet;
import br.com.fiap.ClyvoPet.pet.repository.PetRepository;
import br.com.fiap.ClyvoPet.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final UserService userService;

    @Transactional
    public PetResponse create(PetRequest request) {
        var user = userService.findOrThrow(request.getUserId());
        Pet pet = Pet.builder()
                .name(request.getName()).species(request.getSpecies())
                .race(request.getRace()).birthDate(request.getBirthDate())
                .weight(request.getWeight()).photoUrl(request.getPhotoUrl())
                .user(user).build();
        return PetResponse.from(petRepository.save(pet));
    }

    public PetResponse findById(Long id) {
        return PetResponse.from(findOrThrow(id));
    }

    public Page<PetResponse> findAll(Pageable pageable) {
        return petRepository.findAll(pageable).map(PetResponse::from);
    }

    public List<PetResponse> findByUserId(Long userId) {
        return petRepository.findByUserId(userId).stream().map(PetResponse::from).collect(Collectors.toList());
    }

    public Page<PetResponse> findBySpecies(String species, Pageable pageable) {
        return petRepository.findBySpeciesIgnoreCase(species, pageable).map(PetResponse::from);
    }

    public Page<PetResponse> findByName(String name, Pageable pageable) {
        return petRepository.findByNameContainingIgnoreCase(name, pageable).map(PetResponse::from);
    }

    @Transactional
    public PetResponse update(Long id, PetRequest request) {
        Pet pet = findOrThrow(id);
        pet.setName(request.getName()); pet.setSpecies(request.getSpecies());
        pet.setRace(request.getRace()); pet.setBirthDate(request.getBirthDate());
        pet.setWeight(request.getWeight()); pet.setPhotoUrl(request.getPhotoUrl());
        return PetResponse.from(petRepository.save(pet));
    }

    @Transactional
    public void delete(Long id) {
        findOrThrow(id);
        petRepository.deleteById(id);
    }

    public Pet findOrThrow(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet não encontrado: " + id));
    }
}
