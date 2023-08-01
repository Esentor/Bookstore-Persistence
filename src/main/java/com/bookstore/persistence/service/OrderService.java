package com.bookstore.persistence.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.persistence.converter.OrderConverter;
import com.bookstore.persistence.dto.OrderDTO;
import com.bookstore.persistence.model.Order;
import com.bookstore.persistence.repository.OrderDAO;

@Service
public class OrderService {

	@Autowired
	private final OrderDAO orderDAO;

	public OrderService(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}

	public OrderDTO createOrder(OrderDTO orderDTO) {
		return OrderConverter.convertToDTO(orderDAO.save(OrderConverter.convertToEntity(orderDTO)));
	}

	public List<OrderDTO> getAllOrders() {
		List<Order> orders = orderDAO.findAll();
		return orders.stream().map(order -> OrderConverter.toOrderDTO(order)).collect(Collectors.toList());
	}

	public OrderDTO getOrderById(Long id) {
		return OrderConverter.toOrderDTO(orderDAO.findById(id).orElse(null));
	}

	public OrderDTO updateOrder(OrderDTO updatedOrder) {
		Order existingOrder = orderDAO.findById(updatedOrder.getId()).orElse(null);
		if (existingOrder == null) {
			return null;
		}
		BeanUtils.copyProperties(updatedOrder, existingOrder, "id");
		return OrderConverter.toOrderDTO(orderDAO.save(existingOrder));
	}

	public boolean deleteOrder(Long id) {
		Order existingOrder = orderDAO.findById(id).orElse(null);
		if (existingOrder == null) {
			return false;
		}
		orderDAO.delete(existingOrder);
		return true;
	}

	
}
