package br.com.fiap.ClyvoPet.user.service;

import br.com.fiap.ClyvoPet.appointment.model.Appointment;
import br.com.fiap.ClyvoPet.exception.BusinessException;
import br.com.fiap.ClyvoPet.exception.ResourceNotFoundException;
import br.com.fiap.ClyvoPet.medicalFile.model.MedicalFile;
import br.com.fiap.ClyvoPet.order.repository.OrderRepository;
import br.com.fiap.ClyvoPet.pet.model.Pet;
import br.com.fiap.ClyvoPet.user.dto.DashboardResponse;
import br.com.fiap.ClyvoPet.user.dto.UserRequest;
import br.com.fiap.ClyvoPet.user.dto.UserResponse;
import br.com.fiap.ClyvoPet.user.model.User;
import br.com.fiap.ClyvoPet.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Transactional
    @CacheEvict(value = "users", allEntries = true)
    public UserResponse create(UserRequest request) {
        if (userRepository.existsByEmail(request.getEmail()))
            throw new BusinessException("Email já cadastrado: " + request.getEmail());
        User user = User.builder()
                .name(request.getName()).email(request.getEmail())
                .telephone(request.getTelephone()).password(request.getPassword())
                .build();
        return UserResponse.from(userRepository.save(user));
    }

    @Cacheable(value = "users", key = "#id")
    public UserResponse findById(Long id) {
        return UserResponse.from(findOrThrow(id));
    }

    @Cacheable(value = "users")
    public Page<UserResponse> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserResponse::from);
    }

    public Page<UserResponse> findByName(String name, Pageable pageable) {
        return userRepository.findByNameContainingIgnoreCase(name, pageable).map(UserResponse::from);
    }

    @Transactional
    @CacheEvict(value = "users", allEntries = true)
    public UserResponse update(Long id, UserRequest request) {
        User user = findOrThrow(id);
        if (!user.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail()))
            throw new BusinessException("Email já em uso por outro usuário");
        user.setName(request.getName()); user.setEmail(request.getEmail());
        user.setTelephone(request.getTelephone()); user.setPassword(request.getPassword());
        return UserResponse.from(userRepository.save(user));
    }

    @Transactional
    @CacheEvict(value = "users", allEntries = true)
    public void delete(Long id) {
        findOrThrow(id);
        userRepository.deleteById(id);
    }

    public DashboardResponse getDashboard(Long userId) {
        User user = findOrThrow(userId);
        List<Pet> pets = user.getPets() != null ? user.getPets() : List.of();
        Double totalSpent = orderRepository.sumTotalByUserId(userId);
        int totalOrders = orderRepository.findByUserId(userId).size();

        List<DashboardResponse.PetSummary> summaries = pets.stream().map(pet -> {
            int age = Period.between(pet.getBirthDate(), LocalDate.now()).getYears();
            MedicalFile mf = pet.getMedicalFile();
            List<Appointment> appts = pet.getAppointments() != null ? pet.getAppointments() : List.of();
            boolean alert = mf != null && mf.getNextVaccine() != null
                    && mf.getNextVaccine().isBefore(LocalDate.now().plusDays(30));
            String lastStatus = appts.isEmpty() ? "Sem consultas" : appts.get(appts.size() - 1).getStatus();
            return DashboardResponse.PetSummary.builder()
                    .petName(pet.getName()).species(pet.getSpecies()).ageYears(age)
                    .allergies(mf != null ? mf.getAllergies() : "N/A")
                    .medicines(mf != null ? mf.getMedicines() : "N/A")
                    .nextVaccine(mf != null ? mf.getNextVaccine() : null)
                    .vaccineAlertIn30Days(alert).totalAppointments(appts.size())
                    .lastAppointmentStatus(lastStatus).build();
        }).collect(Collectors.toList());

        return DashboardResponse.builder()
                .ownerName(user.getName()).ownerEmail(user.getEmail())
                .totalPets(pets.size()).totalOrders(totalOrders)
                .totalSpent(totalSpent != null ? totalSpent : 0.0)
                .pets(summaries).build();
    }

    public User findOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado: " + id));
    }
}
