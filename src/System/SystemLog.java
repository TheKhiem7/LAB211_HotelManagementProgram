package System;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class SystemLog {

    private static String countfile = "HotelCount.dat";

    public static void saveSystemCreated(int count) {
        try {
            File f = new File(countfile);
            if (!f.exists()) {
                f.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(f);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(count);
            printWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int loadSystemCreated() {
        int count = 0;
        try {
            File f = new File(countfile);
            if (!f.exists()) {
                saveSystemCreated(count);
            } else {
                FileReader fileReader = new FileReader(f);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String countStr = bufferedReader.readLine();
                count = Integer.parseInt(countStr);
                bufferedReader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public static void increaseSystemCreated() {
        int count = loadSystemCreated();
        count++;
        saveSystemCreated(count);
    }
}
