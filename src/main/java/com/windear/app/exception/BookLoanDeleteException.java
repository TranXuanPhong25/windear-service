package com.windear.app.exception;

public class BookLoanDeleteException extends IllegalStateException {
    public BookLoanDeleteException(String message) {
        super(message);
    }

    public BookLoanDeleteException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookLoanDeleteException(Throwable cause) {
        super(cause);
    }
}
