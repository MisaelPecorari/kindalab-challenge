package com.example.kindalabchallenge.service;

import com.example.kindalabchallenge.dto.CallDto;
import com.example.kindalabchallenge.exception.InvalidElevatorException;
import com.example.kindalabchallenge.model.Elevator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class ElevatorService {

    private final Map<String, Elevator> elevatorMap;

    public ElevatorService(Set<Elevator> elevators) {
        this.elevatorMap = new ConcurrentHashMap<>();
        elevators.forEach(elevator -> elevatorMap.put(elevator.getName(), elevator));
    }

    public void goTo(String elevatorName, CallDto callDto) {
        Elevator elevator = getElevator(elevatorName);
        elevator.goTo(callDto.getFloor(), callDto.getWeightInKilos(), callDto.getKeyCard());
    }

    public void call(String elevatorName, int floor) {
        Elevator elevator = getElevator(elevatorName);
        elevator.call(floor);
    }

    public int getCurrentFloor(String elevatorName) {
        Elevator elevator = getElevator(elevatorName);
        return elevator.getCurrentFloor();
    }

    private Elevator getElevator(String elevatorName) {
        Elevator elevator = elevatorMap.get(elevatorName);
        if (elevator == null) throw new InvalidElevatorException();
        return elevator;
    }
}
