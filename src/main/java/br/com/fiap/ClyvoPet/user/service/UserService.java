package br.com.fiap.ClyvoPet.user.service;

import br.com.fiap.ClyvoPet.user.dto.UserRequest;
import br.com.fiap.ClyvoPet.user.dto.UserResponse;
import br.com.fiap.ClyvoPet.user.model.User;
import br.com.fiap.ClyvoPet.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository repository;

    public UserResponse create(UserRequest request) {
        User user = request.toEntity();
        User response = repository.save(user);
        return UserResponse.fromEntity(response);
    }

    public List<UserResponse> getAll() {
        List<User> users = repository.findAll();
        return users.stream()
                .map(UserResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public UserResponse getById(Long id) {
        User user = repository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return UserResponse.fromEntity(user);
    }

    public UserResponse update(Long id, UserRequest request) {
        User user = repository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(request.password());
        user.setTelephone(request.telephone());

        User response = repository.save(user);
        return UserResponse.fromEntity(response);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
