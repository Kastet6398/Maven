package org.hillel.homework.mapper;

import lombok.AllArgsConstructor;
import org.hillel.homework.dto.response.ProductResponse;
import org.hillel.homework.dto.request.ProductRequest;
import org.hillel.homework.entity.Product;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProductMapper implements Mapper<ProductRequest, ProductResponse, Product> {
    public ProductResponse entityToResponse(Product productEntity) {
        return ProductResponse.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .price(productEntity.getPrice())
                .build();
    }

    public Product requestToEntity(ProductRequest dto) {
        return Product.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .build();
    }
}
