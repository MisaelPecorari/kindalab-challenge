package com.example.kindalabchallenge;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PublicElevatorTest {

    @Test
    void given25Floor_andWeightNoExceeded_andNoKeyCard_whenGoTo_thenSucceed() {
        Elevator elevator = PublicElevator.getInstance();
        Assertions.assertTrue(elevator.goTo(25, 500, false));
        Assertions.assertTrue(elevator.goTo(25, 200, false));
        Assertions.assertTrue(elevator.goTo(25, 1, false));
        Assertions.assertTrue(elevator.goTo(25, 0, false));
        Assertions.assertTrue(elevator.goTo(25, 999, false));
    }

    @Test
    void givenBasementOrFloor50_andWeightNoExceeded_andNoKeyCard_whenGoTo_thenFail() {
        Elevator elevator = PublicElevator.getInstance();
        Assertions.assertFalse(elevator.goTo(-1, 500, false));
        Assertions.assertFalse(elevator.goTo(50, 500, false));
    }

    @Test
    void givenBasementOrFloor50_andWeightNoExceeded_andKeyCard_whenGoTo_thenSucceed() {
        Elevator elevator = PublicElevator.getInstance();
        Assertions.assertTrue(elevator.goTo(-1, 500, true));
        Assertions.assertTrue(elevator.goTo(50, 500, true));
    }

    @Test
    void givenAnyFloor_andWeightExceeded_andNoKeyCard_whenGoTo_thenThrowException() {
        Elevator elevator = PublicElevator.getInstance();
        Assertions.assertThrows(WeightExceededException.class, () -> elevator.goTo(25, 1001, false));
        Assertions.assertThrows(WeightExceededException.class, () ->elevator.goTo(25, 5000, false));
    }

    @Test
    void givenNotExistentFloor_andKeyCard_whenGoTo_thenThrowException() {
        Elevator elevator = PublicElevator.getInstance();
        Assertions.assertThrows(IllegalArgumentException.class, () -> elevator.goTo(-2, 500, true));
        Assertions.assertThrows(IllegalArgumentException.class, () ->elevator.goTo(51, 500, true));
    }


}
