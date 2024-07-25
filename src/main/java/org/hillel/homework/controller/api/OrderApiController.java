package org.hillel.homework.controller.api;

import org.hillel.homework.dto.response.OrderResponse;
import org.hillel.homework.dto.request.OrderItemRequest;
import org.hillel.homework.dto.request.OrderRequest;
import org.hillel.homework.entity.Order;
import org.hillel.homework.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderApiController {

    private final OrderService orderService;

    public OrderApiController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> get(@PathVariable long id) {
        OrderResponse order = orderService.getOrder(id);
        return ResponseEntity.ok(order);
    }

    @PostMapping
    public ResponseEntity<OrderResponse> add(@RequestBody OrderRequest order) {
        OrderResponse createdOrder = orderService.save(order);
        return ResponseEntity.ok(createdOrder);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> update(@PathVariable long id, @RequestBody OrderRequest order) {
        OrderResponse updatedOrder = orderService.update(id, order);
        return ResponseEntity.ok(updatedOrder);
    }

    @PatchMapping("/{id}/products")
    public ResponseEntity<OrderResponse> addProduct(@PathVariable long id, @RequestBody OrderItemRequest orderItemRequest) {
        OrderResponse updatedOrder = orderService.addItem(id, orderItemRequest);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("{orderId}/products/{productId}")
    public ResponseEntity<OrderResponse> removeProduct(@PathVariable long orderId, @PathVariable long productId) {
        OrderResponse updatedOrder = orderService.removeItem(orderId, productId);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Order> delete(@PathVariable long id) {
        orderService.delete(id);
        return ResponseEntity.ok().build();
    }
}
