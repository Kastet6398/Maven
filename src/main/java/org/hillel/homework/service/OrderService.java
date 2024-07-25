package org.hillel.homework.service;

import org.hillel.homework.dto.response.OrderResponse;
import org.hillel.homework.dto.request.OrderItemRequest;
import org.hillel.homework.dto.request.OrderRequest;
import org.hillel.homework.entity.OrderItem;
import org.hillel.homework.entity.Order;
import org.hillel.homework.entity.Product;
import org.hillel.homework.exception.ResourceNotFoundException;
import org.hillel.homework.mapper.OrderItemMapper;
import org.hillel.homework.mapper.OrderMapper;
import org.hillel.homework.repository.OrderItemRepository;
import org.hillel.homework.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.hillel.homework.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private OrderRepository orderRepository;
    private OrderMapper orderMapper;
    private ProductRepository productRepository;
    private OrderItemMapper orderItemMapper;
    private OrderItemRepository orderItemRepository;

    public OrderResponse save(Long id, OrderRequest orderRequest) {
        Order orderEntity = orderMapper.requestToEntity(orderRequest);
        if (id != null) {
            orderEntity.setId(id);
        }
        orderEntity.setOrderDate(LocalDate.now());
        List<OrderItem> orderItems = new ArrayList<>();
        orderEntity.setOrderItems(new ArrayList<>());
        for (OrderItemRequest orderItemRequest : orderRequest.getOrderItems()) {
            Product product = productRepository.findById(orderItemRequest.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
            OrderItem orderItemEntity = orderItemMapper.requestToEntity(orderItemRequest);
            orderItemEntity.setProduct(product);
            orderItemEntity = orderItemRepository.save(orderItemEntity);
            orderItems.add(orderItemEntity);
        }
        orderEntity = orderRepository.save(orderEntity);

        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(orderEntity);
            orderEntity.getOrderItems().add(orderItemRepository.save(orderItem));
        }
        return orderMapper.entityToResponse(orderEntity);
    }

    public OrderResponse save(OrderRequest orderRequest) {
        return save(null, orderRequest);
    }

    public OrderResponse addItem(Long id, OrderItemRequest orderItemRequest) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        Product product = productRepository.findById(orderItemRequest.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        OrderItem orderItemEntity = orderItemMapper.requestToEntity(orderItemRequest);
        orderItemEntity.setOrder(order);
        orderItemRepository.save(orderItemEntity);
        OrderItem orderItem = OrderItem.builder()
                        .product(product)
                        .quantity(orderItemRequest.getQuantity())
                        .build();
        order.getOrderItems().add(orderItem);
        return orderMapper.entityToResponse(orderRepository.save(order));
    }

    public OrderResponse removeItem(Long orderId, Long productId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        if (!order.getOrderItems().removeIf(oi -> {
            if (oi.getProduct().getId().equals(productId)) {
                orderItemRepository.delete(oi);
                return true;
            }
            return false;
        })) {
            throw new ResourceNotFoundException("Product not found in the order");
        }
        return orderMapper.entityToResponse(order);
    }

    public OrderResponse getOrder(Long orderId) {
        return orderMapper.entityToResponse(orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found")));
    }

    public OrderResponse update(Long orderId, OrderRequest orderRequest) {
        Order orderEntity = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        for (OrderItem orderItem : orderEntity.getOrderItems()) {
            orderItemRepository.delete(orderItem);
        }
        return save(orderId, orderRequest);
    }

    public void delete(long id) {
        orderRepository.deleteById(id);
    }
}
