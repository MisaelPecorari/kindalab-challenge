package com.example.kindalabchallenge.exception;

public class AccessDeniedException extends ValidationException {

    private static final String MESSAGE = "Access denied";

    public AccessDeniedException() {
        super(MESSAGE);
    }

}
