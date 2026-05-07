package br.com.fiap.ClyvoPet.signature.repository;

import br.com.fiap.ClyvoPet.signature.model.Signature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SignatureRepository extends JpaRepository<Signature, Long> {
    List<Signature> findByUserId(Long userId);
    Optional<Signature> findByUserIdAndStatus(Long userId, String status);
}
