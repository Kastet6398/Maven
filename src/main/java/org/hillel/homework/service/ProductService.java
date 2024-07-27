package org.hillel.homework.service;

import lombok.AllArgsConstructor;
import org.hillel.homework.dto.response.ProductResponse;
import org.hillel.homework.dto.request.ProductRequest;
import org.hillel.homework.entity.Product;
import org.hillel.homework.exception.ResourceNotFoundException;
import org.hillel.homework.mapper.ProductMapper;
import org.hillel.homework.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {
    private ProductRepository productRepository;
    private ProductMapper productMapper;

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product #" + id + " not found"));
    }

    public ProductResponse save(ProductRequest productRequest) {
        Product productEntity = productMapper.requestToEntity(productRequest);
        productEntity.setId(productRepository.save(productEntity).getId());
        return productMapper.entityToResponse(productEntity);
    }

    public ProductResponse update(Long id, ProductRequest productRequest) {
        getById(id);
        Product productEntity = productMapper.requestToEntity(productRequest);
        productEntity.setId(id);
        return productMapper.entityToResponse(productRepository.save(productEntity));
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    public ProductResponse getById(Long id) {
        return productMapper.entityToResponse(getProductById(id));
    }
}
