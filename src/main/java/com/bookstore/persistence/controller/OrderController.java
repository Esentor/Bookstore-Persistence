package com.bookstore.persistence.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.persistence.dto.OrderDTO;
import com.bookstore.persistence.model.Order;
import com.bookstore.persistence.service.OrderService;

@RestController
@RequestMapping("/bookstore/api/dao/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrderDTO(@RequestBody Order Order) {
        OrderDTO createdOrderDTO = orderService.createOrder(Order);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderDTO);
    }
    
    @GetMapping
    public ResponseEntity<Object> getAllBooks() {
        List<OrderDTO> orders = orderService.getAllOrders();
        if (orders != null) {
            return ResponseEntity.ok(orders);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No orders found");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOrderDTOById(@PathVariable Long id) {
        OrderDTO OrderDTO = orderService.getOrderById(id);
        if (OrderDTO != null) {
            return ResponseEntity.ok(OrderDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No orders found with id:" +id);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrderDTO(@PathVariable Long id, @RequestBody Order updatedOrder) {
        OrderDTO OrderDTO = orderService.updateOrder(id, updatedOrder);
        if (OrderDTO != null) {
            return ResponseEntity.ok(OrderDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderDTO(@PathVariable Long id) {
        boolean deleted = orderService.deleteOrder(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
