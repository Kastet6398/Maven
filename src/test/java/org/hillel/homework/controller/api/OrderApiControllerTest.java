package org.hillel.homework.controller.api;

import org.hillel.homework.OrderApiApplication;
import org.hillel.homework.dto.request.OrderItemRequest;
import org.hillel.homework.dto.request.OrderRequest;
import org.hillel.homework.dto.response.OrderItemResponse;
import org.hillel.homework.dto.response.OrderResponse;
import org.hillel.homework.entity.Order;
import org.hillel.homework.entity.OrderItem;
import org.hillel.homework.entity.Product;
import org.hillel.homework.repository.OrderItemRepository;
import org.hillel.homework.repository.OrderRepository;
import org.hillel.homework.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = OrderApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
public class OrderApiControllerTest {
    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    private final Product product = new Product(null, "name", new BigDecimal("1.00"), null);
    private final Product productWithoutItems = new Product(null, "name", new BigDecimal("1.00"), null);
    private final Order order = new Order(null, "name", LocalDate.now(), null);
    private final Order orderWithoutItems = new Order(null, "name", LocalDate.now(), null);
    private final OrderItem orderItem = new OrderItem(null, product, new BigDecimal("1.00"), order);
    private final OrderItemRequest orderItemRequest = new OrderItemRequest(null, orderItem.getQuantity());
    private final OrderRequest orderRequest = new OrderRequest(order.getCustomerName(), new ArrayList<>(List.of(orderItemRequest)));
    private final OrderItemResponse orderItemResponse = new OrderItemResponse(null, orderItem.getProduct().getId(), orderItem.getQuantity());

    private final OrderResponse orderResponse = new OrderResponse(null, order.getCustomerName(), new ArrayList<>(List.of(orderItemResponse)));

    {
        product.setOrderItems(new ArrayList<>(List.of(orderItem)));
        order.setOrderItems(new ArrayList<>(List.of(orderItem)));
    }

