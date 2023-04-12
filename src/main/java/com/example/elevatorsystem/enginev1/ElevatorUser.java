package com.example.elevatorsystem.enginev1;

public class ElevatorUser {
    int startingFloor;
    int desiredFloor;
    Integer elevatorNumber;
    Direction pressedButton;
    Boolean insideElevator;

    public ElevatorUser(Integer elevatorNumber,
                        int startingFloor,
                        int desiredFloor) {
        this.startingFloor = startingFloor;
        this.desiredFloor = desiredFloor;
        if (startingFloor < desiredFloor) {
            this.pressedButton = Direction.UP;
        } else {
            this.pressedButton = Direction.DOWN;
        }
        this.insideElevator = false;
        this.elevatorNumber = elevatorNumber;
    }

    public int getStartingFloor() {
        return startingFloor;
    }

    public void setStartingFloor(int startingFloor) {
        this.startingFloor = startingFloor;
    }

    public int getDesiredFloor() {
        return desiredFloor;
    }

    public void setDesiredFloor(int desiredFloor) {
        this.desiredFloor = desiredFloor;
    }

    public Integer getElevatorNumber() {
        return elevatorNumber;
    }

    public void setElevatorNumber(Integer elevatorNumber) {
        this.elevatorNumber = elevatorNumber;
    }

    public Direction getPressedButton() {
        return pressedButton;
    }

    public void setPressedButton(Direction pressedButton) {
        this.pressedButton = pressedButton;
    }

    public Boolean getInsideElevator() {
        return insideElevator;
    }

    public void setInsideElevator(Boolean insideElevator) {
        this.insideElevator = insideElevator;
    }

    @Override
    public String toString() {
        return "ElevatorUser{" +
                "startingFloor=" + startingFloor +
                ", desiredFloor=" + desiredFloor +
                ", elevatorNumber=" + elevatorNumber +
                ", pressedButton=" + pressedButton +
                ", insideElevator=" + insideElevator +
                '}';
    }
}
