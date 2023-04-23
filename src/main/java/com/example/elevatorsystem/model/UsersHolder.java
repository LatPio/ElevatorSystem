package com.example.elevatorsystem.model;

import com.example.elevatorsystem.enginev1.ElevatorUserWithStartPoint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UsersHolder {

    List<ElevatorUserWithStartPoint> list = new ArrayList<>();
}
