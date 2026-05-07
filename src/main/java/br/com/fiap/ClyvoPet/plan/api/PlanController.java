package br.com.fiap.ClyvoPet.plan.api;

import br.com.fiap.ClyvoPet.plan.dto.PlanRequest;
import br.com.fiap.ClyvoPet.plan.model.Plan;
import br.com.fiap.ClyvoPet.plan.service.PlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/plans")
@RequiredArgsConstructor
@Tag(name = "Planos", description = "Gerenciamento de planos de assinatura")
public class PlanController {

    private final PlanService planService;

    @PostMapping
    @Operation(summary = "Criar plano vinculado a uma assinatura")
    public ResponseEntity<Plan> create(@Valid @RequestBody PlanRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(planService.create(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar plano por ID")
    public ResponseEntity<Plan> findById(@PathVariable Long id) {
        return ResponseEntity.ok(planService.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar plano")
    public ResponseEntity<Plan> update(@PathVariable Long id, @Valid @RequestBody PlanRequest request) {
        return ResponseEntity.ok(planService.update(id, request));
    }
}
