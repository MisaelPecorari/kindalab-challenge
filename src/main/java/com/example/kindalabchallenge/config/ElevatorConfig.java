package com.example.kindalabchallenge.config;

import com.example.kindalabchallenge.model.Elevator;
import com.example.kindalabchallenge.model.FreightElevator;
import com.example.kindalabchallenge.model.PublicElevator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ElevatorConfig {

    @Bean
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(2);
    }

    @Bean
    public Set<Elevator> elevators() {
        return Set.of(PublicElevator.getInstance(), FreightElevator.getInstance());
    }
}
