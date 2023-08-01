package com.bookstore.persistence.converter;

import org.springframework.beans.BeanUtils;

import com.bookstore.persistence.dto.BookDTO;
import com.bookstore.persistence.model.Book;

public class BookConverter {
	public static BookDTO convertToDTO(Book book) {
		BookDTO bookDTO = new BookDTO();
		BeanUtils.copyProperties(book, bookDTO);
		return bookDTO;
	}

	public static Book convertToEntity(BookDTO bookDTO) {
		Book book = new Book();
		BeanUtils.copyProperties(bookDTO, book);
		return book;
	}
}
