package com.example.kindalabchallenge.model;

public interface ElevatorScheduler {

    Integer getNext();

    boolean add(int floor);

    int getCurrentFloor();

    void setCurrentFloor(int delta);
}
