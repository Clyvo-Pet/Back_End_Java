package br.com.fiap.ClyvoPet.user.repository;

import br.com.fiap.ClyvoPet.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
