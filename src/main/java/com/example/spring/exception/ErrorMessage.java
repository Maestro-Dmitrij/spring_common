package com.example.spring.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ErrorMessage {

    private List<String> errors = new ArrayList<>();

    public ErrorMessage(String error) {
        errors.add(error);
    }

    public ErrorMessage addError(String error) {
        errors.add(error);
        return this;
    }

    public ErrorMessage addErrors(List<String> errors) {
        this.errors.addAll(errors);
        return this;
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }
}