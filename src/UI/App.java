package UI;

import System.Manager;

public class App {

    public static void main(String[] args) {
        String filename = "Hotel.dat";
        Menu menu = new Menu();
        Manager manage = new Manager();
        int userChoice;
        manage.addFromFile(filename);

        do {
            System.out.println("|========== HOTEL MANAGEMENT PROGRAM ============|");
            System.out.println("|                                                |");
            userChoice = menu.getUserChoice();
            switch (userChoice) {
                case 1:
                    manage.addNewHotel();
                    break;
                case 2:
                    manage.checkExistHotel();
                    break;
                case 3:
                    manage.updateHotel();
                    break;
                case 4:
                    manage.deleteHotel();
                    break;
                case 5:
                    manage.searchHotel();
                    break;
                case 6:
                    manage.displayHotels();
                    break;
                case 7:
                    manage.searchHotelByDistrict();
                    break;
                case 8:
                    System.out.println("THANK YOU ^-^");
                    System.exit(0);
                    break;
            }
        } while (userChoice > 0 || userChoice < 9);
    }
}
