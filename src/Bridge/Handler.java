package Bridge;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Handler {

    public static final Scanner sc = new Scanner(System.in);

    public static String getString(String welcome, String msg) {
        boolean check = true;
        String result = "";
        do {
            System.out.print(welcome);
            result = sc.nextLine();
            if (result.isEmpty()) {
                System.err.println(msg);
            } else {
                check = false;
            }
        } while (check);
        return result;
    }

    public static String getStringreg(String welcome, String pattern, String msg, String msgreg) {
        boolean check = true;
        String result = "";
        do {

            System.out.print(welcome);
            result = sc.nextLine();
            if (result.isEmpty()) {
                System.err.println(msg);
            } else if (!result.matches(pattern)) {
                System.err.println(msgreg);
            } else {
                check = false;
            }
        } while (check);
        return result;
    }

    public static String getBlankString(String welcome) {
        String input = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print(welcome);
            input = reader.readLine().trim();
        } catch (Exception e) {
            System.err.println("Error input. Please try again.");
        }
        return input;
    }

    public static int getBlankInt(String welcome, int min, int max) {
        boolean check = true;
        int number = 0;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        do {
            try {
                System.out.print(welcome);
                String input = reader.readLine().trim();
                if (input.isEmpty()) {
                    check = false;
                } else {
                    number = Integer.parseInt(input);
                    if (number < min || number > max) {
                        System.err.println("Number must be between " + min + " and " + max);
                    } else {
                        check = false;
                    }
                }
            } catch (NumberFormatException e) {
                System.err.println("Please input a valid number.");
            } catch (Exception e) {
                System.err.println("An error occurred while reading input.");
            }
        } while (check);
        return number;
    }

    public static int getInt(String welcome, int min, int max) {
        boolean check = true;
        int number = 0;
        do {
            try {

                System.out.print(welcome);
                number = Integer.parseInt(sc.nextLine());
                if (number < min || number > max) {
                    System.err.println("Number must be between " + min + " and " + max);
                } else {
                    check = false;
                }
            } catch (Exception e) {
                System.err.println("Please input a valid number");
            }
        } while (check);
        return number;
    }

    public static String generateCode(String welcome, int length, int currentSize) {
        String format = "%0" + length + "d";
        return welcome + String.format(format, currentSize);
    }
}
