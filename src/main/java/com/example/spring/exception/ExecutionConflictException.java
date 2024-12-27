package com.example.spring.exception;

import lombok.Getter;

@Getter
public class ExecutionConflictException extends RuntimeException {

    private final ErrorMessage errMsg;

    public ExecutionConflictException(String msg) {
        super();
        errMsg = new ErrorMessage().addError(msg);
    }
}