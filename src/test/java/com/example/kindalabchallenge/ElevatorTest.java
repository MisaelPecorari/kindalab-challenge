package com.example.kindalabchallenge;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

@SpringBootTest
public class ElevatorTest {


    @Test
    public void givenCallFromDifferentFloors_andGoToDifferentFloors_whenSchedule_thenSuccess() {
        int delayInMillis = 100;
        Elevator elevator = PublicElevator.getInstance();
        elevator.setDelay(delayInMillis);
        Assertions.assertEquals(0, elevator.getCurrentFloor());

        elevator.call(10);
        elevator.call(5);
        elevator.call(20);

        Awaitility.await().pollDelay(Duration.ofMillis(delayInMillis)).until(() -> {
            if (elevator.getCurrentFloor() == 5) {
                elevator.goTo(2, 500, true);
                return true;
            }
            return false;
        });

        Awaitility.await().ignoreExceptions().untilAsserted(() -> {
            Assertions.assertEquals(5, elevator.getVisitedFloors().get(0));
            Assertions.assertEquals(10, elevator.getVisitedFloors().get(1));
            Assertions.assertEquals(20, elevator.getVisitedFloors().get(2));
            Assertions.assertEquals(2, elevator.getVisitedFloors().get(3));
        });
    }

}
