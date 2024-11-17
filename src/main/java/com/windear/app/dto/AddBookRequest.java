package com.windear.app.dto;

import com.windear.app.entity.BookInShelf;

public class AddBookRequest {
    private String shelfName;
    private BookInShelf book;

    public AddBookRequest(String shelfName, BookInShelf book) {
        this.shelfName = shelfName;
        this.book = book;
    }

    public String getShelfName() {
        return shelfName;
    }

    public void setShelfName(String shelfName) {
        this.shelfName = shelfName;
    }

    public BookInShelf getBook() {
        return book;
    }

    public void setBook(BookInShelf book) {
        this.book = book;
    }
}
