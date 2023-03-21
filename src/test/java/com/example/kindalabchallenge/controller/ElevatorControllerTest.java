package com.example.kindalabchallenge.controller;

import com.example.kindalabchallenge.dto.CallDto;
import com.example.kindalabchallenge.model.KeyCard;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ElevatorControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void givenPublicElevator_andValidParams_whenGoTo_thenOk() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CallDto> httpEntity = new HttpEntity<>(new CallDto(10, 10, KeyCard.USER), headers);
        ResponseEntity<Void> responseEntity = restTemplate.postForEntity("/elevators/{elevatorName}/go-to", httpEntity, Void.class, "public");
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Awaitility.await().untilAsserted(() -> {
            ResponseEntity<String> currentFloorResponse = restTemplate.getForEntity("/elevators/{elevatorName}/current-floor", String.class, "public");
            Assertions.assertEquals(HttpStatus.OK, currentFloorResponse.getStatusCode());
            Assertions.assertEquals("10", currentFloorResponse.getBody());
        });
    }

    @Test
    void givenFreightElevator_whenCall_thenOk() {
        ResponseEntity<Void> responseEntity = restTemplate.postForEntity("/elevators/{elevatorName}/call/{floor}", null, Void.class, "freight", -1);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void givenInvalidElevator_whenCall_thenBadRequest() {
        ResponseEntity<Void> responseEntity = restTemplate.postForEntity("/elevators/{elevatorName}/call/{floor}", null, Void.class, "invalid", -1);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

}