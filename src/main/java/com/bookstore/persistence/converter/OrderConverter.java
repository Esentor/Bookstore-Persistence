package com.bookstore.persistence.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.bookstore.persistence.dto.OrderDTO;
import com.bookstore.persistence.dto.OrderItemDTO;
import com.bookstore.persistence.dto.UserDTO;
import com.bookstore.persistence.model.Book;
import com.bookstore.persistence.model.Order;
import com.bookstore.persistence.model.OrderItem;
import com.bookstore.persistence.model.User;

public class OrderConverter {

	public static OrderDTO toOrderDTO(Order order) {
		if (order == null) {
			return null;
		}
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setId(order.getId());
		orderDTO.setUser(UserConverter.convertToDTO(order.getUser()));

		List<OrderItemDTO> orderItemDTOs = order.getOrderItems().stream().map(x -> toOrderItemDTO(x))
				.collect(Collectors.toList());
		orderDTO.setOrderItems(orderItemDTOs);

		return orderDTO;
	}
	
	public static OrderItemDTO toOrderItemDTO(OrderItem orderItem) {
		OrderItemDTO orderItemDTO = new OrderItemDTO();
		orderItemDTO.setId(orderItem.getId());
		orderItemDTO.setBook(null);
		return orderItemDTO;
	}
	
	public static OrderItem convertToEntity(OrderItemDTO orderItemDTO) {
	    if (orderItemDTO == null) {
	        return null;
	    }

	    OrderItem orderItem = new OrderItem();
	    orderItem.setId(orderItemDTO.getId());

	    // Assuming the BookDTO has a method to convert to Book entity (BookService.convertToEntity)
	    Book book = BookConverter.convertToEntity(orderItemDTO.getBook());
	    orderItem.setBook(book);

	    orderItem.setOrder(convertToEntity(orderItemDTO.getOrder()));

	    return orderItem;
	}

    public static Order convertToEntity(OrderDTO orderDTO) {
    	Order order = new Order();
        order.setId(orderDTO.getId());

        User user = UserConverter.convertToEntity(orderDTO.getUser());
        order.setUser(user);

        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDTO orderItemDTO : orderDTO.getOrderItems()) {
            // Assuming the OrderItemDTO has a method to convert to OrderItem entity
            OrderItem orderItem = convertToEntity(orderItemDTO);
            orderItem.setOrder(order);
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);

        return order;
    }

    public static OrderDTO convertToDTO(Order order) {
    	OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());

        UserDTO userDTO = UserConverter.convertToDTO(order.getUser());
        orderDTO.setUser(userDTO);

        List<OrderItemDTO> orderItemDTOs = new ArrayList<>();
        for (OrderItem orderItem : order.getOrderItems()) {
            // Assuming the OrderItem has a method to convert to OrderItemDTO
            OrderItemDTO orderItemDTO = toOrderItemDTO(orderItem);
            orderItemDTOs.add(orderItemDTO);
        }
        orderDTO.setOrderItems(orderItemDTOs);

        return orderDTO;
    }
}
