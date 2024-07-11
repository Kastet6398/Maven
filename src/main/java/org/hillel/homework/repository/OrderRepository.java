package org.hillel.homework.repository;

import org.hillel.homework.model.entity.OrderEntity;
import org.hillel.homework.model.entity.ProductEntity;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Repository
public class OrderRepository {
    private final Map<Integer, OrderEntity> orders = new HashMap<>();
    private int currentOrderId = 0;
    private int currentProductId = 0;

    public OrderEntity getOrderById(int id) {
        OrderEntity order = orders.get(id);
        if (order == null) {
            throw new NoSuchElementException();
        }
        return order;
    }

    public OrderEntity add(OrderEntity order) {
        order.setId(++currentOrderId);
        for (ProductEntity product : order.getProducts()) {
            product.setId(++currentProductId);
        }
        orders.put(order.getId(), order);
        return order;
    }

    public OrderEntity update(OrderEntity updatedOrder, boolean isIncreasedId) {
        OrderEntity orderToUpdate = getOrderById(updatedOrder.getId());
        if (updatedOrder.getProducts() != null) {
            for (ProductEntity product : updatedOrder.getProducts()) {
                if (isIncreasedId) {
                    currentProductId++;
                }
                product.setId(currentProductId);
            }
            orderToUpdate.setProducts(updatedOrder.getProducts());
        }
        if (updatedOrder.getCustomerName() != null) {
            orderToUpdate.setCustomerName(updatedOrder.getCustomerName());
        }
        orders.put(updatedOrder.getId(), orderToUpdate);
        return orderToUpdate;
    }

    public OrderEntity update(OrderEntity updatedOrder) {
        return update(updatedOrder, true);
    }

    public OrderEntity addProduct(int orderId, ProductEntity product) {
        OrderEntity order = getOrderById(orderId);
        product.setId(++currentProductId);
        order.getProducts().add(product);
        return order;
    }

    public OrderEntity remove(int id) {
        OrderEntity order = orders.remove(id);
        if (order == null) {
            throw new NoSuchElementException();
        }
        return order;
    }

    public OrderEntity removeProduct(int id) {
        for (OrderEntity order : orders.values()) {
            if (order.getProducts().removeIf(product -> product.getId() == id)) {
                return order;
            }
        }
        throw new NoSuchElementException();
    }
}
