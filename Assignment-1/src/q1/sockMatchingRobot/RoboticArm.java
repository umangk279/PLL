package q1.sockMatchingRobot;

// Class for robotic arm that implements runnable interface
public class RoboticArm implements Runnable {

    // Name of thread
    private String name;
    // Shared data that will be accessed by thread
    private SharedData sharedData;
    // Matching machine to which the picked sock must be passed
    private MatchingMachine matchingMachine;

    // Constructor
    RoboticArm(SharedData sharedData, String name) {
        this.name = name;
        this.sharedData = sharedData;
        this.matchingMachine = sharedData.getMatchingMachine();
    }

    // Implementation of run function that needs to run on execution of thread
    @Override
    public void run() {
        // Keep trying to pick up socks
        while(true) {

            // Pick up a sock from the pile
            int sock = sharedData.selectSock();

            // If no socks are left in the pile then stop
            if(sock == Constants.NO_SOCKS) {
                System.out.println(name + " stopping.");
                break;
            }
            // Report that the sock has been picked
            System.out.println(name + " picked sock " + Constants.colorMap[sock]);
            // Pass the sock to matching machine
            matchingMachine.handleSock(sock);
        }
    }

}
