package com.example.spring.exception;

public class NotFoundObjectException extends RuntimeException {

    public NotFoundObjectException(String msg) {
        super(msg);
    }

    public NotFoundObjectException(String msg, Throwable t) {
        super(msg, t);
    }
}
