package com.example.kindalabchallenge;

import lombok.AccessLevel;
import lombok.Getter;

@Getter(value = AccessLevel.PROTECTED)
public abstract class Elevator {
    private final int minFloor;
    private final int maxFloor;

    protected Elevator(int minFloor, int maxFloor) {
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
    }


    public boolean goTo(int floor, int weightInKilos, boolean keyCard) {
        validateWeight(weightInKilos);
        return canGoToFloor(floor, keyCard);
    }

    protected abstract int getMaxWeightSupportedInKilos();

    protected abstract boolean canGoToFloor(int floor, boolean keyCard);

    protected void validateWeight(int weightInKilos) {
        if (weightInKilos > getMaxWeightSupportedInKilos()) throw new WeightExceededException();
    }

}
