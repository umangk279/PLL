package q3.modifiedCalculator;

import java.util.Timer;
import java.util.TimerTask;

public class Main {

    public static void main(String[] args) {
        // Creating new empty user interface for calculator
        UserInterface calculator = new UserInterface();
        // Setting up the UI to add buttons and display
        calculator.setupUI();

        // New timer for highlighting the number keys
        Timer timerNum = new Timer();
        // Scheduling the task of updating the highlight
        // on screen with the timer thread
        timerNum.schedule(new TimerTask() {
            @Override
            public void run() {
                UserInterface.changeFocusNum();
            }
        }, 0, 1000);

        // New timer for highlighting the function keys
        Timer timerFunc = new Timer();
        // Scheduling the task of updating the highlight
        // on screen with the timer thread
        timerNum.schedule(new TimerTask() {
            @Override
            public void run() {
                UserInterface.changeFocusFunc();
            }
        }, 0, 1000);
    }
}
