package org.hillel.homework.service;

import org.hillel.homework.dto.request.ProductRequest;
import org.hillel.homework.dto.response.ProductResponse;
import org.hillel.homework.entity.Product;
import org.hillel.homework.exception.ResourceNotFoundException;
import org.hillel.homework.mapper.ProductMapper;
import org.hillel.homework.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {
    private ProductRepository productRepository;
    private ProductService productService;

    private final Product product = new Product(1L, "name", new BigDecimal(1), null);
    private final ProductRequest productRequest = new ProductRequest(product.getName(), product.getPrice());
    private final ProductResponse productResponse = new ProductResponse(product.getId(), product.getName(), product.getPrice());

    @BeforeEach
    public void setup() {
        productRepository = mock(ProductRepository.class);
        productService = mock(ProductService.class);
        productService = spy(new ProductService(productRepository, new ProductMapper()));
    }

    @Test
    public void getProductById_validId_productReturned() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        assertEquals(productService.getProductById(1L), product);
        verify(productRepository).findById(1L);
    }

    @Test
    public void getProductById_notFound_ResourceNotFoundExceptionThrown() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(1L));
    }

    @Test
    public void saveWithoutId_validObject_saved() {
        when(productRepository.save(new Product(null, product.getName(), product.getPrice(), product.getOrderItems()))).thenReturn(product);
        assertEquals(productResponse, productService.save(productRequest));
        verify(productRepository).save(product);
    }

    @Test
    public void saveWithId_productNotFound_ResourceNotFoundExceptionThrown() {
        doThrow(ResourceNotFoundException.class).when(productService).getProductById(1L);
        assertThrows(ResourceNotFoundException.class, () -> productService.save(1L, productRequest));
        verify(productService).getProductById(1L);
        verify(productRepository, never()).save(any());
    }

    @Test
    public void saveWithId_validProductId_productUpdated() {
        doReturn(product).when(productService).getProductById(1L);
        when(productRepository.save(product)).thenReturn(product);
        assertEquals(productResponse, productService.save(1L, productRequest));
        verify(productService).getProductById(1L);
        verify(productRepository).save(product);
    }

    @Test
    public void getProduct_validId_productResponseReturned() {
        doReturn(product).when(productService).getProductById(1L);
        assertEquals(productResponse, productService.getProduct(1L));
        verify(productService).getProductById(1L);
    }

    @Test
    public void getProduct_notFound_ResourceNotFoundExceptionThrown() {
        doThrow(ResourceNotFoundException.class).when(productService).getProductById(1L);
        assertThrows(ResourceNotFoundException.class, () -> productService.getProduct(1L));
        verify(productService).getProductById(1L);
    }

    @Test
    public void delete_validId_productDeleted() {
        doReturn(product).when(productService).getProductById(1L);
        productService.delete(1L);
        verify(productService).getProductById(1L);
        verify(productRepository).deleteById(1L);
    }

    @Test
    public void delete_notFound_ResourceNotFoundExceptionThrown() {
        doThrow(ResourceNotFoundException.class).when(productService).getProductById(1L);
        assertThrows(ResourceNotFoundException.class, () -> productService.delete(1L));
        verify(productService).getProductById(1L);
    }
}