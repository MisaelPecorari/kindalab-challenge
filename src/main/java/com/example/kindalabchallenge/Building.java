package com.example.kindalabchallenge;

public class Building {

    private final Elevator publicElevator;
    private final Elevator freightElevator;

    public Building() {
        publicElevator = PublicElevator.getInstance();
        freightElevator = FreightElevator.getInstance();
    }

    public boolean goByPublicElevator(int floor, int weightInKilos, boolean keyCard) {
        return publicElevator.goTo(floor, weightInKilos, keyCard);
    }

    public boolean goByFreightElevator(int floor, int weightInKilos) {
        return freightElevator.goTo(floor, weightInKilos, false);
    }

    public void callPublicElevator(int floor) {
        publicElevator.call(floor);
    }

    public void callFreightElevator(int floor) {
        freightElevator.call(floor);
    }

    public int getCurrentFloorPublicElevator() {
        return publicElevator.getCurrentFloor();
    }

    public int getCurrentFloorFreightElevator() {
        return freightElevator.getCurrentFloor();
    }

}
