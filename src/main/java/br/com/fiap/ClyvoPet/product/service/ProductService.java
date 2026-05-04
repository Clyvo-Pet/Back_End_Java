package br.com.fiap.ClyvoPet.product.service;

import br.com.fiap.ClyvoPet.exception.ResourceNotFoundException;
import br.com.fiap.ClyvoPet.product.dto.ProductRequest;
import br.com.fiap.ClyvoPet.product.dto.ProductResponse;
import br.com.fiap.ClyvoPet.product.model.Product;
import br.com.fiap.ClyvoPet.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    @CacheEvict(value = "products", allEntries = true)
    public ProductResponse create(ProductRequest r) {
        Product p = Product.builder().name(r.getName()).description(r.getDescription())
                .category(r.getCategory()).targetSpecies(r.getTargetSpecies())
                .price(r.getPrice()).imgUrl(r.getImgUrl()).active(r.getActive()).build();
        return ProductResponse.from(productRepository.save(p));
    }

    @Cacheable(value = "products", key = "#id")
    public ProductResponse findById(Long id) {
        return ProductResponse.from(findOrThrow(id));
    }

    @Cacheable(value = "products")
    public Page<ProductResponse> findAll(Pageable pageable) {
        return productRepository.findAll(pageable).map(ProductResponse::from);
    }

    public Page<ProductResponse> findByCategory(String category, Pageable pageable) {
        return productRepository.findByCategoryIgnoreCase(category, pageable).map(ProductResponse::from);
    }

    public Page<ProductResponse> findBySpecies(String species, Pageable pageable) {
        return productRepository.findByActiveAndTargetSpeciesIgnoreCase("S", species, pageable).map(ProductResponse::from);
    }

    public Page<ProductResponse> findByName(String name, Pageable pageable) {
        return productRepository.findByNameContainingIgnoreCase(name, pageable).map(ProductResponse::from);
    }

    @Transactional
    @CacheEvict(value = "products", allEntries = true)
    public ProductResponse update(Long id, ProductRequest r) {
        Product p = findOrThrow(id);
        p.setName(r.getName()); p.setDescription(r.getDescription());
        p.setCategory(r.getCategory()); p.setTargetSpecies(r.getTargetSpecies());
        p.setPrice(r.getPrice()); p.setImgUrl(r.getImgUrl()); p.setActive(r.getActive());
        return ProductResponse.from(productRepository.save(p));
    }

    @Transactional
    @CacheEvict(value = "products", allEntries = true)
    public void delete(Long id) {
        findOrThrow(id);
        productRepository.deleteById(id);
    }

    public Product findOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado: " + id));
    }
}
