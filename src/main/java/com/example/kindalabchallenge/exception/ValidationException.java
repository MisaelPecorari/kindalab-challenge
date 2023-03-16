package com.example.kindalabchallenge.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ValidationException extends RuntimeException {

    public ValidationException() {
        log.warn(getValidationMessage());
    }

    protected abstract String getValidationMessage();
}
