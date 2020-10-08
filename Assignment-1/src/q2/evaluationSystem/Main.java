package q2.evaluationSystem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    // Structure of command: TA1/TA2/CC <RollNo> INC/DEC <marks>
    // array list to store the current set of commands
    private static ArrayList<ArrayList<String> > inputCommands = new ArrayList<>();

    // Function that adds input to the list of input commands
    private static void addInputCommand(String inputCommand) {

        // Split the input string by spaces
        String[] commandArguments = inputCommand.split(" ");

        // check if the input has required number of arguments
        if(commandArguments.length != 4) {
            System.out.println("Enter valid command");
            return;
        }

        ArrayList<String> command = new ArrayList<>();

        String teacherName = commandArguments[0];

        // check if the teacher name is valid
        if(!teacherName.equals("TA1") && !teacherName.equals("TA2") && !teacherName.equals("CC")) {
            System.out.println("Enter valid teacher name");
            return;
        }

        String rollNo = commandArguments[1];
        String action = commandArguments[2];

        int marks;

        // calculate change in marks in cases of increment and decrement
        if(action.equals("INC")) {
            marks = Integer.parseInt(commandArguments[3]);
        }
        else if(action.equals("DEC")) {
            marks = -1*Integer.parseInt(commandArguments[3]);
        }
        else {
            System.out.println("Enter valid action");
            return;
        }

        // add the current current command to the list
        command.add(teacherName);
        command.add(rollNo);
        command.add(Integer.toString(marks));
        inputCommands.add(command);
    }

    // Function that executes the current set of input commands
    private static void executeCommands() throws IOException {

        // create new threads for each teacher
        Teacher ta1 = new Teacher("TA1", Thread.NORM_PRIORITY, SharedData.semaphore);
        Teacher ta2 = new Teacher("TA2", Thread.NORM_PRIORITY, SharedData.semaphore);
        Teacher cc = new Teacher("CC", Thread.MAX_PRIORITY, SharedData.semaphore);

        // update the command list for each teacher thread
        for(ArrayList<String > command : inputCommands) {
            switch (command.get(0)) {
                case "TA1":
                    ta1.updateCommandList(command.get(1), command.get(2));
                    break;
                case "TA2":
                    ta2.updateCommandList(command.get(1), command.get(2));
                    break;
                case "CC":
                    cc.updateCommandList(command.get(1), command.get(2));
                    break;
            }
        }

        // clear the current set of input commands
        inputCommands.clear();

        // executing all the threads
        cc.start();
        ta1.start();
        ta2.start();

        // waiting for all the threads to complete execution
        try {
            cc.join();
            ta1.join();
            ta2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Write back all the changes to files
        SharedData.updateFiles();
    }

    public static void main(String[] args) throws IOException {

        SharedData.studData = new HashMap<>();
        // read the student data in stud_info.txt
        SharedData.readStudData();
        // semaphore for the shared student data
        SharedData.semaphore = new Semaphore(1);

        // keep asking for input
        while (true) {
            System.out.println("Write Query to update data, 'execute' to run the entered commands and 'exit' to exit the system:");

            // read the input command
            String input = scanner.nextLine();

            // if command is exit, the the system
            if(input.equals("Exit") || input.equals("exit")) {
                return;
            }

            // if command is execute, execute the current set of input commands
            if(input.equals("Execute") || input.equals("execute")) {
                executeCommands();
            }

            // else add the input command to the list of input commands
            else {
                addInputCommand(input);
            }
        }

    }
}
