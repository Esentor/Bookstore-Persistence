package com.bookstore.persistence.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.persistence.dto.BookDTO;
import com.bookstore.persistence.model.Book;
import com.bookstore.persistence.repository.BookDAO;

@Service
public class BookService {

	@Autowired
	private BookDAO bookDao;

	public BookDTO createBook(Book book) {
		return toBookDTO(bookDao.save(book));
	}

	public BookDTO getBookById(Long id) {
		return toBookDTO(bookDao.findById(id).orElse(null));
	}

	public List<BookDTO> getAllBooks() {
		List<Book> books = bookDao.findAll();
		return books.stream().map(book -> toBookDTO(book)).collect(Collectors.toList());
	}

	public BookDTO updateBook(Long bookId, Book updatedBook) {
		Book existingBook = bookDao.findById(bookId).orElse(null);
		if (existingBook == null) {
			return null;
		}
		BeanUtils.copyProperties(updatedBook, existingBook, "id");
		return toBookDTO(bookDao.save(existingBook));
	}

	public boolean deleteBook(Long id) {
		Book existingBook = bookDao.findById(id).orElse(null);
		if (existingBook == null) {
			return false;
		}
		bookDao.delete(existingBook);
		return true;
	}

	public BookDTO toBookDTO(Book book) {
		if (book == null) {
			return null;
		}
		BookDTO bookDTO = new BookDTO();
		bookDTO.setId(book.getId());
		bookDTO.setTitle(book.getTitle());
		bookDTO.setAuthor(book.getAuthor());
		bookDTO.setPrice(book.getPrice());
		return bookDTO;
	}
}
