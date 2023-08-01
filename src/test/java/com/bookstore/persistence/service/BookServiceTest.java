package com.bookstore.persistence.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import com.bookstore.persistence.dto.BookDTO;
import com.bookstore.persistence.model.Book;
import com.bookstore.persistence.repository.BookDAO;

@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
public class BookServiceTest {

    @Autowired
    private BookDAO bookDAO;

    private BookService bookService;

    @BeforeEach
    public void setUp() {
        bookService = new BookService(bookDAO);
    }

    @Test
    public void testCreateBook() {
        // Test creating a new book
        BookDTO newBookDTO = new BookDTO();
        newBookDTO.setTitle("New Book");
        newBookDTO.setAuthor("New Author");
        newBookDTO.setPrice(29.99);

        BookDTO createdBookDTO = bookService.addBook(newBookDTO);

        assertNotNull(createdBookDTO.getId());
        assertEquals("New Book", createdBookDTO.getTitle());
        assertEquals("New Author", createdBookDTO.getAuthor());
        assertEquals(29.99, createdBookDTO.getPrice(), 0.01);
    }

    @Test
    public void testGetBookById() {
        // Test retrieving a book by its ID
        BookDTO newBookDTO = new BookDTO();
        newBookDTO.setTitle("New Book");
        newBookDTO.setAuthor("New Author");
        newBookDTO.setPrice(29.99);

        BookDTO createdBookDTO = bookService.addBook(newBookDTO);

        BookDTO retrievedBookDTO = bookService.getBookById(createdBookDTO.getId());

        assertNotNull(retrievedBookDTO);
        assertEquals(createdBookDTO.getId(), retrievedBookDTO.getId());
        assertEquals(createdBookDTO.getTitle(), retrievedBookDTO.getTitle());
        assertEquals(createdBookDTO.getAuthor(), retrievedBookDTO.getAuthor());
        assertEquals(createdBookDTO.getPrice(), retrievedBookDTO.getPrice(), 0.01);
    }

    @Test
    public void testUpdateBook() {
        // Test updating an existing book
        BookDTO newBookDTO = new BookDTO();
        newBookDTO.setTitle("New Book");
        newBookDTO.setAuthor("New Author");
        newBookDTO.setPrice(29.99);

        BookDTO createdBookDTO = bookService.addBook(newBookDTO);

        newBookDTO.setId(createdBookDTO.getId());
        newBookDTO.setTitle("Updated Book");
        newBookDTO.setAuthor("Updated Author");
        newBookDTO.setPrice(24.99);

        BookDTO updatedBookDTO = bookService.updateBook(newBookDTO);

        assertNotNull(updatedBookDTO);
        assertEquals(newBookDTO.getId(), updatedBookDTO.getId());
        assertEquals("Updated Book", updatedBookDTO.getTitle());
        assertEquals("Updated Author", updatedBookDTO.getAuthor());
        assertEquals(24.99, updatedBookDTO.getPrice(), 0.01);
    }

    @Test
    public void testDeleteBook() {
        // Test deleting an existing book
        BookDTO newBookDTO = new BookDTO();
        newBookDTO.setTitle("New Book");
        newBookDTO.setAuthor("New Author");
        newBookDTO.setPrice(29.99);

        BookDTO createdBookDTO = bookService.addBook(newBookDTO);

        bookService.deleteBook(createdBookDTO.getId());

        List<Book> books = bookDAO.findAll();
        assertEquals(0, books.size());
    }
}
