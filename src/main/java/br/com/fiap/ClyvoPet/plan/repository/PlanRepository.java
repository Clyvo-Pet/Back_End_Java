package br.com.fiap.ClyvoPet.plan.repository;

import br.com.fiap.ClyvoPet.plan.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
    Optional<Plan> findBySignatureId(Long signatureId);
}
