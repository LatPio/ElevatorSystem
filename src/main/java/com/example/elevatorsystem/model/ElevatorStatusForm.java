package com.example.elevatorsystem.model;

import com.example.elevatorsystem.enginev1.Direction;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ElevatorStatusForm {
    private Integer elevatorNumber;
    private Integer currentFloor;
    private Integer pendingUsers;
    private Direction direction;
    private List<Integer> queueUP;
    private List<Integer> queueDown;
    private List<Integer> floorButtons;
}
