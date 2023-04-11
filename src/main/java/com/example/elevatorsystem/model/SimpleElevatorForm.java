package com.example.elevatorsystem.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleElevatorForm {

    @NotNull(message = "Please enter proper number")
    @Min(value = 1, message = "Number should be at least 1")
    @Max(value=30, message = "Number should not be greater than 30")
    private Integer numbersOfElevators;
    @NotNull(message = "Please enter proper number")
    @Min(value = 3, message = "Number should be at least 1")
    @Max(value=30, message = "Number should not be greater than 30")
    private Integer globalFloors;
    @NotNull(message = "Please enter proper number")
    @Min(value = 1, message = "Number should be at least 1")
    @Max(value=100, message = "Number should not be greater than 100")
    private Integer numberOFRandomUsers;
    @NotNull(message = "Please enter proper number")
    @Min(value = 1, message = "Number should be at least 1")
    @Max(value=1000, message = "Number should not be greater than 1000")
    private Integer desiredSteps;
}