    @BeforeEach
    public void setup() {
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    @Test
    public void testGet() {
        productWithoutItems.setId(productRepository.save(productWithoutItems).getId());
        product.setId(productWithoutItems.getId());
        orderItemResponse.setProductId(product.getId());
        orderWithoutItems.setId(orderRepository.save(orderWithoutItems).getId());
        order.setId(orderWithoutItems.getId());
        orderResponse.setId(order.getId());
        orderItem.setId(orderItemRepository.save(orderItem).getId());
        orderItemResponse.setId(orderItem.getId());
        ResponseEntity<OrderResponse> responseEntity = restTemplate.getForEntity("http://localhost:" + port + "/api/orders/" + order.getId(), OrderResponse.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertThat(responseEntity.getBody()).isEqualTo(orderResponse);
        orderItemRepository.delete(orderItem);
        orderRepository.delete(orderWithoutItems);
        productRepository.delete(productWithoutItems);
    }

    @Test
    public void testAdd() {
        productWithoutItems.setId(productRepository.save(productWithoutItems).getId());
        product.setId(productWithoutItems.getId());
        orderItemRequest.setProductId(product.getId());
        ResponseEntity<OrderResponse> responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/api/orders", orderRequest, OrderResponse.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        OrderResponse response = responseEntity.getBody();
        orderWithoutItems.setId(response.getId());
        assertThat(response.getOrderItems().size()).isEqualTo(1);
        OrderItemResponse itemResponse = response.getOrderItems().get(0);
        orderItem.setId(itemResponse.getId());
        orderItemRepository.delete(orderItem);
        orderRepository.delete(orderWithoutItems);
        productRepository.delete(productWithoutItems);
    }

    @Test
    public void testUpdate() {
        productWithoutItems.setId(productRepository.save(productWithoutItems).getId());
        product.setId(productWithoutItems.getId());
        orderItemRequest.setProductId(product.getId());
        orderItemResponse.setProductId(product.getId());
        orderWithoutItems.setId(orderRepository.save(orderWithoutItems).getId());
        order.setId(orderWithoutItems.getId());
        orderResponse.setId(order.getId());
        orderItemRepository.save(orderItem);
        HttpEntity<OrderRequest> requestEntity = new HttpEntity<>(orderRequest);
        ResponseEntity<OrderResponse> responseEntity = restTemplate.exchange("http://localhost:" + port + "/api/orders/" + order.getId(), HttpMethod.PUT, requestEntity, OrderResponse.class);
        orderItem.setId(orderItem.getId() + 1);
        orderItemResponse.setId(orderItem.getId());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertThat(responseEntity.getBody()).isEqualTo(orderResponse);
        orderItemRepository.delete(orderItem);
        orderRepository.delete(orderWithoutItems);
        productRepository.delete(productWithoutItems);
    }

    @Test
    public void testAddProduct() {
        productWithoutItems.setId(productRepository.save(productWithoutItems).getId());
        product.setId(productWithoutItems.getId());
        orderItemRequest.setProductId(product.getId());
        orderItemResponse.setProductId(product.getId());
        orderWithoutItems.setId(orderRepository.save(orderWithoutItems).getId());
        order.setId(orderWithoutItems.getId());
        orderResponse.setId(order.getId());
        orderItem.setId(orderItemRepository.save(orderItem).getId());
        orderItemResponse.setId(orderItem.getId());
        OrderItemResponse newOrderItem = new OrderItemResponse(orderItemResponse.getId() + 1, orderItemResponse.getProductId(), orderItemResponse.getQuantity());
        orderResponse.getOrderItems().add(newOrderItem);
        HttpEntity<OrderItemRequest> requestEntity = new HttpEntity<>(orderItemRequest);
        ResponseEntity<OrderResponse> responseEntity = restTemplate.exchange("http://localhost:" + port + "/api/orders/" + order.getId() + "/products", HttpMethod.PATCH, requestEntity, OrderResponse.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertThat(responseEntity.getBody()).isEqualTo(orderResponse);
        orderItemRepository.delete(orderItem);
        orderItemRepository.deleteById(newOrderItem.getId());
        orderRepository.delete(orderWithoutItems);
        productRepository.delete(productWithoutItems);
    }

    @Test
    public void testRemoveProduct() {
        productWithoutItems.setId(productRepository.save(productWithoutItems).getId());
        product.setId(productWithoutItems.getId());
        orderWithoutItems.setId(orderRepository.save(orderWithoutItems).getId());
        order.setId(orderWithoutItems.getId());
        orderItem.setId(orderItemRepository.save(orderItem).getId());
        ResponseEntity<?> responseEntity = restTemplate.exchange("http://localhost:" + port + "/api/orders/" + order.getId() + "/products/" + product.getId(), HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        OrderResponse getResponse = restTemplate.getForObject("http://localhost:" + port + "/api/orders/" + order.getId(), OrderResponse.class);
        assertTrue(getResponse.getOrderItems().isEmpty());
        orderRepository.delete(orderWithoutItems);
        productRepository.delete(productWithoutItems);
    }

    @Test
    public void testDelete() {
        productWithoutItems.setId(productRepository.save(productWithoutItems).getId());
        product.setId(productWithoutItems.getId());
        orderWithoutItems.setId(orderRepository.save(orderWithoutItems).getId());
        order.setId(orderWithoutItems.getId());
        orderItem.setId(orderItemRepository.save(orderItem).getId());
        ResponseEntity<?> responseEntity = restTemplate.exchange("http://localhost:" + port + "/api/orders/" + order.getId(), HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        ResponseEntity<OrderResponse> getResponse = restTemplate.getForEntity("http://localhost:" + port + "/api/orders/" + order.getId(), OrderResponse.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        orderRepository.delete(orderWithoutItems);
        productRepository.delete(productWithoutItems);
    }
}