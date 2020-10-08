package q2.evaluationSystem;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

// Class that will be used for threading
public class Teacher extends Thread{

    // Commands that need to be executed by the thread
    private ArrayList<ArrayList<String> > commandList;
    // Semaphore for studData
    Semaphore semaphore;

    // Constructor
    Teacher(String name, int priority, Semaphore semaphore) {
        setName(name);
        setPriority(priority);
        this.semaphore = semaphore;
        commandList = new ArrayList<>();
    }

    // Function to update the command list that will be executed by a particular thread
    public void updateCommandList(String rollNo, String marksChange) {
        ArrayList<String> command = new ArrayList<>();
        command.add(rollNo);
        command.add(marksChange);
        commandList.add(command);
    }

    // Implementation of run that be executed when a thread is executed
    @Override
    public void run() {

        // Execute all the commands in the command list
        for(ArrayList<String> command : commandList) {
            try {
                // try to acquire the semaphore
                semaphore.acquire();
                // Update the studData when semaphore is acquired
                SharedData.updateStudData(command.get(0), Integer.parseInt(command.get(1)), getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // always release the semaphore after completing the update
                semaphore.release();
            }
        }
        // clear the list after all the commands are executed
        commandList.clear();
    }
}
