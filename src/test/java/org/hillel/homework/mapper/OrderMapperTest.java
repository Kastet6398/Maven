package org.hillel.homework.mapper;

import org.hillel.homework.dto.request.OrderItemRequest;
import org.hillel.homework.dto.request.OrderRequest;
import org.hillel.homework.dto.response.OrderItemResponse;
import org.hillel.homework.dto.response.OrderResponse;
import org.hillel.homework.entity.Order;
import org.hillel.homework.entity.OrderItem;
import org.hillel.homework.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderMapperTest {

    private OrderItemMapper orderItemMapper;

    private OrderMapper orderMapper;

    @BeforeEach
    public void setUp() {
        orderItemMapper = mock(OrderItemMapper.class);
        orderMapper = new OrderMapper(orderItemMapper);
    }

    @Test
    public void requestToEntity_validObject_entityReturned() {
        OrderItemRequest orderItemRequest = OrderItemRequest.builder()
                .productId(1L)
                .quantity(new BigDecimal("2.5"))
                .build();
        OrderRequest request = OrderRequest.builder()
                .customerName("John Doe")
                .orderItems(Collections.singletonList(orderItemRequest))
                .build();

        OrderItem orderItem = OrderItem.builder()
                .product(Product.builder().id(1L).build())
                .quantity(new BigDecimal("2.5"))
                .build();

        when(orderItemMapper.requestToEntity(orderItemRequest)).thenReturn(orderItem);

        Order entity = orderMapper.requestToEntity(request);

        assertEquals("John Doe", entity.getCustomerName());
        assertEquals(1, entity.getOrderItems().size());
        assertEquals(orderItem, entity.getOrderItems().get(0));
    }

    @Test
    public void requestToEntity_null_NullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> orderMapper.requestToEntity(null));
    }

    @Test
    public void entityToResponse_validObject_responseReturned() {
        OrderItem orderItem = OrderItem.builder()
                .id(1L)
                .product(Product.builder().id(1L).build())
                .quantity(new BigDecimal("2.5"))
                .build();
        Order entity = Order.builder()
                .id(1L)
                .customerName("John Doe")
                .orderItems(Collections.singletonList(orderItem))
                .build();

        OrderItemResponse orderItemResponse = OrderItemResponse.builder()
                .id(1L)
                .productId(1L)
                .quantity(new BigDecimal("2.5"))
                .build();

        when(orderItemMapper.entityToResponse(orderItem)).thenReturn(orderItemResponse);

        OrderResponse response = orderMapper.entityToResponse(entity);

        assertEquals(1L, response.getId());
        assertEquals("John Doe", response.getCustomerName());
        assertEquals(1, response.getOrderItems().size());
        assertEquals(orderItemResponse, response.getOrderItems().get(0));
    }

    @Test
    public void entityToResponse_null_NullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> orderMapper.entityToResponse(null));
    }
}
