package com.windear.app.exception;

public class BookLoanNotFoundException extends RuntimeException {
    public BookLoanNotFoundException(String message) {
        super(message);
    }

    public BookLoanNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookLoanNotFoundException(Throwable cause) {
        super(cause);
    }
}
