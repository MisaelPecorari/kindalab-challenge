package com.example.kindalabchallenge.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InvalidFloorException extends ValidationException {

    public InvalidFloorException() {
        super();
    }

    @Override
    protected String getValidationMessage() {
        return "The floor you entered doesn't exist";
    }
}
