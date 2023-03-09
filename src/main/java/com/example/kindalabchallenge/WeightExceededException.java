package com.example.kindalabchallenge;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WeightExceededException extends RuntimeException {

    private static final String ALARM_MESSAGE = "Weight limit exceeded. Stopping elevator";

    public WeightExceededException() {
        super(ALARM_MESSAGE);
        log.warn(ALARM_MESSAGE);
    }
}
