package com.example.elevatorsystem.engine;

public class ElevatorsController {


    public StringBuilder run(Integer numbersOfElevators,
                    Integer globalFloors,
                    Integer numberOFRandomUsers,
                    Integer desiredSteps) {

        StringBuilder output = new StringBuilder();
        // Build new Building with elevator system (Counts of elevator: , how many floors )
        ElevatorManager elevatorManager = new ElevatorManager(numbersOfElevators, globalFloors);

        // Table of current status of elevators
        output.append(elevatorManager.printElevatorStatusStringData(elevatorManager.statusElevators()));
        output.append(" \n");

        // Sending 40 random users at once
        elevatorManager.sendUsers(numberOFRandomUsers);
        // or Sending one particular user
//        elevatorManager.sendUsers(new ElevatorUser(2, 5, 10));

        // Simulation of Elevator Manager with desired steps
        for (int i = 0; i < desiredSteps; i++) {
            // For more information on chosen elevator:
            output.append(elevatorManager.printElevatorsStatusString(0));
            output.append(" \n");
            // Seeing random users and their steps in elevator
//            elevatorManager.elevators.get(0).elevatorUserTemp.forEach(a -> System.out.println(a.toString()));
            // invoking one step of elevators
            elevatorManager.stepUpElevators();
        }
//        elevatorManager.sendUsers(40);
//        for (int i = 0; i < 20; i++) {
//            // For more information on chosen elevator:
//            elevatorManager.printElevatorsStatus(1);
//            // Seeing random users and their steps in elevator
//            elevatorManager.elevators.get(1).elevatorUserTemp.forEach(a -> System.out.println(a.toString()));
//            // invoking one step of elevators
//            elevatorManager.stepUpElevators();
//        }
        // Printing summary after the simulation
        output.append(elevatorManager.printElevatorStatusStringData(elevatorManager.statusElevators()));
        output.append(" \n");

        // Printing summary on actual step of every elevator
//        elevatorManager.printElevatorsStatus();

        return output;
    }
}