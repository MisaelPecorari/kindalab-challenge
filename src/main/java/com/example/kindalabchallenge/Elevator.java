package com.example.kindalabchallenge;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter(value = AccessLevel.PROTECTED)
@Slf4j
public abstract class Elevator {
    private final int minFloor;
    private final int maxFloor;
    private int currentFloor;

    protected Elevator(int minFloor, int maxFloor) {
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
        this.currentFloor = 0;
    }

    public boolean goTo(int floor, int weightInKilos, boolean keyCard) {
        validateWeight(weightInKilos);
        boolean canGo = canGoToFloor(floor, keyCard);
        if (canGo) this.currentFloor = floor;
        return canGo;
    }

    protected abstract int getMaxWeightSupportedInKilos();

    protected abstract boolean canGoToFloor(int floor, boolean keyCard);

    protected void validateWeight(int weightInKilos) {
        if (weightInKilos > getMaxWeightSupportedInKilos()) throw new WeightExceededException();
    }

    public void call(int floor) {
        if (this.currentFloor == floor) {
            return;
        }
        log.info("Calling elevator to {} floor", floor);
        this.currentFloor = floor;
    }

    public int getCurrentFloor() {
        log.info("The current floor is {}", currentFloor);
        return this.currentFloor;
    }

}
