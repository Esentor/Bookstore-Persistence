package com.bookstore.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstore.persistence.model.Order;

public interface OrderDAO extends JpaRepository<Order, Long> {
}

