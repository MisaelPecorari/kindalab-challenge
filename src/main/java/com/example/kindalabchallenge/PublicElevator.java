package com.example.kindalabchallenge;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PublicElevator extends Elevator {

    private static Elevator instance;

    public static Elevator getInstance() {
        if (instance == null) {
            instance = new PublicElevator();
        }
        return instance;
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
