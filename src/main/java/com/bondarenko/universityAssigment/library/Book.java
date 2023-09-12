package com.bondarenko.universityAssigment.library;

public class Book {
    private final String name;
    private final String author;
    private final String isbn;
    private final int publishingYear;

    public Book(String name, String author, String isbn, int publishingYear) {
        this.name = name;
        this.author = author;
        this.isbn = isbn;
        this.publishingYear = publishingYear;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getPublishingYear() {
        return publishingYear;
    }
}
