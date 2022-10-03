import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Elevator {
    final List<ElevatorUser> elevatorUserTemp;
    final int floors;
    final Integer elevatorNumber;
    int currentFloor = 0;
    Direction direction;
    Boolean[] queueUP;
    Boolean[] queueDown;
    Boolean[] floorButtons;

    public Elevator(Integer elevatorNumber, int floors) {
        this.floors = floors;
        this.elevatorNumber = elevatorNumber;
        elevatorUserTemp = new ArrayList<>();
        this.direction = Direction.IDLE;
        floorButtons = new Boolean[floors+1];
        queueUP = new Boolean[floors+1];
        queueDown = new Boolean[floors+1];
        falseFiler(floorButtons);
        falseFiler(queueDown);
        falseFiler(queueUP);
    }

    public void stepUp(){
        if (direction.equals(Direction.UP)){
            if(currentFloor < floors){
                currentFloor++;
            }
        } else if (direction.equals(Direction.DOWN)) {
            if (currentFloor > 0){
                currentFloor--;
            }
        }
    }

    public void run(){
        if(Arrays.stream(this.queueUP).anyMatch((a)-> a) | Arrays.stream(this.queueDown).anyMatch(a-> a) | Arrays.stream(this.floorButtons).anyMatch(a-> a)){
            processRequests();
//            System.out.println(toString());
            stepUp();
        } else {
            this.direction = Direction.IDLE;
//            System.out.println(toString());
        }
    }

    private void processRequests() {
        if (this.direction == Direction.UP || this.direction == Direction.IDLE) {
            processUpRequest();
        } else {
            processDownRequest();
        }
    }

    private void  processUpRequest(){

        if (Arrays.stream(Arrays.copyOfRange(queueUP,currentFloor,queueUP.length)).anyMatch(a-> a) | Arrays.stream(Arrays.copyOfRange(floorButtons, currentFloor, floorButtons.length)).anyMatch(a-> a)){

            if (queueUP[currentFloor] && direction == Direction.UP) {
                usersMover(Direction.UP);
                queueUP[currentFloor] = false;
            }

            if (floorButtons[currentFloor] ) {
                usersMover(Direction.UP);
                floorButtons[currentFloor] = false;
            }
            if(currentFloor < findBiggestIndex(queueUP, true) || currentFloor < findBiggestIndex(floorButtons, true)){
                callUp();
            }
        }
        if (Arrays.stream(Arrays.copyOfRange(queueUP,currentFloor,queueUP.length)).noneMatch(a-> a) && Arrays.stream(Arrays.copyOfRange(floorButtons, currentFloor, floorButtons.length)).noneMatch(a-> a)){
            if ( Arrays.stream(Arrays.copyOfRange(queueDown,currentFloor, queueDown.length)).anyMatch(a-> a) ){

                if(currentFloor < findBiggestIndex(queueDown, true)  ) {
                    callUp();
                } else if( queueDown[currentFloor]  ) {
                    usersMover(Direction.DOWN);
                    queueDown[currentFloor] = false;
                    callDown();
                }
            }
            else if (Arrays.stream(Arrays.copyOfRange(queueDown,0, queueDown.length)).anyMatch(a-> a)) {
                callDown();
                if( queueDown[currentFloor]  ) {
                    usersMover(Direction.DOWN);
                    queueDown[currentFloor] = false;
                    callDown();
                }
            } else if (Arrays.stream(Arrays.copyOfRange(queueUP,0, queueDown.length)).anyMatch(a-> a)){
                callDown();
            }
        }

        if (Arrays.stream(queueUP).noneMatch((a)-> a) && Arrays.stream(queueDown).noneMatch(a-> a) && Arrays.stream(floorButtons).noneMatch(a-> a)) {
            callIdle();
        }
    }

    private  void processDownRequest() {


        if (Arrays.stream(Arrays.copyOfRange(queueDown,0 ,currentFloor+1)).anyMatch(a-> a) || Arrays.stream(Arrays.copyOfRange(floorButtons,0, currentFloor+1)).anyMatch(a -> a)) {

            if (queueDown[currentFloor] && direction == Direction.DOWN) {
                usersMover(Direction.DOWN);
                queueDown[currentFloor] = false;
            }
            if (floorButtons[currentFloor]) {
                usersMover(Direction.DOWN);
                floorButtons[currentFloor] = false;
            }
            if (currentFloor >  findSmallestIndex(queueDown, true) || currentFloor > findSmallestIndex(floorButtons, true)) {
                callDown();
            }
        }
        if (Arrays.stream(Arrays.copyOfRange(queueDown,0 ,currentFloor+1)).noneMatch(a-> a) && Arrays.stream(Arrays.copyOfRange(floorButtons,0, currentFloor+1)).noneMatch(a -> a)){
            if ( Arrays.stream(Arrays.copyOfRange(queueUP,0, currentFloor+1)).anyMatch(a-> a) ){

                if(currentFloor > findSmallestIndex(queueUP, true)  ){
                    callDown();
                } else if( queueUP[currentFloor]  ){
                    usersMover(Direction.UP);
                    queueUP[currentFloor] = false;
                    callUp();
                }
            }
            else if (Arrays.stream(Arrays.copyOfRange(queueUP,0, queueUP.length)).anyMatch(a-> a) ){
                callUp();
                if( queueUP[currentFloor]  ){
                    usersMover(Direction.UP);
                    queueUP[currentFloor] = false;
                    callUp();
                }
            }else if (Arrays.stream(Arrays.copyOfRange(queueDown,0, queueDown.length)).anyMatch(a-> a)){
                callUp();
            }
        }

        if (Arrays.stream(queueUP).noneMatch((a)-> a) && Arrays.stream(queueDown).noneMatch(a-> a) && Arrays.stream(floorButtons).noneMatch(a-> a)) {
            callIdle();
        }
    }

    private void usersMover(Direction direction){
        Iterator<ElevatorUser> iter = elevatorUserTemp.iterator();

        while (iter.hasNext()){
            ElevatorUser userActual = iter.next();
            if (!userActual.getInsideElevator() && userActual.getStartingFloor() == currentFloor && userActual.getPressedButton() == direction){
                userActual.setInsideElevator(true);
                floorButtons[userActual.getDesiredFloor()]=true;
            }
        }
        elevatorUserTemp.removeIf(n -> n.getInsideElevator() && n.getDesiredFloor()==currentFloor);
    }

    public void sendRequest(ElevatorUser elevatorUser){
        elevatorUserTemp.add(elevatorUser);
        if (currentFloor == elevatorUser.getStartingFloor() && elevatorUser.getPressedButton() == Direction.UP) {
            elevatorUser.setInsideElevator(true);
            floorButtons[(elevatorUser.getDesiredFloor())] = true;
        } else {
            queueDown[elevatorUser.getStartingFloor()]=true;
        }
        if (currentFloor == elevatorUser.getStartingFloor() && elevatorUser.getPressedButton() == Direction.DOWN) {
            elevatorUser.setInsideElevator(true);
            floorButtons[(elevatorUser.getDesiredFloor())] = true;
        } else {
            queueUP[elevatorUser.getStartingFloor()]=true;
        }


        if (currentFloor < elevatorUser.getStartingFloor()){

            if(elevatorUser.getPressedButton() == Direction.UP){
                queueUP[elevatorUser.getStartingFloor()]=true;
                if (direction.equals(Direction.IDLE)){callUp();}
            }
            if (elevatorUser.getPressedButton()== Direction.DOWN){
                queueDown[elevatorUser.getStartingFloor()]=true;
                if (direction.equals(Direction.IDLE)){callUp();}
            }
        }
        if (currentFloor > elevatorUser.getStartingFloor()){

            if(elevatorUser.getPressedButton() == Direction.UP){
                queueUP[elevatorUser.getStartingFloor()]=true;
                if (direction.equals(Direction.IDLE)){callDown();}

            }
            if (elevatorUser.getPressedButton() == Direction.DOWN){
                queueDown[elevatorUser.getStartingFloor()] =true;
                if (direction.equals(Direction.IDLE)){callDown();}
            }
        }
    }

    private void falseFiler(Boolean[] booleans){
        Arrays.fill(booleans, false);
    }

    private void callUp(){ direction = Direction.UP; }
    private void callDown(){ direction = Direction.DOWN; }
    private void callIdle(){ direction = Direction.IDLE; }
    private int findSmallestIndex(Boolean[] values, Boolean bool) {
        int minIndex = currentFloor;
        for (int i=0; i<values.length; i++) {
            if (values[i] == bool && i<currentFloor) {
                minIndex = i;
            }
        }
        return minIndex;
    }
    private int findBiggestIndex(Boolean[] values, Boolean bool) {
        int maxIndex = currentFloor;
        for (int i=0; i<values.length; i++) {
            if (values[i] == bool && i>maxIndex) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    public Integer[] status(){
        Integer[] output= new Integer[6];
        output[0] = elevatorNumber;
        output[1] = Math.toIntExact(elevatorUserTemp.size());
        output[2] = floors;
        output[3] = currentFloor;
        if (direction == Direction.DOWN){
            output[4] = -1;
        } else if (direction == Direction.UP){
            output[4] = 1;
        } else {
            output[4] = 0;
        }
        return output;
    }

    public String statusString(){
        return "Elevator: "+ elevatorNumber +" {" +
                "Current Floor =" + currentFloor +
                ", pending users=" + ((long) elevatorUserTemp.size()) +
                ", direction=" + direction +
                ", queueUP=" + trueIndexShower(queueUP)  +
                ", queueDown=" + trueIndexShower(queueDown) +
                ", floorButtons=" + trueIndexShower(floorButtons) +
                '}';
    }

    private String trueIndexShower(Boolean[] arr){
        String output = "( ";
        for (int i = 0; i < arr.length; i++) {
            if (arr[i]){
                output+= i ;
                output+=", ";
            }
        }
        if (output.length() > 2) {
            output= output.substring(0,output.length()-2);
        }
        output+=" )";
        return output;
    }

    @Override
    public String toString() {
        return "Elevator: "+ elevatorNumber +" {" +
                ", pending users=" + ((long) elevatorUserTemp.size()) +
                ", floors=" + floors +
                ", currentFlor=" + currentFloor +
                ", direction=" + direction +
                ", queueUP=" + trueIndexShower(queueUP)  +
                ", queueDown=" + trueIndexShower(queueDown) +
                ", floorButtons=" + trueIndexShower(floorButtons) +
                '}';
    }
}
