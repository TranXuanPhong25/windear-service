package com.windear.app.exception;

public class ShelfNameExistException extends IllegalArgumentException {
    public ShelfNameExistException(String message) {
        super(message);
    }

    public ShelfNameExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShelfNameExistException(Throwable cause) {
        super(cause);
    }
}
