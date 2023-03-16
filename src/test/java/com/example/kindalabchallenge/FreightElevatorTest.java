package com.example.kindalabchallenge;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FreightElevatorTest {

    @Test
    void givenAnyFloor_andWeightNoExceeded_whenGoTo_thenSucceed() {
        Elevator elevator = FreightElevator.getInstance();
        Assertions.assertTrue(elevator.goTo(-1, 500, false));
        Assertions.assertTrue(elevator.goTo(10, 200, false));
        Assertions.assertTrue(elevator.goTo(25, 1, false));
        Assertions.assertTrue(elevator.goTo(50, 999, false));
    }

    @Test
    void givenBasementOrFloor50_andWeightNoExceeded_whenGoTo_thenSucceed() {
        Elevator elevator = FreightElevator.getInstance();
        Assertions.assertTrue(elevator.goTo(-1, 500, true));
        Assertions.assertTrue(elevator.goTo(50, 500, true));
    }

    @Test
    void givenAnyFloor_andWeightExceeded_whenGoTo_thenThrowException() {
        Elevator elevator = FreightElevator.getInstance();
        Assertions.assertThrows(WeightExceededException.class, () -> elevator.goTo(25, 3001, false));
        Assertions.assertThrows(WeightExceededException.class, () ->elevator.goTo(25, 15000, false));
    }

    @Test
    void givenNotExistentFloor_whenGoTo_thenThrowException() {
        Elevator elevator = FreightElevator.getInstance();
        Assertions.assertThrows(IllegalArgumentException.class, () -> elevator.goTo(-2, 500, false));
        Assertions.assertThrows(IllegalArgumentException.class, () ->elevator.goTo(51, 500, false));
    }

}
