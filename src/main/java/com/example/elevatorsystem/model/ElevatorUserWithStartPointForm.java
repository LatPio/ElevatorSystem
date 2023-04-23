package com.example.elevatorsystem.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ElevatorUserWithStartPointForm {

    private Integer elevatorNumber;
    private Integer startingPoint;
    private int startingFloor;
    private int desiredFloor;


}
