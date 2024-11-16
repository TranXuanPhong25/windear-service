package com.windear.app.dto;

public class UpdateShelvesRequest {
    private String shelfName;
    private int bookId;
    private int bookStatus;

    public UpdateShelvesRequest(String shelfName, int bookId, int bookStatus) {
        this.shelfName = shelfName;
        this.bookId = bookId;
        this.bookStatus = bookStatus;
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

    public int getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(int bookStatus) {
        this.bookStatus = bookStatus;
    }
}
