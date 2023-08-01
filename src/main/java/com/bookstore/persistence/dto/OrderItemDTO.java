package com.bookstore.persistence.dto;

public class OrderItemDTO {
	private Long id;
	private OrderDTO order;
	private BookDTO book;

	public OrderItemDTO() {
		super();
	}

	public OrderItemDTO(Long id, OrderDTO order, BookDTO book) {
		super();
		this.id = id;
		this.order = order;
		this.book = book;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrderDTO getOrder() {
		return order;
	}

	public void setOrder(OrderDTO order) {
		this.order = order;
	}

	public BookDTO getBook() {
		return book;
	}

	public void setBook(BookDTO book) {
		this.book = book;
	}

}
