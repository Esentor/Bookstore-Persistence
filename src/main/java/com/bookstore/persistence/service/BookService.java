package com.bookstore.persistence.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.persistence.converter.BookConverter;
import com.bookstore.persistence.dto.BookDTO;
import com.bookstore.persistence.model.Book;
import com.bookstore.persistence.repository.BookDAO;

@Service
public class BookService {

	@Autowired
	private final BookDAO bookDAO;

	public BookService(BookDAO bookDAO) {
		this.bookDAO = bookDAO;
	}

	public List<BookDTO> getAllBooks() {
		List<Book> books = bookDAO.findAll();
		return books.stream().map(BookConverter::convertToDTO).collect(Collectors.toList());
	}

	public BookDTO getBookById(Long id) {
		return bookDAO.findById(id).map(BookConverter::convertToDTO).orElse(null);
	}

	public BookDTO addBook(BookDTO bookDTO) {
		Book book = BookConverter.convertToEntity(bookDTO);
		Book savedBook = bookDAO.save(book);
		return BookConverter.convertToDTO(savedBook);
	}

	public BookDTO updateBook(BookDTO bookDTO) {
		if (!bookDAO.existsById(bookDTO.getId())) {
			throw new IllegalArgumentException("Book with given ID does not exist");
		}
		Book book = BookConverter.convertToEntity(bookDTO);
		Book savedBook = bookDAO.save(book);
		return BookConverter.convertToDTO(savedBook);
	}

	public void deleteBook(Long id) {
		if (!bookDAO.existsById(id)) {
			throw new IllegalArgumentException("Book with given ID does not exist");
		}
		bookDAO.deleteById(id);
	}

	public boolean bookExists(Long bookId) {
		return bookDAO.existsById(bookId);
	}

}
