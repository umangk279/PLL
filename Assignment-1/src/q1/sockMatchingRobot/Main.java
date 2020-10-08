package q1.sockMatchingRobot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        // Array list to store the pile of socks
        ArrayList<Integer> pileOfSocks = new ArrayList<>();

        // Reading the pile of socks from input file
        File file = new File("src/q1/sockMatchingRobot/input.txt");
        Scanner scanner = new Scanner(file);

        // No of arms that will operate synchronously
        int noOfRobots = scanner.nextInt();

        while(scanner.hasNextInt()) {
            int sock = scanner.nextInt();
            if(sock<0 || sock>3){
                System.out.println(sock + " is not a valid sock color");
                System.exit(1);
            }
            pileOfSocks.add(sock);
        }
        System.out.println("No of Robots = "+noOfRobots);
        System.out.println("No of socks in sock pile = " +pileOfSocks.size());

        // Creating new shelf manager and matching machine
        ShelfManager shelfManager = new ShelfManager();
        MatchingMachine matchingMachine = new MatchingMachine(shelfManager);

        // Instance through which data will be shared among different threads
        SharedData sharedData = new SharedData(noOfRobots, pileOfSocks, matchingMachine, shelfManager);

        // Generating threads for each robotic arm
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < noOfRobots; i++) {
            RoboticArm roboticArm = new RoboticArm(sharedData, "Robotic Arm-"+(i+1));
            Thread thread = new Thread(roboticArm);
            threads.add(thread);
        }

        // Starting all the threads
        for (int i = 0; i < noOfRobots; i++) {
            threads.get(i).start();
        }

    }
}
