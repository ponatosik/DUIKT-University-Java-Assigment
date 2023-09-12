package com.bondarenko.universityAssigment.library;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Library {
    private final List<Book> books;

    public Library() {
        this.books = new ArrayList<>();
    }

    public Library(List<Book> books) {
        this.books = new ArrayList<>(books);
    }

    public void add(Book book){
        books.add(book);
    }

    public List<Book> getBooks(){
        return Collections.unmodifiableList(books);
    }

    public Optional<Book> findByName(String name) {
        return books.stream().filter(book -> book.getName().equals(name)).findAny();
    }

    public boolean removeByIsbn(String isbn){
        return books.removeIf(book -> book.getIsbn().equals(isbn));
    }
}
