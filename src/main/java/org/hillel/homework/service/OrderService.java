package org.hillel.homework.service;

import org.hillel.homework.model.dto.OrderDTO;
import org.hillel.homework.model.dto.ProductDTO;
import org.hillel.homework.model.entity.OrderEntity;
import org.hillel.homework.model.mapper.OrderMapper;
import org.hillel.homework.model.mapper.ProductMapper;
import org.hillel.homework.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderRepository repository;
    private final OrderMapper orderMapper;
    private final ProductMapper productMapper;

    public OrderService(OrderRepository repository, OrderMapper orderMapper, ProductMapper productMapper) {
        this.repository = repository;
        this.orderMapper = orderMapper;
        this.productMapper = productMapper;
    }

    public OrderEntity getOrderById(int id) {
        return repository.getOrderById(id);
    }

    public OrderEntity add(OrderDTO order) {
        return repository.add(orderMapper.dtoToEntity(order));
    }

    public OrderEntity update(int id, OrderDTO updatedOrder) {
        return repository.update(orderMapper.dtoToEntity(id, updatedOrder));
    }

    public OrderEntity addProduct(int orderId, ProductDTO product) {
        return repository.addProduct(orderId, productMapper.dtoToEntity(product));
    }

    public OrderEntity removeProduct(int id) {
        return repository.removeProduct(id);
    }

    public OrderEntity remove(int id) {
        return repository.remove(id);
    }
}
