package com.example.kindalabchallenge;


public class FreightElevator extends Elevator {

    public FreightElevator(int minFloor, int maxFloor) {
        super(minFloor, maxFloor);
    }

    @Override
    protected int getMaxWeightSupportedInKilos() {
        return 3000;
    }

    @Override
    protected boolean canGoToFloor(int floor, boolean keyCard) {
        return true;
    }
}
