package br.com.fiap.ClyvoPet.signature.api;

import br.com.fiap.ClyvoPet.signature.dto.SignatureRequest;
import br.com.fiap.ClyvoPet.signature.model.Signature;
import br.com.fiap.ClyvoPet.signature.service.SignatureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/signatures")
@RequiredArgsConstructor
@Tag(name = "Assinaturas", description = "Gerenciamento de assinaturas de planos")
public class SignatureController {

    private final SignatureService signatureService;

    @PostMapping
    @Operation(summary = "Criar assinatura para um usuário")
    public ResponseEntity<Signature> create(@Valid @RequestBody SignatureRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(signatureService.create(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar assinatura por ID")
    public ResponseEntity<Signature> findById(@PathVariable Long id) {
        return ResponseEntity.ok(signatureService.findById(id));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Listar assinaturas de um usuário")
    public ResponseEntity<List<Signature>> findByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(signatureService.findByUserId(userId));
    }

    @PatchMapping("/{id}/cancel")
    @Operation(summary = "Cancelar assinatura")
    public ResponseEntity<Signature> cancel(@PathVariable Long id) {
        return ResponseEntity.ok(signatureService.cancel(id));
    }
}
