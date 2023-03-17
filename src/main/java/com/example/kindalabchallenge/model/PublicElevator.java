package com.example.kindalabchallenge.model;

import com.example.kindalabchallenge.exception.AccessDeniedException;
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
    protected void validateAccess(int floor, KeyCard keyCard) {
        boolean doesNotHaveAccess = floor <= getMinFloor() && keyCard.isNotAdmin() || floor >= getMaxFloor() && keyCard.isNotAdmin();
        if (doesNotHaveAccess) throw new AccessDeniedException();
    }

    @Override
    public String getName() {
        return "public";
    }

}
