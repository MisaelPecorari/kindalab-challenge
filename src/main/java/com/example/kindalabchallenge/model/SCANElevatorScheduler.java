package com.example.kindalabchallenge.model;

import lombok.extern.slf4j.Slf4j;

import java.util.TreeSet;

@Slf4j
public class SCANElevatorScheduler implements ElevatorScheduler {

    private Direction direction;
    private final TreeSet<Integer> floorsToVisit;
    private int currentFloor;

    public SCANElevatorScheduler() {
        this.direction = Direction.UPWARDS;
        this.floorsToVisit = new TreeSet<>();
        this.currentFloor = 0;
    }

    @Override
    public Integer getNext() {
        Integer nextFloor = this.direction == Direction.UPWARDS ? nextFloorUpwards() : nextFloorDownwards();
        if (nextFloor != null) floorsToVisit.remove(nextFloor);
        return nextFloor;
    }

    @Override
    public boolean add(int floor) {
        if (floor == this.currentFloor) return false;
        return this.floorsToVisit.add(floor);
    }

    @Override
    public int getCurrentFloor() {
        return this.currentFloor;
    }

    @Override
    public void setCurrentFloor(int currentFloor) {
        Direction direction = currentFloor > this.currentFloor ? Direction.UPWARDS : Direction.DOWNWARDS;
        this.currentFloor = currentFloor;
        this.direction = direction;
        log.info("The elevator is in floor [{}]", this.currentFloor);
    }

    private Integer nextFloorUpwards() {
        Integer nextFloor = this.floorsToVisit.ceiling(this.currentFloor);
        if (nextFloor == null) {
            nextFloor = this.floorsToVisit.floor(this.currentFloor);
        }
        return nextFloor;
    }

    private Integer nextFloorDownwards() {
        Integer nextFloor = this.floorsToVisit.floor(this.currentFloor);
        if (nextFloor == null) {
            nextFloor = this.floorsToVisit.ceiling(this.currentFloor);
        }
        return nextFloor;
    }

    private enum Direction {
        UPWARDS, DOWNWARDS
    }

}
