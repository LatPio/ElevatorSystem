import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Elevator {
    final ArrayList<ElevatorUser> elevatorUserTemp;
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
        if (queueUP[0]){
            usersMoverUP();
            queueUP[currentFloor] = false;
            callUp();
        }
        if (Arrays.stream(Arrays.copyOfRange(queueUP,currentFloor,queueUP.length)).anyMatch((a)-> a)){

            if (queueUP[currentFloor] && direction == Direction.UP) {
                usersMoverUP();
                queueUP[currentFloor] = false;
            }
            if(currentFloor < findBiggestIndex(queueUP, true)){
                callUp();
            }
        }
        if ( Arrays.stream(Arrays.copyOfRange(floorButtons, currentFloor, floorButtons.length)).anyMatch(a-> a) ){

            if (floorButtons[currentFloor] ) {
                usersMoverUP();
                floorButtons[currentFloor] = false;
            }
            if(currentFloor < findBiggestIndex(floorButtons, true) ){
                callUp();
            } else
            if(currentFloor == findBiggestIndex(floorButtons, true) ){
                if ( Arrays.stream(Arrays.copyOfRange(queueDown,currentFloor, queueDown.length)).anyMatch(a-> a) ){

                    if(currentFloor < findBiggestIndex(queueDown, true)  ) {
                        callUp();
                    } else if( queueDown[currentFloor]  ) {
                        usersMoverDown();
                        queueDown[currentFloor] = false;
                        callDown();
                    }
                    else { callDown();}
                }
                else if (Arrays.stream(Arrays.copyOfRange(queueDown,0, queueDown.length)).anyMatch(a-> a)) {
                    callDown();
                }
            }
        } else {
            if ( Arrays.stream(Arrays.copyOfRange(queueDown,findBiggestIndex(queueUP, true) , queueDown.length)).anyMatch(a-> a) ){

                if(currentFloor < findBiggestIndex(queueDown, true)  ) {
                    callUp();
                } else if( queueDown[currentFloor]  ) {
                    usersMoverDown();
                    queueDown[currentFloor] = false;
                    callDown();
                } else { callDown();}
            }
        }

        if (queueDown[queueDown.length-1]){
            usersMoverDown();
            queueDown[currentFloor] = false;
            callDown();
        }
        if (currentFloor == floors){
            callDown();
        }
        if (Arrays.stream(queueUP).noneMatch((a)-> a) && Arrays.stream(queueDown).noneMatch(a-> a) && Arrays.stream(floorButtons).noneMatch(a-> a)) {
            callIdle();
        }
    }

    private  void processDownRequest() {
        if (queueDown[queueDown.length-1]){
            usersMoverDown();
            queueDown[currentFloor] = false;
            callDown();
        }

        if ( Arrays.stream(Arrays.copyOfRange(queueDown,0 ,currentFloor+1)).anyMatch(a-> a) ) {

            if (queueDown[currentFloor] && direction == Direction.DOWN) {
                usersMoverDown();
                queueDown[currentFloor] = false;
            }
            if (currentFloor >  findSmallestIndex(queueDown, true) ) {
                callDown();
            }
        }
        if (Arrays.stream(Arrays.copyOfRange(floorButtons,0, currentFloor+1)).anyMatch(a -> a) ) {

            if (floorButtons[currentFloor]) {
                usersMoverDown();
                floorButtons[currentFloor] = false;
            }
            if (currentFloor > findSmallestIndex(floorButtons, true)) {
                callDown();
            }
            if (currentFloor == findSmallestIndex(floorButtons,true)){
                if ( Arrays.stream(Arrays.copyOfRange(queueUP,0, currentFloor+1)).anyMatch(a-> a) ){
                    if(currentFloor > findSmallestIndex(floorButtons, true)  ){
                        callDown();
                    } else if( queueUP[currentFloor]  ){
                        usersMoverUP();
                        queueUP[currentFloor] = false;
                        callUp();
                    } else if (currentFloor > findSmallestIndex(queueUP, true)){
                        callDown();
                    }
                    else {
                        callUp();}
                }
                else if (Arrays.stream(Arrays.copyOfRange(queueUP,0, queueUP.length)).anyMatch(a-> a) ){
                    callUp();
                }
            }

        } else {
            if ( Arrays.stream(Arrays.copyOfRange(queueUP,0, findSmallestIndex(queueDown, true))).anyMatch(a-> a) ){
                if(currentFloor > findSmallestIndex(queueUP, true)  ){
                    callDown();
                } else if( queueUP[currentFloor]  ){
                    usersMoverUP();
                    queueUP[currentFloor] = false;
                    callUp();
                } else { callUp();}
            }
            else if (Arrays.stream(Arrays.copyOfRange(queueUP,0, queueUP.length)).anyMatch(a-> a) && findSmallestIndex(queueUP, true) <= findSmallestIndex(queueDown, true) ){
                callUp();
                if (queueUP[currentFloor] && direction==Direction.UP){
                    usersMoverUP();
                    queueUP[currentFloor] = false;
                }
                if (Arrays.stream(floorButtons).noneMatch(a-> a)){
                    if (currentFloor>findSmallestIndex(queueDown, true)){
                        callDown();
                    }
                }
            }
        }
        if (currentFloor == 0){
            if (queueUP[0]){
                usersMoverUP();
                queueUP[currentFloor] = false;
                callUp();
            } else if(Arrays.stream(floorButtons).anyMatch(a-> a) || Arrays.stream(queueDown).anyMatch(a-> a)){
                callUp();
            }
        }

        if (Arrays.stream(queueUP).noneMatch((a)-> a) && Arrays.stream(queueDown).noneMatch(a-> a) && Arrays.stream(floorButtons).noneMatch(a-> a)) {
            callIdle();
        }
    }

    private void usersMoverUP(){
        Iterator<ElevatorUser> iter = elevatorUserTemp.iterator();

        while (iter.hasNext()){
            ElevatorUser userActual = iter.next();
            if (!userActual.getInsideElevator() && userActual.getStartingFloor() == currentFloor && userActual.getPressedButton() == Direction.UP){
                userActual.setInsideElevator(true);
                floorButtons[userActual.getDesiredFloor()]=true;
            }
        }
        elevatorUserTemp.removeIf(n -> n.getInsideElevator() && n.getDesiredFloor()==currentFloor);
    }

    private void usersMoverDown(){
        Iterator<ElevatorUser> iterator2 = elevatorUserTemp.iterator();

        while (iterator2.hasNext()){
            ElevatorUser userActual = iterator2.next();
            if (!userActual.getInsideElevator() && (userActual.getStartingFloor() == currentFloor && userActual.getPressedButton() == Direction.DOWN)){
                userActual.setInsideElevator(true);
                floorButtons[userActual.getDesiredFloor()] = true;
            }
        }
        elevatorUserTemp.removeIf(n -> n.getInsideElevator() && n.getDesiredFloor()==currentFloor);
    }

    public void sendRequest(ElevatorUser elevatorUser){
        elevatorUserTemp.add(elevatorUser);
        if (currentFloor == elevatorUser.getStartingFloor()){
            elevatorUser.setInsideElevator(true);
            if (elevatorUser.getPressedButton() == Direction.UP){
                floorButtons[(elevatorUser.getDesiredFloor())] = true;
            }
            if ((elevatorUser.getPressedButton() == Direction.DOWN)){
                floorButtons[elevatorUser.getDesiredFloor()]=true;
            }
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
        output[1] = Math.toIntExact(elevatorUserTemp.stream().count());
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
                ", pending users=" + (elevatorUserTemp.stream().count()) +
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
                ", pending users=" + (elevatorUserTemp.stream().count()) +
                ", floors=" + floors +
                ", currentFlor=" + currentFloor +
                ", direction=" + direction +
                ", queueUP=" + trueIndexShower(queueUP)  +
                ", queueDown=" + trueIndexShower(queueDown) +
                ", floorButtons=" + trueIndexShower(floorButtons) +
                '}';
    }
}
