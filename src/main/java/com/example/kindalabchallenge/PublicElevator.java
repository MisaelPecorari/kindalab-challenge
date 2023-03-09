package com.example.kindalabchallenge;

public class PublicElevator extends Elevator {

    public PublicElevator(int minFloor, int maxFloor) {
        super(minFloor, maxFloor);
    }

    @Override
    protected int getMaxWeightSupportedInKilos() {
        return 1000;
    }

    @Override
    protected boolean canGoToFloor(int floor, boolean keyCard) {
        return (floor > getMinFloor() || keyCard) && (floor < getMaxFloor() || keyCard);
    }

}
