package com.example.kindalabchallenge;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BuildingTest {

    @Test
    void givenPublicElevatorInGroundFloor_whenCallFrom20Floor_andGoToLastFloor_andHaveKeyCard_thenEndsInLastFloor() {
        Building building = new Building();
        Assertions.assertEquals(0, building.getCurrentFloorPublicElevator());

        building.callPublicElevator(20);
        Assertions.assertEquals(20, building.getCurrentFloorPublicElevator());

        building.goByPublicElevator(50, 200, true);
        Assertions.assertEquals(50, building.getCurrentFloorPublicElevator());
    }

    @Test
    void givenFreightElevatorInGroundFloor_whenCallFromBasement_andGoToLastFloor_thenEndsInLastFloor() {
        Building building = new Building();
        Assertions.assertEquals(0, building.getCurrentFloorFreightElevator());

        building.callFreightElevator(-1);
        Assertions.assertEquals(-1, building.getCurrentFloorFreightElevator());

        building.goByFreightElevator(50, 200);
        Assertions.assertEquals(50, building.getCurrentFloorFreightElevator());
    }

}
