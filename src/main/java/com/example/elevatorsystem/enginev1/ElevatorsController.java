package com.example.elevatorsystem.enginev1;

import com.example.elevatorsystem.model.ElevatorStatusForm;

import java.util.ArrayList;
import java.util.List;

public class ElevatorsController {

    public List<List<ElevatorStatusForm>> run(Integer numbersOfElevators,
                             Integer globalFloors,
                             ArrayList<ElevatorUserWithStartPoint> users,
                             Integer desiredSteps) {

        List<List<ElevatorStatusForm>> output = new ArrayList<>();
        ElevatorManager elevatorManager = new ElevatorManager(numbersOfElevators, globalFloors);


        for (int i = 0; i < desiredSteps; i++) {
            int finalI = i;
            users.stream().forEach(user -> {
                if(user.getStartingPoint() == finalI){
                    elevatorManager.sendUsers(user);
                }
            }
            );
            output.add(new ArrayList<>());
            for (int j = 0; j < numbersOfElevators; j++) {


                output.get(i).add(ElevatorStatusForm.builder()
                        .elevatorNumber(elevatorManager.elevators.get(j).elevatorNumber)
                        .currentFloor(elevatorManager.elevators.get(j).currentFloor)
                        .pendingUsers(elevatorManager.elevators.get(j).elevatorUserTemp.size())
                        .direction(elevatorManager.elevators.get(j).direction)
                        .queueDown(Elevator.queueArray(elevatorManager.elevators.get(j).queueDown))
                        .queueUP(Elevator.queueArray(elevatorManager.elevators.get(j).queueUP))
                        .floorButtons(Elevator.queueArray(elevatorManager.elevators.get(j).floorButtons))
                        .build());


            }

            elevatorManager.stepUpElevators();
        }


        return output;
    }
    public StringBuilder[] run2(Integer numbersOfElevators,
                             Integer globalFloors,
                             Integer numberOFRandomUsers,
                             Integer desiredSteps) {

        StringBuilder[] output = new StringBuilder[desiredSteps];

        ElevatorManager elevatorManager = new ElevatorManager(numbersOfElevators, globalFloors);

        elevatorManager.sendUsers(numberOFRandomUsers);

        for (int i = 0; i < desiredSteps; i++) {
            output[i]= new StringBuilder();
            output[i].append(elevatorManager.printElevatorStatusStringData(elevatorManager.statusElevators()));
            output[i].append(" \n");
            for (int j = 0; j < numbersOfElevators; j++) {
                output[i].append(elevatorManager.printElevatorsStatusString(j));
                output[i].append(" \n");
            }
            elevatorManager.stepUpElevators();
        }

        return output;
    }
}