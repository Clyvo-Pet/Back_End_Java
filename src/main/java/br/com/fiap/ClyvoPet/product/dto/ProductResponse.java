package br.com.fiap.ClyvoPet.product.dto;

import br.com.fiap.ClyvoPet.product.model.Product;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private String category;
    private String targetSpecies;
    private Double price;
    private String imgUrl;
    private String active;

    public static ProductResponse from(Product p) {
        return ProductResponse.builder()
                .id(p.getId()).name(p.getName()).description(p.getDescription())
                .category(p.getCategory()).targetSpecies(p.getTargetSpecies())
                .price(p.getPrice()).imgUrl(p.getImgUrl()).active(p.getActive()).build();
    }
}
