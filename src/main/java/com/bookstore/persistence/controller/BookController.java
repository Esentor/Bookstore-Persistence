package com.bookstore.persistence.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.persistence.dto.BookDTO;
import com.bookstore.persistence.model.Book;
import com.bookstore.persistence.service.BookService;

@RestController
@RequestMapping("/bookstore/api/dao/books")
public class BookController {

	private final BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@PostMapping
	public ResponseEntity<BookDTO> createBook(@RequestBody Book book) {
		BookDTO createdBook = bookService.createBook(book);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
	}

	@GetMapping
	public ResponseEntity<Object> getAllBooks() {
		List<BookDTO> books = bookService.getAllBooks();
		if (books != null) {
			return ResponseEntity.ok(books);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No books found");
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getBookById(@PathVariable Long id) {
		BookDTO book = bookService.getBookById(id);
		if (book != null) {
			return ResponseEntity.ok(book);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No books found with id: "+id);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
		BookDTO book = bookService.updateBook(id, updatedBook);
		if (book != null) {
			return ResponseEntity.ok(book);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
		boolean deleted = bookService.deleteBook(id);
		if (deleted) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
