package com.windear.app.dto;

import java.util.List;

public class DeleteBookRequest {
    private List<String> shelfNames;
    private int bookId;

    public DeleteBookRequest() {

    }

    public DeleteBookRequest(List<String> shelfNames, int bookId) {
        this.shelfNames = shelfNames;
        this.bookId = bookId;
    }

    public List<String> getShelfNames() {
        return shelfNames;
    }

    public void setShelfNames(List<String> shelfNames) {
        this.shelfNames = shelfNames;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
