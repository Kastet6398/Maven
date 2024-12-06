package org.hillel.homework.service;

import org.hillel.homework.dto.request.OrderItemRequest;
import org.hillel.homework.dto.request.OrderRequest;
import org.hillel.homework.dto.response.OrderItemResponse;
import org.hillel.homework.dto.response.OrderResponse;
import org.hillel.homework.entity.Order;
import org.hillel.homework.entity.OrderItem;
import org.hillel.homework.entity.Product;
import org.hillel.homework.exception.ResourceNotFoundException;
import org.hillel.homework.mapper.OrderItemMapper;
import org.hillel.homework.mapper.OrderMapper;
import org.hillel.homework.repository.OrderItemRepository;
import org.hillel.homework.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceTest {
    private OrderRepository orderRepository;
    private OrderService orderService;
    private ProductService productService;
    private OrderItemRepository orderItemRepository;

    private final Product product = new Product(1L, "name", new BigDecimal(1), null);
    private final Order order = new Order(1L, "name", LocalDate.now(), null);
    private final OrderItem orderItem = new OrderItem(1L, product, new BigDecimal(1), order);
    private final OrderItemRequest orderItemRequest = new OrderItemRequest(orderItem.getProduct().getId(), orderItem.getQuantity());
    private final OrderRequest orderRequest = new OrderRequest(order.getCustomerName(), new ArrayList<>(List.of(orderItemRequest)));
    private final OrderItemResponse orderItemResponse = new OrderItemResponse(orderItem.getId(), orderItem.getProduct().getId(), orderItem.getQuantity());
    private final OrderResponse orderResponse = new OrderResponse(order.getId(), order.getCustomerName(), new ArrayList<>(List.of(orderItemResponse)));
    {
        product.setOrderItems(new ArrayList<>(List.of(orderItem)));
        order.setOrderItems(new ArrayList<>(List.of(orderItem)));
    }

    @BeforeEach
    public void setup() {
        orderRepository = mock(OrderRepository.class);
        productService = mock(ProductService.class);
        orderItemRepository = mock(OrderItemRepository.class);
        OrderItemMapper orderItemMapper = new OrderItemMapper();
        orderService = spy(new OrderService(orderRepository, new OrderMapper(orderItemMapper), productService, orderItemMapper, orderItemRepository));
    }

    @Test
    public void getOrderById_validId_orderReturned() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        Order actual = orderService.getOrderById(1L);
        verify(orderRepository).findById(1L);
        assertNotNull(actual);
        assertThat(order).isEqualTo(actual);
    }

    @Test
    public void getOrderById_notFound_ResourceNotFoundExceptionThrown() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> orderService.getOrderById(1L));
    }

    @Test
    public void saveWithId_validIds_saved() {
        doReturn(order).when(orderService).getOrderById(1L);
        when(productService.getProductById(1L)).thenReturn(product);
        when(orderRepository.save(any())).thenReturn(order);
        when(orderItemRepository.save(any())).thenReturn(orderItem);
        assertThat(orderService.save(1L, orderRequest)).isEqualTo(orderResponse);
        verify(orderService).getOrderById(1L);
        verify(productService).getProductById(1L);
        verify(orderItemRepository).save(any());
        verify(orderRepository).save(any());
    }

    @Test
    public void saveWithId_orderNotFound_ResourceNotFoundExceptionThrown() {
        doThrow(ResourceNotFoundException.class).when(orderService).getOrderById(1L);
        assertThrows(ResourceNotFoundException.class, () -> orderService.save(1L, orderRequest));
        verify(orderService).getOrderById(1L);
        verify(orderItemRepository, never()).save(any());
        verify(orderRepository, never()).save(any());
    }

    @Test
    public void saveWithId_productNotFound_ResourceNotFoundExceptionThrown() {
        doReturn(order).when(orderService).getOrderById(1L);
        doThrow(ResourceNotFoundException.class).when(productService).getProductById(1L);
        assertThrows(ResourceNotFoundException.class, () -> orderService.save(1L, orderRequest));
        verify(orderService).getOrderById(1L);
        verify(productService).getProductById(1L);
        verify(orderRepository, never()).save(any());
    }

    @Test
    public void saveWithoutId_validIds_saved() {
        when(productService.getProductById(1L)).thenReturn(product);
        when(orderRepository.save(any())).thenReturn(order);
        when(orderItemRepository.save(any())).thenReturn(orderItem);
        assertThat(orderService.save(orderRequest)).isEqualTo(orderResponse);
        verify(productService).getProductById(1L);
        verify(orderItemRepository).save(any());
        verify(orderRepository).save(any());
    }

    @Test
    public void saveWithoutId_productNotFound_ResourceNotFoundExceptionThrown() {
        doThrow(ResourceNotFoundException.class).when(productService).getProductById(1L);
        assertThrows(ResourceNotFoundException.class, () -> orderService.save(orderRequest));
        verify(orderRepository, never()).save(any());
        verify(productService).getProductById(1L);
        verify(orderRepository, never()).save(any());
    }

    @Test
    public void addItem_validIds_itemAdded() {
        order.setOrderItems(new ArrayList<>());
        doReturn(order).when(orderService).getOrderById(1L);
        when(orderRepository.save(order)).thenReturn(order);
        when(orderItemRepository.save(any())).thenReturn(orderItem);
        assertThat(orderService.addItem(1L, orderItemRequest)).isEqualTo(orderResponse);
        order.setOrderItems(new ArrayList<>(List.of(orderItem)));
        verify(orderItemRepository).save(any());
    }

    @Test
    public void addItem_orderNotFound_ResourceNotFoundExceptionThrown() {
        doThrow(ResourceNotFoundException.class).when(orderService).getOrderById(1L);
        assertThrows(ResourceNotFoundException.class, () -> orderService.addItem(1L, orderItemRequest));
        verify(orderRepository, never()).save(any());
        verify(orderService).getOrderById(1L);
        verify(orderRepository, never()).save(any());
    }

    @Test
    public void addItem_productNotFound_ResourceNotFoundExceptionThrown() {
        doReturn(order).when(orderService).getOrderById(1L);
        when(productService.getProductById(1L)).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> orderService.addItem(1L, orderItemRequest));
        verify(orderService).getOrderById(1L);
        verify(productService).getProductById(1L);
        verify(orderRepository, never()).save(any());
    }

    @Test
    public void removeItem_validIds_itemRemoved() {
        doReturn(order).when(orderService).getOrderById(1L);
        orderService.removeItem(1L, 1L);
        verify(orderService).getOrderById(1L);
        verify(orderItemRepository).delete(orderItem);
    }

    @Test
    public void removeItem_orderNotFound_ResourceNotFoundExceptionThrown() {
        doThrow(ResourceNotFoundException.class).when(orderService).getOrderById(1L);
        assertThrows(ResourceNotFoundException.class, () -> orderService.removeItem(1L, 1L));
        verify(orderService).getOrderById(1L);
        verify(orderItemRepository, never()).deleteById(any());
        verify(orderRepository, never()).save(any());
    }

    @Test
    public void removeItem_orderItemNotFound_ResourceNotFoundExceptionThrown() {
        order.setOrderItems(new ArrayList<>());
        doReturn(order).when(orderService).getOrderById(1L);
        assertThrows(ResourceNotFoundException.class, () -> orderService.removeItem(1L, 1L));
        verify(orderService).getOrderById(1L);
        verify(orderItemRepository, never()).deleteById(any());
        verify(orderRepository, never()).deleteById(any());
    }

    @Test
    public void getOrder_validId_orderResponseReturned() {
        doReturn(order).when(orderService).getOrderById(1L);
        assertThat(orderService.getOrder(1L)).isEqualTo(orderResponse);
        verify(orderService).getOrderById(1L);
    }

    @Test
    public void getOrder_notFound_ResourceNotFoundExceptionThrown() {
        doThrow(ResourceNotFoundException.class).when(orderService).getOrderById(1L);
        assertThrows(ResourceNotFoundException.class, () -> orderService.getOrder(1L));
        verify(orderService).getOrderById(1L);
    }

    @Test
    public void delete_validId_orderDeleted() {
        doReturn(order).when(orderService).getOrderById(1L);
        orderService.delete(1L);
        verify(orderService).getOrderById(1L);
        verify(orderRepository).deleteById(1L);
    }

    @Test
    public void delete_notFound_ResourceNotFoundExceptionThrown() {
        doThrow(ResourceNotFoundException.class).when(orderService).getOrderById(1L);
        assertThrows(ResourceNotFoundException.class, () -> orderService.delete(1L));
        verify(orderService).getOrderById(1L);
        verify(orderRepository, never()).deleteById(any());
    }
}