package com.example.kindalabchallenge.model;

import com.example.kindalabchallenge.exception.AccessDeniedException;
import com.example.kindalabchallenge.exception.InvalidFloorException;
import com.example.kindalabchallenge.exception.WeightExceededException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class PublicElevatorTest {

    private Elevator elevator;

    @BeforeEach
    void init() {
        elevator = PublicElevator.getInstance();
        elevator.setRequestProcessorThread(Mockito.mock(Thread.class));
    }

    @Test
    void given25Floor_andWeightNoExceeded_andNoKeyCard_whenGoTo_thenSucceed() {
        KeyCard keyCard = KeyCard.USER;
        Assertions.assertTrue(elevator.goTo(25, 500, keyCard));
        Assertions.assertTrue(elevator.goTo(25, 200, keyCard));
        Assertions.assertTrue(elevator.goTo(25, 1, keyCard));
        Assertions.assertTrue(elevator.goTo(25, 0, keyCard));
        Assertions.assertTrue(elevator.goTo(25, 999, keyCard));
    }

    @Test
    void givenBasementOrFloor50_andWeightNoExceeded_andNoKeyCard_whenGoTo_thenFail() {
        KeyCard keyCard = KeyCard.USER;
        Assertions.assertThrows(AccessDeniedException.class, () -> elevator.goTo(-1, 500, keyCard));
        Assertions.assertThrows(AccessDeniedException.class, () -> elevator.goTo(50, 500, keyCard));
    }

    @Test
    void givenBasementOrFloor50_andWeightNoExceeded_andKeyCard_whenGoTo_thenSucceed() {
        KeyCard keyCard = KeyCard.ADMIN;
        Assertions.assertTrue(elevator.goTo(-1, 500, keyCard));
        Assertions.assertTrue(elevator.goTo(50, 500, keyCard));
    }

    @Test
    void givenAnyFloor_andWeightExceeded_andUserKeyCard_whenGoTo_thenThrowException() {
        KeyCard keyCard = KeyCard.USER;
        Assertions.assertThrows(WeightExceededException.class, () -> elevator.goTo(25, 1001, keyCard));
        Assertions.assertThrows(WeightExceededException.class, () ->elevator.goTo(25, 5000, keyCard));
    }

    @Test
    void givenNotExistentFloor_andAdminCard_whenGoTo_thenInvalidFloorExceptionException() {
        KeyCard keyCard = KeyCard.ADMIN;
        Assertions.assertThrows(InvalidFloorException.class, () -> elevator.goTo(-2, 500, keyCard));
        Assertions.assertThrows(InvalidFloorException.class, () ->elevator.goTo(51, 500, keyCard));
    }

}
