package com.example.kindalabchallenge.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InvalidFloorException extends ValidationException {

    private static final String MESSAGE = "The floor you entered doesn't exist";

    public InvalidFloorException() {
        super(MESSAGE);
    }
}
