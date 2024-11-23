package com.windear.app.dto;

import com.windear.app.entity.BookInShelf;

import java.util.List;

public class AddBookRequest {
    private List<String> shelfNames;
    private BookInShelf book;

    public AddBookRequest() {

    }

    public AddBookRequest(List<String> shelfNames, BookInShelf book) {
        this.shelfNames = shelfNames;
        this.book = book;
    }

    public List<String> getShelfNames() {
        return shelfNames;
    }

    public void setShelfNames(List<String> shelfNames) {
        this.shelfNames = shelfNames;
    }

    public BookInShelf getBook() {
        return book;
    }

    public void setBook(BookInShelf book) {
        this.book = book;
    }
}
