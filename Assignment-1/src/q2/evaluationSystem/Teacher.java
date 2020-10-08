package q2.evaluationSystem;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Teacher extends Thread{
    private ArrayList<ArrayList<String> > commandList;
    Semaphore semaphore;

    Teacher(String name, int priority, Semaphore semaphore) {
        setName(name);
        setPriority(priority);
        this.semaphore = semaphore;
        commandList = new ArrayList<>();
    }

    public void updateCommandList(String rollNo, String marksChange) {
        ArrayList<String> command = new ArrayList<>();
        command.add(rollNo);
        command.add(marksChange);
        commandList.add(command);
    }

    @Override
    public void run() {
        for(ArrayList<String> command : commandList) {
            try {
                semaphore.acquire();
                SharedData.updateStudData(command.get(0), Integer.parseInt(command.get(1)), getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
        }
        commandList.clear();
    }
}
