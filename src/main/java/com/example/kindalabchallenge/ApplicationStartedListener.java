package com.example.kindalabchallenge;

import com.example.kindalabchallenge.model.FreightElevator;
import com.example.kindalabchallenge.model.PublicElevator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Slf4j
public class ApplicationStartedListener implements ApplicationListener<ApplicationStartedEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        initiateElevators();
    }

    private void initiateElevators() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(PublicElevator.getInstance());
        executorService.submit(FreightElevator.getInstance());
    }

}
