package br.com.fiap.ClyvoPet.medicalFile.api;

import br.com.fiap.ClyvoPet.medicalFile.dto.MedicalFileRequest;
import br.com.fiap.ClyvoPet.medicalFile.model.MedicalFile;
import br.com.fiap.ClyvoPet.medicalFile.service.MedicalFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/medical-files")
@RequiredArgsConstructor
@Tag(name = "Fichas Médicas", description = "Gerenciamento de fichas médicas dos pets")
public class MedicalFileController {

    private final MedicalFileService medicalFileService;

    @PostMapping
    @Operation(summary = "Criar ficha médica para um pet")
    public ResponseEntity<MedicalFile> create(@Valid @RequestBody MedicalFileRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(medicalFileService.create(request));
    }

    @GetMapping("/pet/{petId}")
    @Operation(summary = "Buscar ficha médica por pet")
    public ResponseEntity<MedicalFile> findByPet(@PathVariable Long petId) {
        return ResponseEntity.ok(medicalFileService.findByPetId(petId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar ficha médica por ID")
    public ResponseEntity<MedicalFile> findById(@PathVariable Long id) {
        return ResponseEntity.ok(medicalFileService.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar ficha médica")
    public ResponseEntity<MedicalFile> update(@PathVariable Long id,
                                              @Valid @RequestBody MedicalFileRequest request) {
        return ResponseEntity.ok(medicalFileService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover ficha médica")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        medicalFileService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
