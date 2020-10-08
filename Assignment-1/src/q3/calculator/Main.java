package q3.calculator;

import java.util.Timer;
import java.util.TimerTask;

public class Main {

    public static void main(String[] args) {
        // Creating new empty user interface for calculator
        UserInterface calculator = new UserInterface();
        // Setting up the UI to add buttons and display
        calculator.setupUI();

        // New timer for highlighting the keys
        Timer timer = new Timer();
        // Scheduling the task of updating the highlight
        // on screen with the timer thread
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                UserInterface.changeFocus();
            }
        }, 0, 1000);
    }
}
