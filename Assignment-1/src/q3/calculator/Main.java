package q3.calculator;

import java.util.Timer;
import java.util.TimerTask;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello q3");
        UserInterface calculator = new UserInterface();
        calculator.setupUI();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                UserInterface.changeFocus();
            }
        }, 0, 1000);
    }
}
