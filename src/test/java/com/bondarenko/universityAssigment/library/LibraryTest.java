package com.bondarenko.universityAssigment.library;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {
    Library library;
    List<Book> libraryBooks;
    Book uniqueBook;

    @BeforeEach
    void setUp() {
        uniqueBook = new Book("1984 (Nineteen Eighty-Four)", " George Orwell", "978-0241453513", 1949);
        libraryBooks = List.of(
                new Book("Harry Potter and the Sorcerer's Stone", " J.K. Rowling", "978-1338878929", 1998),
                new Book("Java For Kids", " J.K. Rowling", "978-1981597314", 2017),
                new Book("The Lord Of The Rings", " J.R.R. Tolkien", "978-0544003415", 1954)
        );
        library = new Library(libraryBooks);
    }

    @Test
    void Add_ValidBook_ShouldChangeSize() {
        int sizeBefore = library.getBooks().size();

        library.add(uniqueBook);
        int sizeAfter = library.getBooks().size();

        assertEquals(sizeBefore + 1, sizeAfter);
    }

    @Test
    void GetBooks_NotEmptyLibrary_ShouldReturnBooks() {

        var actual = library.getBooks();

        assertArrayEquals(actual.toArray(), libraryBooks.toArray());
    }

    @Test
    void FindByName_ExistingBook_ShouldReturnBook() {
        var bookName = uniqueBook.getName();
        library.add(uniqueBook);

        var actual = library.findByName(bookName);

        assertTrue(actual.isPresent());
        assertEquals(actual.get(), uniqueBook);
    }

    @Test
    void FindByName_NotExistingBook_ShouldReturnEmptyOptional() {
        var bookName = "This book does not exist. I hope so";

        var actual = library.findByName(bookName);

        assertFalse(actual.isPresent());
    }

    @Test
    void RemoveByIsbn_ExistingBook_ShouldReturnTrue() {
        var bookIsbn = uniqueBook.getIsbn();
        library.add(uniqueBook);

        var actual = library.removeByIsbn(bookIsbn);

        assertTrue(actual);
    }

    @Test
    void RemoveByIsbn_NotExistingBook_ShouldReturnFalse() {
        var bookIsbn = "Invalid isbn. I hope so";

        var actual = library.removeByIsbn(bookIsbn);

        assertFalse(actual);
    }
}