package com.example.kindalabchallenge.controller;

import com.example.kindalabchallenge.dto.CallDto;
import com.example.kindalabchallenge.service.ElevatorService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/elevators/{elevatorName}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ElevatorController {

    private final ElevatorService elevatorService;

    @PostMapping(path = "/go-to")
    public ResponseEntity<Void> goTo(@PathVariable String elevatorName, @RequestBody CallDto callDto) {
        this.elevatorService.goTo(elevatorName, callDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/call/{floor}")
    public ResponseEntity<Void> call(@PathVariable String elevatorName, @PathVariable int floor){
        this.elevatorService.call(elevatorName, floor);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/current-floor")
    public ResponseEntity<Integer> getCurrentFloor(@PathVariable String elevatorName) {
        int currentFloor = this.elevatorService.getCurrentFloor(elevatorName);
        return ResponseEntity.ok(currentFloor);
    }

}
