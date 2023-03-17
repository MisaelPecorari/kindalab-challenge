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
        Thread publicElevatorThread = createElevatorThread(PublicElevator.getInstance(), "PublicElevatorThread");
        Thread freightElevatorThread = createElevatorThread(FreightElevator.getInstance(), "FreightElevatorThread");
        publicElevatorThread.start();
        freightElevatorThread.start();
    }

    private Thread createElevatorThread(Elevator elevator, String threadName) {
        RequestProcessor requestProcessor = new RequestProcessor(elevator);
        Thread thread = new Thread(requestProcessor, threadName);
        elevator.setRequestProcessorThread(thread);
        return thread;
    }

}
