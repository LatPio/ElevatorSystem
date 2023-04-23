package com.example.elevatorsystem.model;

import com.example.elevatorsystem.enginev1.ElevatorUserWithStartPoint;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleV2ElevatorForm {

    @NotNull(message = "Please enter proper number")
    @Min(value = 1, message = "Number should be at least 1")
    @Max(value=30, message = "Number should not be greater than 30")
    private Integer numbersOfElevators;
    @NotNull(message = "Please enter proper number")
    @Min(value = 3, message = "Number should be at least 1")
    @Max(value=30, message = "Number should not be greater than 30")
    private Integer globalFloors;

    private ArrayList<ElevatorUserWithStartPoint> users;
    @NotNull(message = "Please enter proper number")
    @Min(value = 1, message = "Number should be at least 1")
    @Max(value=10_000, message = "Number should not be greater than 1000")
    private Integer desiredSteps;
}
