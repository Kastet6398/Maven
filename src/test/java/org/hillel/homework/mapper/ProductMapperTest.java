package org.hillel.homework.mapper;

import org.hillel.homework.dto.request.ProductRequest;
import org.hillel.homework.dto.response.ProductResponse;
import org.hillel.homework.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ProductMapperTest {

    private ProductMapper productMapper;

    @BeforeEach
    public void setUp() {
        productMapper = new ProductMapper();
    }

    @Test
    public void requestToEntity_validObject_entityReturned() {
        ProductRequest request = ProductRequest.builder()
                .name("Product 1")
                .price(new BigDecimal("10.00"))
                .build();

        Product entity = productMapper.requestToEntity(request);

        assertEquals("Product 1", entity.getName());
        assertEquals(new BigDecimal("10.00"), entity.getPrice());
    }

    @Test
    public void requestToEntity_null_NullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> productMapper.requestToEntity(null));
    }

    @Test
    public void entityToResponse_validObject_responseReturned() {
        Product entity = Product.builder()
                .id(1L)
                .name("Product 1")
                .price(new BigDecimal("10.00"))
                .build();

        ProductResponse response = productMapper.entityToResponse(entity);

        assertEquals(1L, response.getId());
        assertEquals("Product 1", response.getName());
        assertEquals(new BigDecimal("10.00"), response.getPrice());
    }

    @Test
    public void entityToResponse_null_NullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> productMapper.entityToResponse(null));
    }
}
