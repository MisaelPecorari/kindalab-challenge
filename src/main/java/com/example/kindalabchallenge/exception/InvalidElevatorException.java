package com.example.kindalabchallenge.exception;

public class InvalidElevatorException extends ValidationException {

    private static final String MESSAGE = "The elevator selected doesn't exist";

    public InvalidElevatorException() {
        super(MESSAGE);
    }
}
