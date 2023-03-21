package com.example.kindalabchallenge.dto;

import com.example.kindalabchallenge.model.KeyCard;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CallDto {
    private int floor;
    private int weightInKilos;
    private KeyCard keyCard;
}
