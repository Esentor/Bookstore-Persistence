package com.bookstore.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstore.persistence.model.User;

public interface UserDAO extends JpaRepository<User, Long> {
}

