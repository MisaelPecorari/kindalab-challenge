package com.example.kindalabchallenge.exception;

public class WeightExceededException extends ValidationException {

    private static final String MESSAGE = "Weight limit exceeded. Elevator stopped";

    public WeightExceededException() {
        super(MESSAGE);
    }
}
