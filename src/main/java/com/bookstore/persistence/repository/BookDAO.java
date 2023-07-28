package com.bookstore.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstore.persistence.model.Book;

public interface BookDAO extends JpaRepository<Book, Long> {
}
