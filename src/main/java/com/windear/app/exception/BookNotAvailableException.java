package com.windear.app.exception;

public class BookNotAvailableException extends RuntimeException {
    public BookNotAvailableException(String message) {
        super(message);
    }

    public BookNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookNotAvailableException(Throwable cause) {
        super(cause);
    }
}
