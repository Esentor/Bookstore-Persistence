package com.bookstore.persistence.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import com.bookstore.persistence.converter.OrderConverter;
import com.bookstore.persistence.converter.UserConverter;
import com.bookstore.persistence.dto.BookDTO;
import com.bookstore.persistence.dto.OrderDTO;
import com.bookstore.persistence.dto.OrderItemDTO;
import com.bookstore.persistence.dto.UserDTO;
import com.bookstore.persistence.model.Order;
import com.bookstore.persistence.model.OrderItem;
import com.bookstore.persistence.repository.OrderDAO;
import com.bookstore.persistence.repository.OrderItemDAO;
import com.bookstore.persistence.repository.UserDAO;

@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
public class OrderServiceTest {

	@Autowired
	private OrderDAO orderDAO;

	@Autowired
	private OrderItemDAO orderItemDAO;

	@Autowired
	private UserDAO userDAO;

	private OrderService orderService;
	private static UserDTO testUser;

	@BeforeEach
	public void setUp() {
		orderService = new OrderService(orderDAO);
		testUser = new UserDTO(1000L, "John", "Doe", "john.doe@example.com");
		userDAO.save(UserConverter.convertToEntity(testUser));
	}

	@AfterEach
	public void tearDownAfterAll() {
		userDAO.deleteById(testUser.getId());
	}

	// Test cases for CRUD operations
	@Test
	public void testCreateOrder() {
		// Create an OrderDTO
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setUser(testUser);

		List<OrderItemDTO> orderItems = new ArrayList<>();
		orderItems.add(new OrderItemDTO(1L, orderDTO, new BookDTO(1L, "Book 1", "Author 1", 19.99)));
		orderItems.add(new OrderItemDTO(2L, orderDTO, new BookDTO(2L, "Book 2", "Author 2", 24.99)));
		orderDTO.setOrderItems(orderItems);

		// Call the order service method to save the order
		OrderDTO createdOrderDTO = orderService.createOrder(orderDTO);

		// Retrieve the saved order from the database
		Optional<Order> retrievedOrder = orderDAO.findById(createdOrderDTO.getId());

		// Assert the result
		assertTrue(retrievedOrder.isPresent());
		assertEquals(createdOrderDTO.getId(), retrievedOrder.get().getId());
		assertEquals("John", retrievedOrder.get().getUser().getFirstName());
		assertEquals("Doe", retrievedOrder.get().getUser().getLastName());
		assertEquals("john.doe@example.com", retrievedOrder.get().getUser().getEmail());
		assertEquals(2, retrievedOrder.get().getOrderItems().size());
	}

	@Test
	public void testGetOrderById() {
		// Prepare the mock data for the order
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setUser(testUser);

		List<OrderItemDTO> orderItems = new ArrayList<>();
		orderItems.add(new OrderItemDTO(1L, orderDTO, new BookDTO(1L, "Book 1", "Author 1", 19.99)));
		orderDTO.setOrderItems(orderItems);

		// Convert OrderDTO to Order and save it to the database
		Order order = OrderConverter.convertToEntity(orderDTO);
		orderDAO.save(order);

		// Call the order service method to retrieve the order by ID
		OrderDTO retrievedOrderDTO = orderService.getOrderById(order.getId());

		// Assert the result
		assertNotNull(retrievedOrderDTO);
		assertEquals(order.getId(), retrievedOrderDTO.getId());
		assertEquals("John", retrievedOrderDTO.getUser().getFirstName());
		assertEquals("Doe", retrievedOrderDTO.getUser().getLastName());
		assertEquals("john.doe@example.com", retrievedOrderDTO.getUser().getEmail());
		assertEquals(1, retrievedOrderDTO.getOrderItems().size());
	}

	@Test
	public void testUpdateOrder() {
		// Prepare the mock data for the order
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setUser(testUser);

		List<OrderItemDTO> orderItems = new ArrayList<>();
		orderItems.add(new OrderItemDTO(1L, orderDTO, new BookDTO(1L, "Book 1", "Author 1", 19.99)));
		orderDTO.setOrderItems(orderItems);

		// Convert OrderDTO to Order and save it to the database
		Order order = OrderConverter.convertToEntity(orderDTO);
		orderDAO.save(order);

		// Modify some properties of the orderDTO
		orderDTO.setUser(new UserDTO(1L, "Updated John", "Doe", "updated.john.doe@example.com"));

		// Call the order service method to update the order
		OrderDTO updatedOrderDTO = orderService.updateOrder(orderDTO);

		// Retrieve the updated order from the database
		Optional<Order> updatedOrder = orderDAO.findById(updatedOrderDTO.getId());

		// Assert the result
		assertTrue(updatedOrder.isPresent());
		assertEquals(updatedOrderDTO.getId(), updatedOrder.get().getId());
		assertEquals("Updated John", updatedOrder.get().getUser().getFirstName());
		assertEquals("Doe", updatedOrder.get().getUser().getLastName());
		assertEquals("updated.john.doe@example.com", updatedOrder.get().getUser().getEmail());
		assertEquals(1, updatedOrder.get().getOrderItems().size());
	}

	@Test
	public void testDeleteOrder() {
		// Prepare the mock data for the order
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setUser(testUser);

		List<OrderItemDTO> orderItems = new ArrayList<>();
		orderItems.add(new OrderItemDTO(1L, orderDTO, new BookDTO(1L, "Book 1", "Author 1", 19.99)));
		orderDTO.setOrderItems(orderItems);

		// Convert OrderDTO to Order and save it to the database
		Order order = OrderConverter.convertToEntity(orderDTO);
		orderDAO.save(order);

		// Call the order service method to delete the order
		orderService.deleteOrder(order.getId());

		// Retrieve the order from the database
		Optional<Order> deletedOrder = orderDAO.findById(order.getId());

		// Assert that the order is deleted (should not be present)
		assertFalse(deletedOrder.isPresent());

		// Also, assert that the associated order items are deleted
		List<OrderItem> orderItemsAfterDeletion = orderItemDAO.findAllByOrderId(order.getId());
		assertTrue(orderItemsAfterDeletion.isEmpty());
	}
}
