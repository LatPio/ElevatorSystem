public class ElevatorsController {


    public static void main(String[] args) {

        // Build new Building with elevator system (Counts of elevator: , how many floors )
        ElevatorManager elevatorManager = new ElevatorManager( 16, 10);

        // Table of current status of elevators
        elevatorManager.printElevatorStatusRawData(elevatorManager.statusElevators());

        //        Sending 40 random users at once
        elevatorManager.sendUsers(40);
        // or Sending one particular user
        elevatorManager.sendUsers(new ElevatorUser(2, 5,10));

        // Simulation of Elevator Manager with 100 steps
        for (int i = 0; i < 50; i++) {
            // For more information on chosen elevator:
            elevatorManager.printElevatorsStatus(1);
            // Seeing random users and their steps in elevator
            elevatorManager.elevators.get(1).elevatorUserTemp.forEach(a -> System.out.println(a.toString()));
            // invoking one step of elevators
            elevatorManager.stepUpElevators();
        }
        // Printing summary after 100 steps of simulation
        elevatorManager.printElevatorStatusRawData(elevatorManager.statusElevators());

        //Printing summary on actual step of every elevator
        elevatorManager.printElevatorsStatus();




    }
}