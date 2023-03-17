package com.example.kindalabchallenge.model;

import com.example.kindalabchallenge.exception.AccessDeniedException;
import com.example.kindalabchallenge.exception.InvalidFloorException;
import com.example.kindalabchallenge.exception.WeightExceededException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

@Getter
@Slf4j
public abstract class Elevator implements Runnable {

    private final int minFloor;
    private final int maxFloor;
    private int currentFloor;
    private final TreeSet<Integer> floorsToVisit;
    private final List<Integer> visitedFloors;
    @Setter(value = AccessLevel.PROTECTED)
    private Thread requestProcessorThread;
    private Direction direction;
    @Setter(value = AccessLevel.PROTECTED)
    private int delay;

    protected Elevator() {
        this.minFloor = -1;
        this.maxFloor = 50;
        this.currentFloor = 0;
        this.floorsToVisit = new TreeSet<>();
        this.visitedFloors = new ArrayList<>();
        this.direction = Direction.UPWARDS;
        this.delay = 750;
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
        if (floor == this.currentFloor) return;
        boolean added = floorsToVisit.add(floor);
        if (added) {
            log.info("Floor [{}] added to the list", floor);
            if (requestProcessorThread.getState() == Thread.State.WAITING) {
                  notify();
            } else {
                requestProcessorThread.interrupt();
            }
        }
    }

    public synchronized Integer nextFloor() {
        Integer nextFloor = calculateNext();
        if (nextFloor == null) waitInFloor();
        return nextFloor == null ? this.currentFloor : nextFloor;
    }

    public void move(int nextFloor) throws InterruptedException {
        log.info("Next floor is [{}]", nextFloor);
        int delayInMillis = calculateDelayInMillis(nextFloor);
        Thread.sleep(delayInMillis); //simulates delay
        this.currentFloor = nextFloor;
        visitedFloors.add(this.currentFloor);
    }

    private void waitInFloor() {
        log.info("No next floor. Waiting in floor [{}]", this.currentFloor);
        try {
            wait();
        } catch (InterruptedException e) {
            log.error("Something happened", e);
        }
    }

    private Integer calculateNext() {
        Integer nextFloor = this.direction == Direction.UPWARDS ? nextFloorUpwards() : nextFloorDownwards();
        if (nextFloor != null) floorsToVisit.remove(nextFloor);
        return nextFloor;
    }

    private Integer nextFloorUpwards() {
        Integer nextFloor = this.floorsToVisit.ceiling(this.currentFloor);
        if (nextFloor == null) {
            nextFloor = this.floorsToVisit.floor(this.currentFloor);
            this.direction = Direction.DOWNWARDS;
        }
        return nextFloor;
    }

    private Integer nextFloorDownwards() {
        Integer nextFloor = this.floorsToVisit.floor(this.currentFloor);
        if (nextFloor == null) {
            nextFloor = this.floorsToVisit.ceiling(this.currentFloor);
            this.direction = Direction.UPWARDS;
        }
        return nextFloor;
    }

    private int calculateDelayInMillis(int nextFloor) {
        int difference = Math.abs(this.currentFloor - nextFloor);
        return difference * this.delay;
    }

    private enum Direction {
        UPWARDS, DOWNWARDS
    }

    public abstract String getName();

    @Override
    public void run() {
        while (true) {
            this.requestProcessorThread = Thread.currentThread();
            Integer nextFloor = nextFloor();
            try {
                if (nextFloor != this.currentFloor) {
                    move(nextFloor);
                }
            } catch (InterruptedException e) {
                if (this.currentFloor != nextFloor) {
                    floorsToVisit.add(nextFloor);
                }
            }
        }
    }

}
