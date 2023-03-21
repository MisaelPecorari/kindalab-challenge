package com.example.kindalabchallenge.service;

import com.example.kindalabchallenge.dto.CallDto;
import com.example.kindalabchallenge.exception.InvalidElevatorException;
import com.example.kindalabchallenge.model.Elevator;
import com.example.kindalabchallenge.model.KeyCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

@ExtendWith(MockitoExtension.class)
class ElevatorServiceTest {

    private ElevatorService elevatorService;
    private Elevator elevator;

    @BeforeEach
    void init() {
        Elevator elevator = Mockito.mock(Elevator.class);
        Mockito.when(elevator.getName()).thenReturn("public");
        this.elevator = elevator;
        this.elevatorService = new ElevatorService(Set.of(elevator));
    }

    @Test
    void givenPublicElevator_andCorrectValidation_whenGoTo_thenSuccess() {
        elevatorService.goTo("public", new CallDto(3, 500, KeyCard.USER));
        Mockito.verify(elevator).goTo(3, 500, KeyCard.USER);
    }

    @Test
    void givenInvalidElevator_andCorrectValidation_whenGoTo_thenInvalidElevatorException() {
        Assertions.assertThrows(InvalidElevatorException.class, () ->
                elevatorService.goTo("invalid", new CallDto(10, 500, KeyCard.USER))
        );
    }

    @Test
    void givenPublicElevator_whenCall_thenSuccess() {
        elevatorService.call("public", 20);
        Mockito.verify(elevator).call(20);
    }

    @Test
    void givenInvalidElevator_whenCall_thenInvalidElevatorException() {
        Assertions.assertThrows(InvalidElevatorException.class, () ->
                elevatorService.call("invalid", 20)
        );
    }

    @Test
    void givenPublicElevator_whenGetCurrentFloor_thenSuccess() {
        Mockito.when(elevator.getCurrentFloor()).thenReturn(5);
        int currentFloor = elevatorService.getCurrentFloor("public");
        Assertions.assertEquals(5, currentFloor);
        Mockito.verify(elevator).getCurrentFloor();
    }

    @Test
    void givenInvalidElevator_whenGetCurrentFloor_thenInvalidElevatorException() {
        Assertions.assertThrows(InvalidElevatorException.class, () ->
                elevatorService.getCurrentFloor("invalid")
        );
    }

}