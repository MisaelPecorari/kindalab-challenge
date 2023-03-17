package com.example.kindalabchallenge.model;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ElevatorTest {

    @Test
    public void givenCallFromDifferentFloors_andGoToDifferentFloors_whenSchedule_thenSuccess() {
        int delayInMillis = 20;
        KeyCard keyCard = KeyCard.ADMIN;
        Elevator elevator = PublicElevator.getInstance();
        elevator.setDelay(delayInMillis);
        Assertions.assertEquals(0, elevator.getCurrentFloor());

        elevator.call(10);
        elevator.call(5);
        elevator.call(20);

        Awaitility.await().until(() -> {
            if (elevator.getCurrentFloor() == 5) {
                elevator.goTo(2, 500, keyCard);
                elevator.goTo(25, 500, keyCard);
                return true;
            }
            return false;
        });

        Awaitility.await().until(() -> {
            if (elevator.getCurrentFloor() == 25) {
                elevator.goTo(13, 500, keyCard);
                elevator.goTo(46, 500, keyCard);
                return true;
            }
            return false;
        });

        Awaitility.await().ignoreExceptions().untilAsserted(() -> {
            Assertions.assertEquals(5, elevator.getVisitedFloors().get(0));
            Assertions.assertEquals(10, elevator.getVisitedFloors().get(1));
            Assertions.assertEquals(20, elevator.getVisitedFloors().get(2));
            Assertions.assertEquals(25, elevator.getVisitedFloors().get(3));
            Assertions.assertEquals(13, elevator.getVisitedFloors().get(4));
            Assertions.assertEquals(2, elevator.getVisitedFloors().get(5));
            Assertions.assertEquals(46, elevator.getVisitedFloors().get(6));
        });
    }

}
