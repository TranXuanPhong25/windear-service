package com.windear.app.exception;

public class BorrowSameBookException extends RuntimeException {
    public BorrowSameBookException(String message) {
        super(message);
    }

    public BorrowSameBookException(String message, Throwable cause) {
        super(message, cause);
    }

    public BorrowSameBookException(Throwable cause) {
        super(cause);
    }
}
