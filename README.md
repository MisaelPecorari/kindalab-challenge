# kindalab-challenge

First of all, download the dependencies and compile the project by running
`mvn clean compile`

After that, you can run all, a set or a specific test using the following commands:

1. Run all the tests `mvn test`
2. Run a complete class with `mvn test -Dtest="CLASS_NAME"`, which can be one of BuildingTest, FreightElevatorTest or PublicElevatorTest.
3. Run a specific test with `mvn test -Dtest="CLASS_NAME#TEST_NAME"`. I will leave all the possible combinations in the next list:

```
BuildingTest#givenPublicElevatorInGroundFloor_whenCallFrom20Floor_andGoToLastFloor_andHaveKeyCard_thenEndsInLastFloor
BuildingTest#givenFreightElevatorInGroundFloor_whenCallFromBasement_andGoToLastFloor_thenEndsInLastFloor

FreightElevatorTest#givenAnyFloor_andWeightNoExceeded_whenGoTo_thenSucceed
FreightElevatorTest#givenBasementOrFloor50_andWeightNoExceeded_whenGoTo_thenSucceed
FreightElevatorTest#givenAnyFloor_andWeightExceeded_whenGoTo_thenThrowException

PublicElevatorTest#given25Floor_andWeightNoExceeded_andNoKeyCard_whenGoTo_thenSucceed
PublicElevatorTest#givenBasementOrFloor50_andWeightNoExceeded_andNoKeyCard_whenGoTo_thenFail
PublicElevatorTest#givenBasementOrFloor50_andWeightNoExceeded_andKeyCard_whenGoTo_thenSucceed
PublicElevatorTest#givenAnyFloor_andWeightExceeded_andNoKeyCard_whenGoTo_thenThrowException
```
