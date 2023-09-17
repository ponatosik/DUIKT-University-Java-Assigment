package com.bondarenko.universityAssigment.library;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {
    Library library;
    List<Item> libraryItems;
    Book uniqueBook;

    @BeforeEach
    void setUp() {
        uniqueBook = new Book("1984 (Nineteen Eighty-Four)", " George Orwell", "978-0241453513", 1949);
        libraryItems = List.of(
                new Book("Harry Potter and the Sorcerer's Stone", " J.K. Rowling", "978-1338878929", 1998),
                new Book("Java For Kids", " J.K. Rowling", "978-1981597314", 2017),
                new Book("The Lord Of The Rings", " J.R.R. Tolkien", "978-0544003415", 1954)
        );
        library = new Library(libraryItems);
    }

    @Test
    void Add_ValidBook_ShouldChangeSize() {
        int sizeBefore = library.getItems().size();

        library.add(uniqueBook);
        int sizeAfter = library.getItems().size();

        assertEquals(sizeBefore + 1, sizeAfter);
    }

    @Test
    void GetItems_NotEmptyLibrary_ShouldReturnBooks() {

        var actual = library.getItems();

        assertArrayEquals(actual.toArray(), libraryItems.toArray());
    }

    @Test
    void FindById_ExistingBook_ShouldReturnBook() {
        var bookId = uniqueBook.getUniqueID();
        library.add(uniqueBook);

        var actual = library.findById(bookId);

        assertTrue(actual.isPresent());
        assertEquals(actual.get(), uniqueBook);
    }

    @Test
    void FindById_NotExistingBook_ShouldReturnEmptyOptional() {
        var bookName = "This book ID does not exist. I hope so";

        var actual = library.findById(bookName);

        assertFalse(actual.isPresent());
    }

    @Test
    void RemoveById_ExistingBook_ShouldReturnTrue() {
        var bookIsbn = uniqueBook.getUniqueID();
        library.add(uniqueBook);

        var actual = library.removeById(bookIsbn);

        assertTrue(actual);
    }

    @Test
    void RemoveById_NotExistingBook_ShouldReturnFalse() {
        var bookId = "This book ID does not exist. I hope so";

        var actual = library.removeById(bookId);

        assertFalse(actual);
    }
}