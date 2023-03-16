package com.example.kindalabchallenge.exception;

public class WeightExceededException extends ValidationException {

    public WeightExceededException() {
        super();
    }

    @Override
    protected String getValidationMessage() {
        return "Weight limit exceeded. Elevator stopped";
    }
}
