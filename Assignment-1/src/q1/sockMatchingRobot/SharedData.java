package q1.sockMatchingRobot;

import java.util.ArrayList;
import java.util.Random;

// Class to store the shared data and functions
public class SharedData {
    // No of robotic arms
    private int noOfRobots;
    // Sock pile from which sock must be picked
    private ArrayList<Integer> pileOfSocks;
    // Shelf manager and matching machine associatted with system
    private ShelfManager shelfManager;
    private MatchingMachine matchingMachine;
    // Random object to be used for selection
    private Random random;
    // Lock for the pile of socks
    private Object lock;

    // Constructor
    SharedData(int noOfRobots, ArrayList<Integer> pile, MatchingMachine mm, ShelfManager sm) {
        this.noOfRobots = noOfRobots;
        this.pileOfSocks = pile;
        this.shelfManager = sm;
        this.matchingMachine = mm;
        this.random = new Random();
        lock = new Object();
    }

    // Returns the matching machine
    public MatchingMachine getMatchingMachine() {
        return matchingMachine;
    }

    // Returns the picked up sock
    public int selectSock() {

        int sock;
        int n = 0;
        boolean flag = false;

        // Acquire the lock for sock pile
        synchronized (lock) {
            // there are socks left in the pile
            if (pileOfSocks.size() > 0) {
                // randomly select a sock
                n = random.nextInt(pileOfSocks.size());
            } else {
                flag = true;
            }
        }

        // If no socks are left, return no sock
        if (flag) {
            return Constants.NO_SOCKS;
        }

        // Try to acquire the lock for sock pile
        synchronized (lock) {
            sock = pileOfSocks.get(n);
            pileOfSocks.remove(n);
        }
        // return the picked sock
        return sock;
    }
}
