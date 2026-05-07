package br.com.fiap.ClyvoPet.pet.api;

import br.com.fiap.ClyvoPet.pet.dto.PetRequest;
import br.com.fiap.ClyvoPet.pet.dto.PetResponse;
import br.com.fiap.ClyvoPet.pet.service.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
@Tag(name = "Pets", description = "Gerenciamento de pets")
public class PetController {

    private final PetService petService;

    @PostMapping
    @Operation(summary = "Cadastrar pet")
    public ResponseEntity<PetResponse> create(@Valid @RequestBody PetRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(petService.create(request));
    }

    @GetMapping
    @Operation(summary = "Listar pets com paginação")
    public ResponseEntity<Page<PetResponse>> findAll(
            @ParameterObject @PageableDefault(size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(petService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pet por ID")
    public ResponseEntity<PetResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(petService.findById(id));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Listar pets de um usuário")
    public ResponseEntity<List<PetResponse>> findByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(petService.findByUserId(userId));
    }

    @GetMapping("/species/{species}")
    @Operation(summary = "Buscar pets por espécie")
    public ResponseEntity<Page<PetResponse>> findBySpecies(
            @PathVariable String species,
            @ParameterObject @PageableDefault(size = 10, sort = "name") Pageable pageable) {
        return ResponseEntity.ok(petService.findBySpecies(species, pageable));
    }

    @GetMapping("/search")
    @Operation(summary = "Buscar pets por nome")
    public ResponseEntity<Page<PetResponse>> findByName(
            @RequestParam String name,
            @ParameterObject @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(petService.findByName(name, pageable));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar pet")
    public ResponseEntity<PetResponse> update(@PathVariable Long id,
                                              @Valid @RequestBody PetRequest request) {
        return ResponseEntity.ok(petService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover pet")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        petService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
