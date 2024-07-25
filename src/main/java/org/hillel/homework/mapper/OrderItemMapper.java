package org.hillel.homework.mapper;

import lombok.AllArgsConstructor;
import org.hillel.homework.dto.response.OrderItemResponse;
import org.hillel.homework.dto.request.OrderItemRequest;
import org.hillel.homework.entity.OrderItem;
import org.hillel.homework.entity.Product;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderItemMapper implements Mapper<OrderItemRequest, OrderItemResponse, OrderItem> {
    @Override
    public OrderItem requestToEntity(OrderItemRequest request) {
        return OrderItem.builder()
                .product(Product.builder().id(request.getProductId()).build())
                .quantity(request.getQuantity())
                .build();
    }

    @Override
    public OrderItemResponse entityToResponse(OrderItem entity) {
        return OrderItemResponse.builder()
                .id(entity.getId())
                .productId(entity.getProduct().getId())
                .quantity(entity.getQuantity())
                .build();
    }
}
