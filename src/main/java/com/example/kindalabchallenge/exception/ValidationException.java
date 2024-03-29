package com.example.kindalabchallenge.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
        log.warn(message);
    }
}
