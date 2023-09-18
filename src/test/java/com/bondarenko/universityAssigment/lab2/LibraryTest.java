package com.bondarenko.universityAssigment.lab2;

import com.bondarenko.universityAssigment.lab2.items.*;
import com.bondarenko.universityAssigment.lab2.exceptions.*;

import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {
    Library library;
    List<Item> libraryItems;
    List<Patron> libraryPatrons;
    Book uniqueBook;

    @BeforeEach
    void setUp() {
        uniqueBook = new Book("1984 (Nineteen Eighty-Four)", "George Orwell", "978-0241453513", 1949);
        libraryItems = List.of(
                new Book("Harry Potter and the Sorcerer's Stone", " J.K. Rowling", "978-1338878929", 1998),
                new Book("Java For Kids", " J.K. Rowling", "978-1981597314", 2017),
                new Book("The Lord Of The Rings", " J.R.R. Tolkien", "978-0544003415", 1954),
                new DVD("The Lord of the Rings: The Fellowship of the Ring", 178),
                new DVD("Harry Potter and the Deathly Hallows: Part 1", 146)
        );
        libraryPatrons = List.of(
                new Patron("John"),
                new Patron("Walter")
        );
        library = new Library(libraryItems, libraryPatrons);
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

    @Test
    void RegisterPatron_ValidPatron_ShouldNotThrowError() {
        var patron = new Patron("Olexandr");

        assertDoesNotThrow(() -> library.registerPatron(patron));
    }

    @Test
    void LendItem_ValidPatronAndItem_ShouldMakeItemBorrowed() {
        var patron = new Patron("Olexandr");
        library.registerPatron(patron);
        library.add(uniqueBook);

        library.lendItem(patron, uniqueBook);

        assertTrue(uniqueBook.isBorrowed());
    }

    @Test
    void LendItem_UnregisteredPatron_ShouldThrowUnknownPatronException() {
        var patron = new Patron("Unregistered");
        library.add(uniqueBook);

        assertThrows(UnknownPatronException.class, () -> library.lendItem(patron, uniqueBook));
    }

    @Test
    void LendItem_BorrowedItem_ShouldThrowItemCannotBeBorrowedException() {
        var patron1 = new Patron("Patron1");
        var patron2 = new Patron("Patron2");
        library.registerPatron(patron1);
        library.registerPatron(patron2);
        library.add(uniqueBook);

        library.lendItem(patron1, uniqueBook);

        assertThrows(ItemCannotBeBorrowedException.class, () -> library.lendItem(patron2, uniqueBook));
    }

    @Test
    void ReturnItem_ValidPatronAndItem_ShouldMakeItemNotBorrowed() {
        var patron = new Patron("Olexandr");
        library.registerPatron(patron);
        library.add(uniqueBook);

        library.lendItem(patron, uniqueBook);
        library.returnItem(patron, uniqueBook);

        assertFalse(uniqueBook.isBorrowed());
    }

    @Test
    void ListAvailable_ValidPatronAndItem_ShouldReturnNotBorrowedItems() {
        var borrowedItem = uniqueBook;
        uniqueBook.borrowItem();
        library.add(borrowedItem);
        var expected = library.getItems().stream().filter(Predicate.not(Item::isBorrowed)).toArray();

        var actual = library.listAvailable();

        assertArrayEquals(expected, actual.toArray());
    }

    @Test
    void ListBorrowed_ValidPatronAndItem_ShouldReturnBorrowedItems() {
        var borrowedItem = uniqueBook;
        uniqueBook.borrowItem();
        library.add(borrowedItem);
        var expected = library.getItems().stream().filter(Item::isBorrowed).toArray();

        var actual = library.listBorrowed();

        assertArrayEquals(expected, actual.toArray());
    }

    @Test
    void GetItemBorrower_BorrowedItem_ReturnsPatronThatBorrowedItem() {
        var actual = new Patron("Olexandr");
        library.registerPatron(actual);
        library.add(uniqueBook);
        library.lendItem(actual, uniqueBook);

        var expected = library.getItemBorrower(uniqueBook);

        assertTrue(expected.isPresent());
        assertEquals(expected.get(), actual);
    }
}