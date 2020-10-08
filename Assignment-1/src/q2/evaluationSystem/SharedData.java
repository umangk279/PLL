package q2.evaluationSystem;

import java.io.*;
import java.util.*;
import java.util.concurrent.Semaphore;

public class SharedData {
    public static HashMap<String, ArrayList<String>> studData;
    public static Semaphore semaphore;

    public static void readStudData() throws IOException {
        FileReader fileReader = new FileReader("src/q2/evaluationSystem/stud_info.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            String[] data = line.split(",");
            ArrayList<String> dataEntry = new ArrayList<>();
            dataEntry.add(data[0]);
            dataEntry.add(data[1]);
            dataEntry.add(data[2]);
            dataEntry.add(data[3]);
            dataEntry.add(data[4]);
            studData.put(data[0], dataEntry);
        }
    }

    public static void updateStudData(String rollNo, int marksChange, String teacher) {
        if(!studData.containsKey(rollNo)) {
            System.out.println(rollNo + " is not present in stud_info.txt");
            return;
        }
        ArrayList<String> dataEntry = studData.get(rollNo);
        String lastModifiedBy = dataEntry.get(4);
        if(lastModifiedBy.equals("CC") && !teacher.equals("CC")) {
            return;
        }
        int modifiedMarks = Integer.parseInt(dataEntry.get(3)) + marksChange;
        dataEntry.set(3, Integer.toString(modifiedMarks));
        dataEntry.set(4, teacher);
        studData.replace(rollNo, dataEntry);
    }

    public static void updateFiles() throws IOException {
        FileWriter fileWriter = new FileWriter("src/q2/evaluationSystem/stud_info.txt");
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        for(Map.Entry<String, ArrayList<String>> entry : studData.entrySet()) {
            ArrayList<String> arr = entry.getValue();
            bufferedWriter.append(arr.get(0));
            for (int i = 1; i < arr.size(); i++) {
                bufferedWriter.append(',');
                bufferedWriter.append(arr.get(i));
            }
            bufferedWriter.newLine();
        }

        bufferedWriter.flush();
        bufferedWriter.close();

        ArrayList<ArrayList<String> > allDataArrays = new ArrayList<>();
        for(Map.Entry<String, ArrayList<String>> entry : studData.entrySet()) {
            ArrayList<String> arr = entry.getValue();
            allDataArrays.add(arr);
        }

        fileWriter = new FileWriter("src/q2/evaluationSystem/stud_info_roll_no.txt");
        bufferedWriter = new BufferedWriter(fileWriter);

        Collections.sort(allDataArrays, new Comparator<ArrayList<String>>() {
            @Override
            public int compare(ArrayList<String> t1, ArrayList<String> t2) {
                return t1.get(0).compareTo(t2.get(0));
            }
        });

        for(ArrayList<String> entry : allDataArrays) {
            bufferedWriter.append(entry.get(0));
            for (int i = 1; i < entry.size(); i++) {
                bufferedWriter.append(',');
                bufferedWriter.append(entry.get(i));
            }
            bufferedWriter.newLine();
        }
        bufferedWriter.flush();
        bufferedWriter.close();

        fileWriter = new FileWriter("src/q2/evaluationSystem/stud_info_name.txt");
        bufferedWriter = new BufferedWriter(fileWriter);

        Collections.sort(allDataArrays, new Comparator<ArrayList<String>>() {
            @Override
            public int compare(ArrayList<String> t1, ArrayList<String> t2) {
                return t1.get(1).compareTo(t2.get(1));
            }
        });

        for(ArrayList<String> entry : allDataArrays) {
            bufferedWriter.append(entry.get(0));
            for (int i = 1; i < entry.size(); i++) {
                bufferedWriter.append(',');
                bufferedWriter.append(entry.get(i));
            }
            bufferedWriter.newLine();
        }
        bufferedWriter.flush();
        bufferedWriter.close();
    }
}
