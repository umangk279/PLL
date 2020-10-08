package q2.evaluationSystem;

import java.io.*;
import java.util.*;
import java.util.concurrent.Semaphore;

// Class to store shared data and functions
public class SharedData {
    // Map to store student information
    public static HashMap<String, ArrayList<String>> studData;
    // Semaphore for studData
    public static Semaphore semaphore;

    // Function to read the student data from stud_info.txt
    public static void readStudData() throws IOException {

        FileReader fileReader = new FileReader("src/q2/evaluationSystem/stud_info.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        // reading the file line by line
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            String[] data = line.split(",");
            ArrayList<String> dataEntry = new ArrayList<>();
            dataEntry.add(data[0]);
            dataEntry.add(data[1]);
            dataEntry.add(data[2]);
            dataEntry.add(data[3]);
            dataEntry.add(data[4]);
            // adding the entry in stud_info to our data
            studData.put(data[0], dataEntry);
        }
    }

    // Function to update the stud data
    public static void updateStudData(String rollNo, int marksChange, String teacher) {

        // if the roll does not exist in the data
        if(!studData.containsKey(rollNo)) {
            System.out.println(rollNo + " is not present in stud_info.txt");
            return;
        }

        // get the data entry corresponding to the rollno
        ArrayList<String> dataEntry = studData.get(rollNo);

        String lastModifiedBy = dataEntry.get(4);
        // If the file was last modified by CC, and the current updater is not CC
        if(lastModifiedBy.equals("CC") && !teacher.equals("CC")) {
            return;
        }

        // change the data entry with updated information
        int modifiedMarks = Integer.parseInt(dataEntry.get(3)) + marksChange;
        dataEntry.set(3, Integer.toString(modifiedMarks));
        dataEntry.set(4, teacher);
        // update the data entry in studData
        studData.replace(rollNo, dataEntry);
    }

    // Function that writes back the changes to files
    public static void updateFiles() throws IOException {

        FileWriter fileWriter = new FileWriter("src/q2/evaluationSystem/stud_info.txt");
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        // write the updated data to buffer for stud_info
        for(Map.Entry<String, ArrayList<String>> entry : studData.entrySet()) {
            ArrayList<String> arr = entry.getValue();
            bufferedWriter.append(arr.get(0));
            for (int i = 1; i < arr.size(); i++) {
                bufferedWriter.append(',');
                bufferedWriter.append(arr.get(i));
            }
            bufferedWriter.newLine();
        }

        // flush and close the buffer reader
        bufferedWriter.flush();
        bufferedWriter.close();

        // making an array list of all data entries
        ArrayList<ArrayList<String> > allDataArrays = new ArrayList<>();
        for(Map.Entry<String, ArrayList<String>> entry : studData.entrySet()) {
            ArrayList<String> arr = entry.getValue();
            allDataArrays.add(arr);
        }

        //  writing data sorted by roll no back to stud_info_rollno
        fileWriter = new FileWriter("src/q2/evaluationSystem/stud_info_roll_no.txt");
        bufferedWriter = new BufferedWriter(fileWriter);

        // sort the data using roll numbers
        Collections.sort(allDataArrays, new Comparator<ArrayList<String>>() {
            @Override
            public int compare(ArrayList<String> t1, ArrayList<String> t2) {
                return t1.get(0).compareTo(t2.get(0));
            }
        });

        // write the data line by line
        for(ArrayList<String> entry : allDataArrays) {
            bufferedWriter.append(entry.get(0));
            for (int i = 1; i < entry.size(); i++) {
                bufferedWriter.append(',');
                bufferedWriter.append(entry.get(i));
            }
            bufferedWriter.newLine();
        }
        // flush and close the buffer reader
        bufferedWriter.flush();
        bufferedWriter.close();

        //  writing data sorted by name back to stud_info_name
        fileWriter = new FileWriter("src/q2/evaluationSystem/stud_info_name.txt");
        bufferedWriter = new BufferedWriter(fileWriter);

        // sort the data using roll names
        Collections.sort(allDataArrays, new Comparator<ArrayList<String>>() {
            @Override
            public int compare(ArrayList<String> t1, ArrayList<String> t2) {
                return t1.get(1).compareTo(t2.get(1));
            }
        });

        // write the data line by line
        for(ArrayList<String> entry : allDataArrays) {
            bufferedWriter.append(entry.get(0));
            for (int i = 1; i < entry.size(); i++) {
                bufferedWriter.append(',');
                bufferedWriter.append(entry.get(i));
            }
            bufferedWriter.newLine();
        }
        // flush and close the buffer reader
        bufferedWriter.flush();
        bufferedWriter.close();
    }
}
