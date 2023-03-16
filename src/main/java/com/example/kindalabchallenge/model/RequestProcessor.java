package com.example.kindalabchallenge.model;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestProcessor implements Runnable {

    private final Elevator elevator;

    public RequestProcessor(Elevator elevator) {
        this.elevator = elevator;
    }

    @Override
    public void run() {
        while (true) {
            Integer nextFloor = elevator.nextFloor();
            try {
                if (nextFloor != elevator.getCurrentFloor()) {
                    elevator.move(nextFloor);
                }
            } catch (InterruptedException e) {
                if(elevator.getCurrentFloor() != nextFloor) {
                    elevator.getFloorsToVisit().add(nextFloor);
                }
            }
        }
    }

}
