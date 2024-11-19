package com.windear.app.entity;

import com.windear.app.exception.BookNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class Shelf {
    String name;
    List<BookInShelf> books = new ArrayList<>();

    public Shelf() {

    }

    public Shelf(String name) {
        this.name = name;
    }

    public Shelf(String name, List<BookInShelf> books) {
        this.name = name;
        this.books = books;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BookInShelf> getBooks() {
        return books;
    }

    public void setBooks(List<BookInShelf> books) {
        this.books = books;
    }

    public void addBook(BookInShelf book) {
        books.add(book);
    }

    public BookInShelf findBookById(int bookId) {
        return books.stream()
                .filter(book -> book.getBookId() == bookId)
                .findFirst()
                .orElse(null);
    }
}
