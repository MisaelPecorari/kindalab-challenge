package com.example.kindalabchallenge;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

@Getter(value = AccessLevel.PROTECTED)
@Slf4j
public abstract class Elevator {

    private static final int MIN_FLOOR = -1;
    private static final int MAX_FLOOR = 50;

    private final int minFloor;
    private final int maxFloor;
    private int currentFloor;
    private final TreeSet<Integer> floorsToVisit;
    private final List<Integer> visitedFloors;
    @Setter
    private Thread requestProcessorThread;
    private boolean isUpwards;
    @Setter
    private int delay;

    protected Elevator() {
        this.minFloor = MIN_FLOOR;
        this.maxFloor = MAX_FLOOR;
        this.currentFloor = 0;
        this.floorsToVisit = new TreeSet<>();
        this.visitedFloors = new ArrayList<>();
        this.isUpwards = true;
        this.delay = 750;
    }

    public boolean goTo(int floor, int weightInKilos, boolean keyCard) {
        validateWeight(weightInKilos);
        validateFloorsRange(floor);
        boolean canGo = canGoToFloor(floor, keyCard);
        if (canGo) call(floor);
        return canGo;
    }

    protected abstract int getMaxWeightSupportedInKilos();

    protected abstract boolean canGoToFloor(int floor, boolean keyCard);

    protected void validateWeight(int weightInKilos) {
        if (weightInKilos > getMaxWeightSupportedInKilos()) throw new WeightExceededException();
    }

    protected void validateFloorsRange(int floor) {
        if (floor < this.minFloor || floor > this.maxFloor) throw new IllegalArgumentException("Floor should be within " + this.minFloor + " and " + this.maxFloor);
    }

    public synchronized void call(int floor) {
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
        Integer nextFloor = this.isUpwards ? nextFloorUpwards() : nextFloorDownwards();
        if (nextFloor != null) floorsToVisit.remove(nextFloor);
        return nextFloor;
    }

    private Integer nextFloorUpwards() {
        Integer nextFloor = this.floorsToVisit.ceiling(this.currentFloor);
        if (nextFloor == null) {
            nextFloor = this.floorsToVisit.floor(this.currentFloor);
            this.isUpwards = false;
        }
        return nextFloor;
    }

    private Integer nextFloorDownwards() {
        Integer nextFloor = this.floorsToVisit.floor(this.currentFloor);
        if (nextFloor == null) {
            nextFloor = this.floorsToVisit.ceiling(this.currentFloor);
            this.isUpwards = true;
        }
        return nextFloor;
    }

    private int calculateDelayInMillis(int nextFloor) {
        int difference = Math.abs(this.currentFloor - nextFloor);
        return difference * this.delay;
    }

}
