package org.hillel.homework.mapper;

import lombok.AllArgsConstructor;
import org.hillel.homework.dto.response.OrderResponse;
import org.hillel.homework.dto.request.OrderRequest;
import org.hillel.homework.entity.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;

@Component
@AllArgsConstructor
public class OrderMapper implements Mapper<OrderRequest, OrderResponse, Order> {
    private OrderItemMapper orderItemMapper;

    @Override
    public OrderResponse entityToResponse(Order entity) {
        return OrderResponse.builder()
                .id(entity.getId())
                .customerName(entity.getCustomerName())
                .orderItems(entity.getOrderItems().stream().map(e -> orderItemMapper.entityToResponse(e)).toList())
                .build();
    }

    @Override
    public Order requestToEntity(OrderRequest request) {
        return Order.builder()
                .customerName(request.getCustomerName())
                .orderItems(request.getOrderItems().stream().map(r -> orderItemMapper.requestToEntity(r)).toList())
                .build();
    }
}
