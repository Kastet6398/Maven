package org.hillel.homework.controller.api;

import org.hillel.homework.OrderApiApplication;
import org.hillel.homework.model.dto.OrderDTO;
import org.hillel.homework.model.dto.ProductDTO;
import org.hillel.homework.model.entity.OrderEntity;
import org.hillel.homework.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/orders")
public class OrderApiController {
    private final OrderService orderService;

    public OrderApiController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderEntity> get(@PathVariable int id) {
        try {
            OrderEntity order = orderService.getOrderById(id);
            return ResponseEntity.ok(order);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<OrderEntity> add(@RequestBody OrderDTO order) {
        OrderEntity createdOrder = orderService.add(order);
        return ResponseEntity.ok(createdOrder);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderEntity> update(@PathVariable int id, @RequestBody OrderDTO order) {
        try {
            OrderEntity updatedOrder = orderService.update(id, order);
            return ResponseEntity.ok(updatedOrder);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/products")
    public ResponseEntity<OrderEntity> addProduct(@PathVariable int id, @RequestBody ProductDTO product) {
        try {
            OrderEntity updatedOrder = orderService.addProduct(id, product);
            return ResponseEntity.ok(updatedOrder);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<OrderEntity> removeProduct(@PathVariable int id) {
        try {
            OrderEntity updatedOrder = orderService.removeProduct(id);
            return ResponseEntity.ok(updatedOrder);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OrderEntity> delete(@PathVariable int id) {
        try {
            OrderEntity updatedOrder = orderService.remove(id);
            return ResponseEntity.ok(updatedOrder);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
