package com.example.kindalabchallenge.model;

import com.example.kindalabchallenge.exception.AccessDeniedException;
import com.example.kindalabchallenge.exception.InvalidFloorException;
import com.example.kindalabchallenge.exception.WeightExceededException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Getter
@Slf4j
public abstract class Elevator implements Runnable {

    private final int minFloor;
    private final int maxFloor;
    @Setter
    private Thread executorThread;
    @Setter
    private int delay;
    private final ElevatorScheduler scheduler;
    private final List<Integer> visitedFloors;

    protected Elevator() {
        this.minFloor = -1;
        this.maxFloor = 50;
        this.delay = 750;
        this.scheduler = new SCANElevatorScheduler();
        this.visitedFloors = new ArrayList<>();
    }

    public void goTo(int floor, int weightInKilos, KeyCard keyCard) {
        validateWeight(weightInKilos);
        validateAccess(floor, keyCard);
        call(floor);
    }

    protected abstract int getMaxWeightSupportedInKilos();

    protected abstract void validateAccess(int floor, KeyCard keyCard) throws AccessDeniedException;

    private void validateWeight(int weightInKilos) {
        if (weightInKilos > getMaxWeightSupportedInKilos()) throw new WeightExceededException();
    }

    private void validateFloor(int floor) {
        if (floor < this.minFloor || floor > this.maxFloor) throw new InvalidFloorException();
    }

    public synchronized void call(int floor) {
        validateFloor(floor);
        boolean added = scheduler.add(floor);
        if (added) {
            log.info("Floor [{}] added to the list", floor);
            if (executorThread.getState() == Thread.State.WAITING) {
                  notify();
            } else {
                executorThread.interrupt();
            }
        }
    }

    private synchronized int nextFloor() {
        Integer nextFloor = scheduler.getNext();
        if (nextFloor == null) waitInFloor();
        return nextFloor == null ? scheduler.getCurrentFloor() : nextFloor;
    }

    private void waitInFloor() {
        log.info("No next floor. Waiting in floor [{}]", scheduler.getCurrentFloor());
        try {
            wait();
        } catch (InterruptedException e) {
            log.error("Something happened", e);
        }
    }

    public abstract String getName();

    @Override
    public void run() {
        while (true) {
            setCurrentThread();
            int nextFloor = nextFloor();
            try {
                move(nextFloor);
            } catch (InterruptedException e) {
                scheduler.add(nextFloor);
            }
        }
    }

    private void setCurrentThread() {
        Thread.currentThread().setName(this.getClass().getSimpleName());
        this.executorThread = Thread.currentThread();
    }

    private void move(int nextFloor) throws InterruptedException {
        if (this.scheduler.getCurrentFloor() == nextFloor) return;
        if (nextFloor > getCurrentFloor()) {
            while (nextFloor > getCurrentFloor()) {
                moveDelta(1);
            }
        } else {
            while (nextFloor < getCurrentFloor()) {
                moveDelta(-1);
            }
        }
        log.info("Floor [{}] served", nextFloor);
        visitedFloors.add(nextFloor);
    }

    private void moveDelta(int delta) throws InterruptedException {
        Thread.sleep(this.delay);
        this.scheduler.setCurrentFloor(getCurrentFloor() + delta);
    }

    public int getCurrentFloor() {
        return this.scheduler.getCurrentFloor();
    }

}
