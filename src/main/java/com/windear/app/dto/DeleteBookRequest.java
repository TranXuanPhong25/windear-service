package com.windear.app.dto;

public class DeleteBookRequest {
    private String shelfName;
    private int bookId;

    public DeleteBookRequest(String shelfName, int bookId) {
        this.shelfName = shelfName;
        this.bookId = bookId;
    }

    public String getShelfName() {
        return shelfName;
    }

    public void setShelfName(String shelfName) {
        this.shelfName = shelfName;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
