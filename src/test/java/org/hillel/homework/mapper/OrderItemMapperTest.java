package org.hillel.homework.mapper;

import org.hillel.homework.dto.request.OrderItemRequest;
import org.hillel.homework.dto.response.OrderItemResponse;
import org.hillel.homework.entity.OrderItem;
import org.hillel.homework.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderItemMapperTest {

    private OrderItemMapper orderItemMapper;

    @BeforeEach
    public void setUp() {
        orderItemMapper = new OrderItemMapper();
    }

    @Test
    public void requestToEntity_validObject_entityReturned() {
        OrderItemRequest request = OrderItemRequest.builder()
                .productId(1L)
                .quantity(new BigDecimal("2.5"))
                .build();

        OrderItem entity = orderItemMapper.requestToEntity(request);

        assertEquals(1L, entity.getProduct().getId());
        assertEquals(new BigDecimal("2.5"), entity.getQuantity());
    }

    @Test
    public void requestToEntity_null_NullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> orderItemMapper.requestToEntity(null));
    }

    @Test
    public void entityToResponse_validObject_responseReturned() {
        Product product = Product.builder().id(1L).build();
        OrderItem entity = OrderItem.builder()
                .id(1L)
                .product(product)
                .quantity(new BigDecimal("2.5"))
                .build();

        OrderItemResponse response = orderItemMapper.entityToResponse(entity);

        assertEquals(1L, response.getId());
        assertEquals(1L, response.getProductId());
        assertEquals(new BigDecimal("2.5"), response.getQuantity());
    }

    @Test
    public void entityToResponse_null_NullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> orderItemMapper.entityToResponse(null));
    }
}
