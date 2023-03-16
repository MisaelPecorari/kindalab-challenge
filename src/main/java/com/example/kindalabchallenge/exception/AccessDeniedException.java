package com.example.kindalabchallenge.exception;

public class AccessDeniedException extends ValidationException {

    public AccessDeniedException() {
        super();
    }

    @Override
    protected String getValidationMessage() {
        return "Access denied";
    }
}
