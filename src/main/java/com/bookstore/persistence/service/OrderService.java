package com.bookstore.persistence.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.persistence.dto.OrderDTO;
import com.bookstore.persistence.dto.OrderItemDTO;
import com.bookstore.persistence.model.Order;
import com.bookstore.persistence.model.OrderItem;
import com.bookstore.persistence.repository.OrderDAO;

@Service
public class OrderService {

	@Autowired
	private final OrderDAO orderDAO;

	public OrderService(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}

	public OrderDTO createOrder(Order order) {
		return toOrderDTO(orderDAO.save(order));
	}

	public List<OrderDTO> getAllOrders() {
		List<Order> orders = orderDAO.findAll();
		return orders.stream().map(order -> toOrderDTO(order)).collect(Collectors.toList());
	}

	public OrderDTO getOrderById(Long id) {
		return toOrderDTO(orderDAO.findById(id).orElse(null));
	}

	public OrderDTO updateOrder(Long orderId, Order updatedOrder) {
		Order existingOrder = orderDAO.findById(orderId).orElse(null);
		if (existingOrder == null) {
			return null;
		}
		BeanUtils.copyProperties(updatedOrder, existingOrder, "id");
		return toOrderDTO(orderDAO.save(existingOrder));
	}

	public boolean deleteOrder(Long id) {
		Order existingOrder = orderDAO.findById(id).orElse(null);
		if (existingOrder == null) {
			return false;
		}
		orderDAO.delete(existingOrder);
		return true;
	}

	private OrderDTO toOrderDTO(Order order) {
		if (order == null) {
			return null;
		}
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setId(order.getId());
		orderDTO.setUserId(order.getUser().getId());

		List<OrderItemDTO> orderItemDTOs = order.getOrderItems().stream().map(x -> toOrderItemDTO(x))
				.collect(Collectors.toList());
		orderDTO.setOrderItems(orderItemDTOs);

		return orderDTO;
	}

	private OrderItemDTO toOrderItemDTO(OrderItem orderItem) {
		OrderItemDTO orderItemDTO = new OrderItemDTO();
		orderItemDTO.setId(orderItem.getId());
		orderItemDTO.setBook(null);
		return orderItemDTO;
	}
}
