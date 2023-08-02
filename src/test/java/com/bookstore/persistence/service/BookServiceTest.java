package com.bookstore.persistence.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.bookstore.persistence.dto.BookDTO;
import com.bookstore.persistence.model.Book;
import com.bookstore.persistence.repository.BookDAO;

public class BookServiceTest {

    @Mock
    private BookDAO bookDAO;

    @InjectMocks
    private BookService bookService;

    @SuppressWarnings("deprecation")
	@BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllBooksValidDataReturnsAllBooks() {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "Book 1", "Author 1", 29.99));
        books.add(new Book(2L, "Book 2", "Author 2", 19.99));
        when(bookDAO.findAll()).thenReturn(books);

        List<BookDTO> result = bookService.getAllBooks();
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetBookByIdValidIdReturnsBookDTO() {
        Long bookId = 1L;
        Book book = new Book(bookId, "Book 1", "Author 1", 29.99);
        when(bookDAO.findById(bookId)).thenReturn(Optional.of(book));

        BookDTO result = bookService.getBookById(bookId);
        assertNotNull(result);
        assertEquals(bookId, result.getId());
        assertEquals("Book 1", result.getTitle());
        assertEquals("Author 1", result.getAuthor());
        assertEquals(29.99, result.getPrice());
    }

    @Test
    public void testGetBookByIdInvalidIdReturnsNull() {
        Long bookId = 1L;
        when(bookDAO.findById(bookId)).thenReturn(Optional.empty());

        BookDTO result = bookService.getBookById(bookId);
        assertNull(result);
    }

    @Test
    public void testAddBookValidBookDTOCreatesNewBook() {
        BookDTO bookDTO = new BookDTO(null, "Book 1", "Author 1", 29.99);
        when(bookDAO.save(any(Book.class))).thenReturn(new Book(1L, "Book 1", "Author 1", 29.99));

        BookDTO result = bookService.addBook(bookDTO);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("Book 1", result.getTitle());
        assertEquals("Author 1", result.getAuthor());
        assertEquals(29.99, result.getPrice());
    }

    @Test
    public void testUpdateBookValidBookDTOUpdatesExistingBook() {
        Long bookId = 1L;
        Book existingBook = new Book(bookId, "Book 1", "Author 1", 29.99);
        when(bookDAO.existsById(bookId)).thenReturn(true);
        when(bookDAO.save(any(Book.class))).thenReturn(existingBook);

        BookDTO bookDTO = new BookDTO(bookId, "Updated Book", "Updated Author", 19.99);
        BookDTO result = bookService.updateBook(bookDTO);

        assertNotNull(result);
        assertEquals(bookId, result.getId());
        assertEquals("Updated Book", result.getTitle());
        assertEquals("Updated Author", result.getAuthor());
        assertEquals(19.99, result.getPrice());
    }

    @Test
    public void testUpdateBookInvalidBookDTOThrowsIllegalArgumentException() {
        Long bookId = 1L;
        when(bookDAO.existsById(bookId)).thenReturn(false);

        BookDTO bookDTO = new BookDTO(bookId, "Updated Book", "Updated Author", 19.99);
        assertThrows(IllegalArgumentException.class, () -> bookService.updateBook(bookDTO));
    }

    @Test
    public void testDeleteBookValidIdDeletesExistingBook() {
        Long bookId = 1L;
        when(bookDAO.existsById(bookId)).thenReturn(true);

        assertDoesNotThrow(() -> bookService.deleteBook(bookId));
        verify(bookDAO, times(1)).deleteById(bookId);
    }

    @Test
    public void testDeleteBookInvalidIdThrowsIllegalArgumentException() {
        Long bookId = 1L;
        when(bookDAO.existsById(bookId)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> bookService.deleteBook(bookId));
        verify(bookDAO, never()).deleteById(bookId);
    }

    @Test
    public void testBookExistsExistingBookIdReturnsTrue() {
        Long bookId = 1L;
        when(bookDAO.existsById(bookId)).thenReturn(true);

        boolean result = bookService.bookExists(bookId);
        assertTrue(result);
    }

    @Test
    public void testBookExistsNonExistingBookIdReturnsFalse() {
        Long bookId = 1L;
        when(bookDAO.existsById(bookId)).thenReturn(false);

        boolean result = bookService.bookExists(bookId);
        assertFalse(result);
    }

}
