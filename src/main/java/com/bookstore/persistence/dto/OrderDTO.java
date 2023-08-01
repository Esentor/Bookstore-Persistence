package com.bookstore.persistence.dto;

import java.util.ArrayList;
import java.util.List;

public class OrderDTO {
	private Long id;
	private UserDTO user;
	private List<OrderItemDTO> orderItems;
	
	public OrderDTO() {
		super();
	}

	public OrderDTO(Long id, UserDTO user, List<OrderItemDTO> orderItems) {
		super();
		this.id = id;
		this.user = user;
		this.orderItems = orderItems;
	}

	public void addOrderItem(OrderItemDTO orderItem) {
		if (orderItems == null) {
			orderItems = new ArrayList<>();
		}
		orderItems.add(orderItem);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public List<OrderItemDTO> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItemDTO> orderItems) {
		this.orderItems = orderItems;
	}

}
