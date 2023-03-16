package com.example.kindalabchallenge.model;

import com.example.kindalabchallenge.exception.InvalidFloorException;
import com.example.kindalabchallenge.exception.WeightExceededException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class FreightElevatorTest {

    private Elevator elevator;

    @BeforeEach
    void init() {
        elevator = FreightElevator.getInstance();
        elevator.setRequestProcessorThread(Mockito.mock(Thread.class));
    }

    @Test
    void givenAnyFloor_andWeightNoExceeded_whenGoTo_thenSucceed() {
        KeyCard keyCard = KeyCard.USER;
        Assertions.assertTrue(elevator.goTo(-1, 500, keyCard));
        Assertions.assertTrue(elevator.goTo(10, 200, keyCard));
        Assertions.assertTrue(elevator.goTo(25, 1, keyCard));
        Assertions.assertTrue(elevator.goTo(50, 999, keyCard));
    }

    @Test
    void givenBasementOrFloor50_andWeightNoExceeded_andAdminCard_whenGoTo_thenSucceed() {
        KeyCard keyCard = KeyCard.ADMIN;
        Assertions.assertTrue(elevator.goTo(-1, 500, keyCard));
        Assertions.assertTrue(elevator.goTo(50, 500, keyCard));
    }

    @Test
    void givenAnyFloor_andWeightExceeded_whenGoTo_thenThrowException() {
        KeyCard keyCard = KeyCard.USER;
        Assertions.assertThrows(WeightExceededException.class, () -> elevator.goTo(25, 3001, keyCard));
        Assertions.assertThrows(WeightExceededException.class, () ->elevator.goTo(25, 15000, keyCard));
    }

    @Test
    void givenNotExistentFloor_whenGoTo_thenThrowException() {
        KeyCard keyCard = KeyCard.USER;
        Assertions.assertThrows(InvalidFloorException.class, () -> elevator.goTo(-2, 500, keyCard));
        Assertions.assertThrows(InvalidFloorException.class, () ->elevator.goTo(51, 500, keyCard));
    }

}
