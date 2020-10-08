package q2.evaluationSystem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    // Structure of command: TA1/TA2/CC <RollNo> INC/DEC <marks>
    private static ArrayList<ArrayList<String> > inputCommands = new ArrayList<>();

    private static void addInputCommand(String inputCommand) {
        String[] commandArguments = inputCommand.split(" ");
        if(commandArguments.length != 4) {
            System.out.println("Enter valid command");
            return;
        }
        ArrayList<String> command = new ArrayList<>();
        String teacherName = commandArguments[0];

        if(!teacherName.equals("TA1") && !teacherName.equals("TA2") && !teacherName.equals("CC")) {
            System.out.println("Enter valid teacher name");
            return;
        }
        String rollNo = commandArguments[1];
        String action = commandArguments[2];

        int marks;
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

        command.add(teacherName);
        command.add(rollNo);
        command.add(Integer.toString(marks));
        inputCommands.add(command);
    }

    private static void executeCommands() throws IOException {
        Teacher ta1 = new Teacher("TA1", Thread.NORM_PRIORITY, SharedData.semaphore);
        Teacher ta2 = new Teacher("TA2", Thread.NORM_PRIORITY, SharedData.semaphore);
        Teacher cc = new Teacher("CC", Thread.MAX_PRIORITY, SharedData.semaphore);

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

        inputCommands.clear();

        cc.start();
        ta1.start();
        ta2.start();

        try {
            cc.join();
            ta1.join();
            ta2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        SharedData.updateFiles();
    }

    public static void main(String[] args) throws IOException {

        SharedData.studData = new HashMap<>();
        SharedData.readStudData();
        SharedData.semaphore = new Semaphore(1);
        while (true) {
            System.out.println("Write Query to update data, execute to run the entered commands and Exit to exit the system:");
            String input = scanner.nextLine();
            if(input.equals("Exit") || input.equals("exit")) {
                return;
            }
            if(input.equals("Execute") || input.equals("execute")) {
                executeCommands();
            }
            else {
                addInputCommand(input);
            }
        }

    }
}
