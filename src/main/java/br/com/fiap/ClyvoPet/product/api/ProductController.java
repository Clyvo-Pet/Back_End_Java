package br.com.fiap.ClyvoPet.product.api;

import br.com.fiap.ClyvoPet.product.dto.ProductRequest;
import br.com.fiap.ClyvoPet.product.dto.ProductResponse;
import br.com.fiap.ClyvoPet.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Produtos", description = "Gerenciamento de produtos do pet shop")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @Operation(summary = "Criar produto")
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(request));
    }

    @GetMapping
    @Operation(summary = "Listar produtos com paginação e ordenação")
    public ResponseEntity<Page<ProductResponse>> findAll(
            @PageableDefault(size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(productService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @GetMapping("/search")
    @Operation(summary = "Buscar produto por nome")
    public ResponseEntity<Page<ProductResponse>> findByName(@RequestParam String name,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(productService.findByName(name, pageable));
    }

    @GetMapping("/category/{category}")
    @Operation(summary = "Filtrar por categoria")
    public ResponseEntity<Page<ProductResponse>> findByCategory(@PathVariable String category,
            @PageableDefault(size = 10, sort = "price") Pageable pageable) {
        return ResponseEntity.ok(productService.findByCategory(category, pageable));
    }

    @GetMapping("/species/{species}")
    @Operation(summary = "Filtrar por espécie alvo")
    public ResponseEntity<Page<ProductResponse>> findBySpecies(@PathVariable String species,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(productService.findBySpecies(species, pageable));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar produto")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id, @Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover produto")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
