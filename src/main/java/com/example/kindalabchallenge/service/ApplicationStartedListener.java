package com.example.kindalabchallenge.service;

import com.example.kindalabchallenge.model.FreightElevator;
import com.example.kindalabchallenge.model.PublicElevator;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

@Component
@AllArgsConstructor
public class ApplicationStartedListener implements ApplicationListener<ApplicationStartedEvent> {

    private final ExecutorService executorService;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        initiateElevators();
    }

    private void initiateElevators() {
        executorService.submit(PublicElevator.getInstance());
        executorService.submit(FreightElevator.getInstance());
    }
}
