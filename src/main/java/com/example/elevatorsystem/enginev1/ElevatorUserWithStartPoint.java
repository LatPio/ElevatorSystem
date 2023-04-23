package com.example.elevatorsystem.enginev1;

public class ElevatorUserWithStartPoint extends ElevatorUser{

    Integer startingPoint;

    public ElevatorUserWithStartPoint(Integer elevatorNumber, int startingFloor, int desiredFloor, Integer startingPoint) {
        super(elevatorNumber, startingFloor, desiredFloor);
        this.startingPoint = startingPoint;
    }


    public Integer getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(Integer startingPoint) {
        this.startingPoint = startingPoint;
    }

    @Override
    public String toString() {
        return "ElevatorUserWithStartPoint{" +
                "startingPoint=" + startingPoint +
                ", elevatorNumber=" + super.getElevatorNumber() +
                ", StartingFloor=" + super.getStartingFloor() +
                ", desiredFloor=" + super.getDesiredFloor() +
                ", StartingFloor=" + super.getStartingFloor() +
                ", pressedButton=" + super.getPressedButton() +
                ", insideElevator=" + super.getInsideElevator() +
                '}';
    }
}
