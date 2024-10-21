package com.windear.app.exception;

import com.windear.app.entity.Review;

public class ReviewNotFoundException extends RuntimeException{
    public ReviewNotFoundException(String message) {
        super(message);
    }

    public ReviewNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReviewNotFoundException(Throwable cause) {
        super(cause);
    }
}
