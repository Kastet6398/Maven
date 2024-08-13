package org.hillel.homework.controller.api;

import org.hillel.homework.OrderApiApplication;
import org.hillel.homework.dto.request.OrderItemRequest;
import org.hillel.homework.dto.request.ProductRequest;
import org.hillel.homework.dto.response.OrderItemResponse;
import org.hillel.homework.dto.response.OrderResponse;
import org.hillel.homework.dto.response.ProductResponse;
import org.hillel.homework.entity.Order;
import org.hillel.homework.entity.Product;
import org.hillel.homework.repository.OrderRepository;
import org.hillel.homework.repository.ProductRepository;
import org.hillel.homework.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = OrderApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
public class ProductApiControllerTest {
    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    private final Product product = new Product(null, "name", new BigDecimal("1.00"), null);
    private final ProductRequest productRequest = new ProductRequest(product.getName(), product.getPrice());
    private final ProductResponse productResponse = new ProductResponse(null, product.getName(), product.getPrice());

    @Test
    public void testGet() {
        product.setId(productRepository.save(product).getId());
        productResponse.setId(product.getId());
        ResponseEntity<ProductResponse> responseEntity = restTemplate.getForEntity("http://localhost:" + port + "/api/products/" + product.getId(), ProductResponse.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertThat(responseEntity.getBody()).isEqualTo(productResponse);
        productRepository.delete(product);
    }

    @Test
    public void testAdd() {
        ResponseEntity<ProductResponse> responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/api/products", productRequest, ProductResponse.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        ProductResponse response = responseEntity.getBody();
        product.setId(response.getId());
        productResponse.setId(product.getId());
        assertThat(response).isEqualTo(productResponse);
        productRepository.delete(product);
    }

    @Test
    public void testUpdate() {
        product.setId(productRepository.save(product).getId());
        productResponse.setId(product.getId());
        productRequest.setName("name2");
        productResponse.setName(productRequest.getName());
        HttpEntity<ProductRequest> requestEntity = new HttpEntity<>(productRequest);
        ResponseEntity<ProductResponse> responseEntity = restTemplate.exchange("http://localhost:" + port + "/api/products/" + product.getId(), HttpMethod.PUT, requestEntity, ProductResponse.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertThat(responseEntity.getBody()).isEqualTo(productResponse);
        productRepository.delete(product);
    }

    @Test
    public void testDelete() {
        product.setId(productRepository.save(product).getId());
        ResponseEntity<?> responseEntity = restTemplate.exchange("http://localhost:" + port + "/api/products/" + product.getId(), HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        ResponseEntity<OrderResponse> getResponse = restTemplate.getForEntity("http://localhost:" + port + "/api/products/" + product.getId(), OrderResponse.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        productRepository.delete(product);
    }
}