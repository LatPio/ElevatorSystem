import java.util.ArrayList;
import java.util.Objects;

public class ElevatorManager {

    final Integer numbersOfElevators;
    final Integer globalFloors;
    ArrayList<Elevator> elevators = new ArrayList<>();

    public ElevatorManager(Integer numbersOfElevators, Integer globalFloors) {
        this.numbersOfElevators = numbersOfElevators;
        this.globalFloors = globalFloors;
        createElevators(numbersOfElevators, globalFloors);
    }

    private void createElevators(Integer elevatorCounts, Integer floors){
        for (int i = 1; i < elevatorCounts+1; i++) {
            elevators.add(new Elevator(i, floors));
        }
    }
    public void stepUpElevators(){
        for (Elevator elevator : elevators) {
            elevator.run();
        }
    }

    public Integer[][] statusElevators(){
        Integer[][] output = new Integer[elevators.size()][];
        for (int i = 0; i< elevators.size(); i++) {
            output[i] = elevators.get(i).status();
        }
        return output;
    }

    public void printElevatorsStatus(){
        for (Elevator elevator : elevators) {
            System.out.println(elevator.statusString());
        }
    }
    public void printElevatorsStatus(Integer elevatorNumber){
        System.out.println(elevators.get(elevatorNumber).statusString());
    }

    public void printElevatorStatusRawData(Integer[][] status){
        System.out.println("| Elevator number | Pending Users | Declared Floors | Current Floor | Direction |");
        System.out.println("---------------------------------------------------------------------------------");

        for (int i = 0; i < status.length; i++) {

            String a= "| ID: ";
            a+= String.valueOf(status[i][0]);
            String out = (status[i][0] < 10) ? "           " : "          ";
            a+= out;

            String c = "| ";
            c+= String.valueOf(status[i][1]);
            String out3 = (status[i][1] < 10) ? "             " : "            ";
            c+=out3;

            String d = "| ";
            d+= String.valueOf(status[i][2]);
            String out4 = (status[i][2] < 10) ? "             " : "              ";
            d+=out4;

            String e = "| ";
            e+= String.valueOf(status[i][3]);
            String out5 = (status[i][3] < 10) ? "             " : "            ";
            e+=out5;

            String f = "| ";
            f+= String.valueOf(status[i][4]);
            String out6 = (status[i][4] < 0) ? "        |" : "         |";
            f+=out6;

            System.out.println(a+c+d+e+f);

        }
        System.out.println("---------------------------------------------------------------------------------");
    }

    private Integer getRandom(Integer min, Integer max){
        return (int) ((Math.random() *(max - min)) + min);
    }

    public ElevatorUser createRandomUser(){
        Integer ellNum = (getRandom(1,numbersOfElevators +1));
        Integer startF = (getRandom(0,globalFloors+1));
        Integer endF = (getRandom(0,globalFloors+1));
        while (Objects.equals(startF, endF)){
            endF = (getRandom(0,globalFloors+1));
        }
        return new ElevatorUser(ellNum, startF, endF);
    }

    public void sendUsers(ElevatorUser elevatorUser){
        elevators.get(elevatorUser.elevatorNumber-1).sendRequest(elevatorUser);
    }

    public void sendUsers(Integer numberOfRandomUsers){
        for (int i = 0; i < numberOfRandomUsers; i++) {
            ElevatorUser userGenerated = createRandomUser();
            elevators.get(userGenerated.elevatorNumber-1).sendRequest(userGenerated);
        }
    }

    public void sendUsers(ElevatorUser[] elevatorUsers){
        for (int i = 0; i < elevatorUsers.length; i++) {
            ElevatorUser userGenerated = createRandomUser();
            elevators.get(userGenerated.elevatorNumber-1).sendRequest(userGenerated);
        }
    }



}
