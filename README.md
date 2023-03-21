# kindalab-challenge

First of all, download the dependencies and compile the project by running
`mvn clean compile`

You can start running the application by executing:
`mvn spring-boot:run`

Here are some examples of how to use the API. Please note that the elevator type (public or freight) is specified in the URI:
1. Call the elevator to a certain floor:
`
curl -X POST -H "Content-Type: application/json" http://localhost:8080/elevators/public/call/5
`
2. Go to another floor: `curl -X POST -H "Content-Type: application/json" -d '{"floor":5, "weightInKilos":350, "keyCard": "ADMIN"}' http://localhost:8080/elevators/freight/go-to`
3. Check the current floor of any elevator: `curl http://localhost:8080/elevators/public/current-floor`


Optionally, you can run all, a set or a specific test using the following commands:

1. Run all the tests `mvn test`
2. Run a complete class with `mvn test -Dtest="CLASS_NAME"`, which can be one of BuildingTest, FreightElevatorTest or PublicElevatorTest.
3. Run a specific test with `mvn test -Dtest="CLASS_NAME#TEST_NAME"`. I will leave all the possible combinations in the next list: