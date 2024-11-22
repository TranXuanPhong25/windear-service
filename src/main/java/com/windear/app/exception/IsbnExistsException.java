package com.windear.app.exception;

public class IsbnExistsException extends RuntimeException {
    public IsbnExistsException(String message) {
        super(message);
    }

    public IsbnExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public IsbnExistsException(Throwable cause) {
        super(cause);
    }
}
