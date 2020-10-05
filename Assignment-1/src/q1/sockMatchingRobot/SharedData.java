package q1.sockMatchingRobot;

import java.util.ArrayList;
import java.util.Random;

public class SharedData {

    private int noOfRobots;
    private ArrayList<Integer> pileOfSocks;
    private ShelfManager shelfManager;
    private MatchingMachine matchingMachine;
    private Random random;
    private Object lock;

    SharedData(int noOfRobots, ArrayList<Integer> pile, MatchingMachine mm, ShelfManager sm) {
        this.noOfRobots = noOfRobots;
        this.pileOfSocks = pile;
        this.shelfManager = sm;
        this.matchingMachine = mm;
        this.random = new Random();
        lock = new Object();
    }

    public MatchingMachine getMatchingMachine() {
        return matchingMachine;
    }

    public int selectSock() {

        int sock;
        int n = 0;
        boolean flag = false;

        synchronized (lock) {
            if (pileOfSocks.size() > 0) {
                n = random.nextInt(pileOfSocks.size());
            } else {
                flag = true;
            }
        }

        if (flag) {
            return Constants.NO_SOCKS;
        }

        synchronized (lock) {
            sock = pileOfSocks.get(n);
            pileOfSocks.remove(n);
        }
        return sock;
    }
}
