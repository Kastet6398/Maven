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

    public ProductResponse save(Long id, ProductRequest productRequest) {
        if (id != null) {
            getProductById(id);
        }
        Product productEntity = productMapper.requestToEntity(productRequest);
        if (id == null) {
            productEntity.setId(productRepository.save(productEntity).getId());
            return productMapper.entityToResponse(productEntity);
        } else {
            productEntity.setId(id);
            return productMapper.entityToResponse(productRepository.save(productEntity));
        }
    }

    public ProductResponse save(ProductRequest productRequest) {
        return save(null, productRequest);
    }

    public void delete(Long id) {
        getProductById(id);
        productRepository.deleteById(id);
    }

    public ProductResponse getProduct(Long id) {
        return productMapper.entityToResponse(getProductById(id));
    }
}
