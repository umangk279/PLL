package q1.sockMatchingRobot;

public class RoboticArm implements Runnable {

    private String name;
    private SharedData sharedData;
    private MatchingMachine matchingMachine;

    RoboticArm(SharedData sharedData, String name) {
        this.name = name;
        this.sharedData = sharedData;
        this.matchingMachine = sharedData.getMatchingMachine();
    }

    @Override
    public void run() {
        while(true) {
            int sock = sharedData.selectSock();

            if(sock == Constants.NO_SOCKS) {
                System.out.println(name + " stopping.");
                break;
            }
            System.out.println(name + " picked sock " + Constants.colorMap[sock]);
            matchingMachine.handleSock(sock);
        }
    }

}
