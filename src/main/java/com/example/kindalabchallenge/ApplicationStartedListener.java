package com.example.kindalabchallenge;

import com.example.kindalabchallenge.model.Elevator;
import com.example.kindalabchallenge.model.FreightElevator;
import com.example.kindalabchallenge.model.PublicElevator;
import com.example.kindalabchallenge.model.RequestProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ApplicationStartedListener implements ApplicationListener<ApplicationStartedEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        initiateElevators();
    }

    private void initiateElevators() {
        getPublicElevatorThread().start();
        getFreightElevatorThread().start();
    }

    private Thread getPublicElevatorThread() {
        Elevator publicElevator = PublicElevator.getInstance();
        RequestProcessor requestProcessor = new RequestProcessor(publicElevator);
        Thread thread = new Thread(requestProcessor, "PublicElevatorThread");
        publicElevator.setRequestProcessorThread(thread);
        return thread;
    }

    private Thread getFreightElevatorThread() {
        Elevator freightElevator = FreightElevator.getInstance();
        RequestProcessor requestProcessor = new RequestProcessor(freightElevator);
        Thread thread = new Thread(requestProcessor, "FreightElevatorThread");
        freightElevator.setRequestProcessorThread(thread);
        return thread;

    }
}
