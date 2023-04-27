# ElevatorManager

## Main Logic and features:

With ElevatorManager class you can declare how many elevators and how many floors will have your simulation.
Logic behind Elevator class is:

- First are served UP request then Down request.
- If elevator is running, it can pick up new User.
- If User want's go up and Elevator going UP then User can be pick up by Elevator, and vice versa.
- When elevator going in one direction will pick up every one that want go in the same direction. When last user is
  served, then if there are pending request, elevator will go in opposite direction and start pick up User. This circle
  will go on to the last request.
- If there is no users then elevator going to be stop in Idle mode.

Created with Java 17, SpringBoot 3.0.5, Thymeleaf, Gradle.

# Getting Started

Download source code and run with gradle build and run. There are simple html forms to get started with simulations.
