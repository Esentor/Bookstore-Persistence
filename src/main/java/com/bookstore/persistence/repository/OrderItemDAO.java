package com.bookstore.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstore.persistence.model.OrderItem;

public interface OrderItemDAO extends JpaRepository<OrderItem, Long>{

	List<OrderItem> findAllByOrderId(Long id);

}
