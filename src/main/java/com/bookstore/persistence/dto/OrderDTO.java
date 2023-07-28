package com.bookstore.persistence.dto;

import java.util.ArrayList;
import java.util.List;

public class OrderDTO {
	private Long id;
	private Long userId;
	private List<OrderItemDTO> orderItems;

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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<OrderItemDTO> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItemDTO> orderItems) {
		this.orderItems = orderItems;
	}

}
