package q3.modifiedCalculator;

import java.util.Timer;
import java.util.TimerTask;

public class Main {

    public static void main(String[] args) {
        UserInterface calculator = new UserInterface();
        calculator.setupUI();

        Timer timerNum = new Timer();
        timerNum.schedule(new TimerTask() {
            @Override
            public void run() {
                UserInterface.changeFocusNum();
            }
        }, 0, 1000);

        Timer timerFunc = new Timer();
        timerNum.schedule(new TimerTask() {
            @Override
            public void run() {
                UserInterface.changeFocusFunc();
            }
        }, 0, 1000);
    }
}
