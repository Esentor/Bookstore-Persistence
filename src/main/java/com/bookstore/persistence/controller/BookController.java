package com.bookstore.persistence.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.bookstore.persistence.service.BookService;

@RestController
@RequestMapping("/bookstore/api/dao/books")
public class BookController {

	@Autowired
	private final BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping
	public ResponseEntity<List<BookDTO>> getAllBooks() {
		List<BookDTO> books = bookService.getAllBooks();
		return new ResponseEntity<>(books, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
		BookDTO book = bookService.getBookById(id);
		if (book == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(book, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<BookDTO> addBook(@RequestBody BookDTO bookDTO) {
		BookDTO createdBook = bookService.addBook(bookDTO);
		return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
		if (!bookService.bookExists(id)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		if (!bookDTO.getId().equals(id)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		BookDTO updatedBook = bookService.updateBook(bookDTO);
		return new ResponseEntity<>(updatedBook, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
		if (!bookService.bookExists(id)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		bookService.deleteBook(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
