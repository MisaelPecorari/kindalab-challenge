package com.example.kindalabchallenge.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FreightElevator extends Elevator {

    private static Elevator instance;

    public static Elevator getInstance() {
        if (instance == null) {
            instance = new FreightElevator();
        }
        return instance;
    }

    @Override
    protected int getMaxWeightSupportedInKilos() {
        return 3000;
    }

    @Override
    protected void validateAccess(int floor, KeyCard keyCard) {
        //everybody has access
    }
}
