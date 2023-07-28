package com.bookstore.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstore.persistence.model.OrderItem;

public interface OrderItemDAO extends JpaRepository<OrderItem, Long>{

}
