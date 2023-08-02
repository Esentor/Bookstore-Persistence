package com.bookstore.persistence.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;

import com.bookstore.persistence.converter.OrderConverter;
import com.bookstore.persistence.dto.OrderDTO;
import com.bookstore.persistence.dto.OrderItemDTO;
import com.bookstore.persistence.dto.UserDTO;
import com.bookstore.persistence.model.Order;
import com.bookstore.persistence.repository.OrderDAO;

public class OrderServiceTest {

    @Mock
    private OrderDAO orderDAO;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateOrder() {
        // Arrange
        OrderDTO orderDTO = createSampleOrderDTO();
        Order orderEntity = OrderConverter.convertToEntity(orderDTO);

        Mockito.when(orderDAO.save(Mockito.any(Order.class))).thenReturn(orderEntity);

        // Act
        OrderDTO createdOrder = orderService.createOrder(orderDTO);

        // Assert
        assertNotNull(createdOrder);
        assertEquals(orderDTO.getUser().getId(), createdOrder.getUser().getId());
        assertEquals(orderDTO.getOrderItems().size(), createdOrder.getOrderItems().size());
    }

    @Test
    public void testCreateOrderWithNullInput() {
        // Arrange
        OrderDTO orderDTO = null;

        // Act
        OrderDTO createdOrder = orderService.createOrder(orderDTO);

        // Assert
        assertNull(createdOrder);
    }

    @Test
    public void testGetAllOrders() {
        // Arrange
        List<Order> orders = createSampleOrderEntities();
        Mockito.when(orderDAO.findAll()).thenReturn(orders);

        // Act
        List<OrderDTO> orderDTOs = orderService.getAllOrders();

        // Assert
        assertNotNull(orderDTOs);
        assertEquals(orders.size(), orderDTOs.size());
    }

    @Test
    public void testGetOrderById() {
        // Arrange
        Long orderId = 1L;
        Order orderEntity = createSampleOrderEntity(orderId);
        Mockito.when(orderDAO.findById(orderId)).thenReturn(java.util.Optional.of(orderEntity));

        // Act
        OrderDTO orderDTO = orderService.getOrderById(orderId);

        // Assert
        assertNotNull(orderDTO);
        assertEquals(orderEntity.getId(), orderDTO.getId());
    }

    @Test
    public void testGetNonExistentOrderById() {
        // Arrange
        Long orderId = 100L;
        Mockito.when(orderDAO.findById(orderId)).thenReturn(java.util.Optional.empty());

        // Act
        OrderDTO orderDTO = orderService.getOrderById(orderId);

        // Assert
        assertNull(orderDTO);
    }

    @Test
    public void testUpdateOrder() {
        // Arrange
        Long orderId = 1L;
        OrderDTO updatedOrderDTO = createSampleOrderDTO();
        updatedOrderDTO.setId(orderId);

        Order existingOrderEntity = createSampleOrderEntity(orderId);
        Mockito.when(orderDAO.findById(orderId)).thenReturn(java.util.Optional.of(existingOrderEntity));

        Order updatedOrderEntity = OrderConverter.convertToEntity(updatedOrderDTO);
        BeanUtils.copyProperties(updatedOrderEntity, existingOrderEntity, "id");
        Mockito.when(orderDAO.save(Mockito.any(Order.class))).thenReturn(existingOrderEntity);

        // Act
        OrderDTO resultOrderDTO = orderService.updateOrder(updatedOrderDTO);

        // Assert
        assertNotNull(resultOrderDTO);
        assertEquals(updatedOrderDTO.getId(), resultOrderDTO.getId());
        assertEquals(updatedOrderDTO.getUser().getId(), resultOrderDTO.getUser().getId());
        assertEquals(updatedOrderDTO.getOrderItems().size(), resultOrderDTO.getOrderItems().size());
    }

    @Test
    public void testUpdateNonExistentOrder() {
        // Arrange
        Long orderId = 100L;
        OrderDTO updatedOrderDTO = createSampleOrderDTO();
        updatedOrderDTO.setId(orderId);

        Mockito.when(orderDAO.findById(orderId)).thenReturn(java.util.Optional.empty());

        // Act
        OrderDTO resultOrderDTO = orderService.updateOrder(updatedOrderDTO);

        // Assert
        assertNull(resultOrderDTO);
    }

    @Test
    public void testDeleteOrder() {
        // Arrange
        Long orderId = 1L;
        Order existingOrderEntity = createSampleOrderEntity(orderId);
        Mockito.when(orderDAO.findById(orderId)).thenReturn(java.util.Optional.of(existingOrderEntity));

        // Act
        boolean isDeleted = orderService.deleteOrder(orderId);

        // Assert
        assertTrue(isDeleted);
    }

    @Test
    public void testDeleteNonExistentOrder() {
        // Arrange
        Long orderId = 100L;
        Mockito.when(orderDAO.findById(orderId)).thenReturn(java.util.Optional.empty());

        // Act
        boolean isDeleted = orderService.deleteOrder(orderId);

        // Assert
        assertFalse(isDeleted);
    }

    // Helper methods for test data creation

    private OrderDTO createSampleOrderDTO() {
        OrderDTO orderDTO = new OrderDTO();
        
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        orderDTO.setUser(userDTO);

        List<OrderItemDTO> orderItems = new ArrayList<>();
        orderItems.add(createSampleOrderItemDTO(1L));
        orderItems.add(createSampleOrderItemDTO(2L));
        orderDTO.setOrderItems(orderItems);

        return orderDTO;
    }

    private List<Order> createSampleOrderEntities() {
        List<Order> orders = new ArrayList<>();
        orders.add(createSampleOrderEntity(1L));
        orders.add(createSampleOrderEntity(2L));
        orders.add(createSampleOrderEntity(3L));
        return orders;
    }

    private Order createSampleOrderEntity(Long id) {
        Order order = new Order();
        order.setId(id);
        return order;
    }

    private OrderItemDTO createSampleOrderItemDTO(Long id) {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setId(id);
        return orderItemDTO;
    }
}
