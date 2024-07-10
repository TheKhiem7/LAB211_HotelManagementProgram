package UI;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu extends ArrayList<String> {

    public Menu() {
        super();
    }

    public int getUserChoice() {
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        System.out.println("|            1 - Add a new hotel                 |");
        System.out.println("|            2 - Verify a hotel's ID             |");
        System.out.println("|            3 - Modify hotel information        |");
        System.out.println("|            4 - Remove a hotel entry            |");
        System.out.println("|            5 - Look for hotels                 |");
        System.out.println("|            6 - Show a list of hotels           |");
        System.out.println("|            7 - Search by District              |");
        System.out.println("|            8 - Quit the program                |");
        System.out.println("|                                                |");
        System.out.println("|================================================|");

        do {
            System.out.print("| Please choose an option (1-8): ");

            try {
                choice = Integer.parseInt(sc.nextLine());
                if (choice < 1 || choice > 9) {
                    System.err.println("Invalid option, pleace choose option in the menu above\n");
                }
            } catch (Exception e) {
                System.err.println("Invalid syntax, pleace type number show in the menu above\n");
            }
        } while (choice < 1 || choice > 9);
        return choice;
    }
}
