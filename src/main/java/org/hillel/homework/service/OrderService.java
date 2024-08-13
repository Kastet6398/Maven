package org.hillel.homework.service;

import org.hillel.homework.dto.response.OrderResponse;
import org.hillel.homework.dto.request.OrderItemRequest;
import org.hillel.homework.dto.request.OrderRequest;
import org.hillel.homework.entity.OrderItem;
import org.hillel.homework.entity.Order;
import org.hillel.homework.exception.ResourceNotFoundException;
import org.hillel.homework.mapper.OrderItemMapper;
import org.hillel.homework.mapper.OrderMapper;
import org.hillel.homework.repository.OrderItemRepository;
import org.hillel.homework.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private OrderRepository orderRepository;
    private OrderMapper orderMapper;
    private ProductService productService;
    private OrderItemMapper orderItemMapper;
    private OrderItemRepository orderItemRepository;

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order #" + id + " not found"));
    }

    public OrderResponse save(Long id, OrderRequest orderRequest) {
        Order prevOrder = null;
        if (id != null) {
            prevOrder = getOrderById(id);
        }
        Order orderEntity = orderMapper.requestToEntity(orderRequest);
        if (id != null) {
            orderEntity.setId(id);
        }
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemRequest orderItemRequest : orderRequest.getOrderItems()) {
            OrderItem orderItemEntity = orderItemMapper.requestToEntity(orderItemRequest);
            orderItemEntity.setProduct(productService.getProductById(orderItemRequest.getProductId()));
            orderItemEntity.setOrder(orderEntity);
            orderItems.add(orderItemEntity);
        }
        if (id != null) {
            for (OrderItem orderItem : prevOrder.getOrderItems()) {
                orderItemRepository.delete(orderItem);
            }
        }
        orderEntity.setOrderDate(LocalDate.now());

        orderEntity = orderRepository.save(orderEntity);
        List<OrderItem> savedOrderItems = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            savedOrderItems.add(orderItemRepository.save(orderItem));
        }
        orderEntity.setOrderItems(savedOrderItems);
        return orderMapper.entityToResponse(orderEntity);
    }

    public OrderResponse save(OrderRequest orderRequest) {
        return save(null, orderRequest);
    }

    public OrderResponse addItem(Long id, OrderItemRequest orderItemRequest) {
        Order order = getOrderById(id);
        productService.getProductById(orderItemRequest.getProductId());
        OrderItem orderItemEntity = orderItemMapper.requestToEntity(orderItemRequest);
        orderItemEntity.setOrder(order);
        orderItemEntity = orderItemRepository.save(orderItemEntity);

        List<OrderItem> orderItems = order.getOrderItems();
        orderItems.add(orderItemEntity);
        order.setOrderItems(orderItems);
        return orderMapper.entityToResponse(order);
    }

    public OrderResponse removeItem(Long orderId, Long productId) {
        Order order = getOrderById(orderId);
        if (!order.getOrderItems().removeIf(oi -> {
            if (oi.getProduct().getId().equals(productId)) {
                orderItemRepository.delete(oi);
                return true;
            }
            return false;
        })) {
            throw new ResourceNotFoundException("Product #" + productId + " not found in the order #" + orderId);
        }
        return orderMapper.entityToResponse(order);
    }

    public OrderResponse getOrder(Long orderId) {
        return orderMapper.entityToResponse(getOrderById(orderId));
    }

    public void delete(long id) {
        getOrderById(id);
        orderRepository.deleteById(id);
    }
}
