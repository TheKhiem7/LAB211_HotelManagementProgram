package System;

import Core.Hotel;
import Bridge.Handler;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

public class Manager {

    private String filename = "Hotel.dat";
    private static List<Hotel> hotels = new ArrayList();
    private static List<Hotel> searchResults = new ArrayList<>();
    private static int lastHotelId = 0;

//  ===================================================================================================================================
    //OPTION_1
    public void addNewHotel() {
        String hotelId = generateHotelId();
        String hotelName = Handler.getString("Enter hotel name: ", "Hotel name Empty choice, please choose the option.");
        int hotelRoomAvailable = Handler.getInt("Enter number of available rooms: ", 0, 500);
        String hotelAddress = Handler.getString("Enter hotel address: ", "Hotel address Empty choice, please choose the option.");
        String hotelPhone = Handler.getStringreg("Enter hotel phone number (0xxxxxxxxx): ", "0\\d{9}",
                "Phone number Empty choice, please choose the option!", "Phone number must start with 0 and have 10 digits !");
        int hotelRating = Handler.getInt("Enter hotel rating (from 1 to 5): ", 1, 5);

        Hotel hotel = new Hotel(hotelId, hotelName, hotelRoomAvailable,
                hotelAddress, hotelPhone, hotelRating);
        hotels.add(hotel);

        SystemLog.increaseSystemCreated();

        System.out.println("New hotel added successfully.");
        saveToFile(filename);

        String continueDo;
        do {
            continueDo = Handler.getString("Do you want to continue to add new hotel? (Y/N): ", "Please make your choice");
            if (continueDo.equalsIgnoreCase("n")) {
                System.out.println("Program will return to the main menu");
            } else if (continueDo.equalsIgnoreCase("y")) {
                addNewHotel();
            } else {
                System.err.println("Invalid input. Please enter 'Y' or 'N'.");
            }
        } while (!continueDo.equalsIgnoreCase("y") && !continueDo.equalsIgnoreCase("n"));
    }

    private static String generateHotelId() {
        int count = SystemLog.loadSystemCreated();
        lastHotelId = count + 1;
        String hotelId = Handler.generateCode("HID", 3, lastHotelId);
        return hotelId;
    }

    public void addFromFile(String filename) {
        try {
            File f = new File(filename);
            if (!f.exists()) {
                f.createNewFile();
                return;
            }
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String details;
            while ((details = br.readLine()) != null) {

                StringTokenizer stk = new StringTokenizer(details, ";");

                String hotelId = stk.nextToken().toUpperCase();

                String hotelName = stk.nextToken().toUpperCase();

                int hotelRoomAvailable = Integer.parseInt(stk.nextToken());

                String hotelAddress = stk.nextToken().toUpperCase();

                String hotelPhone = stk.nextToken();

                int hotelRating = Integer.parseInt(stk.nextToken());

                Hotel hotel = new Hotel(hotelId, hotelName, hotelRoomAvailable, hotelAddress, hotelPhone, hotelRating);

                hotels.add(hotel);
            }
            br.close();
            fr.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void saveToFile(String filename) {
        if (hotels.isEmpty()) {
            System.out.println("Empty List");
            return;
        }
        try {
            File f = new File(filename);
            FileWriter fw = new FileWriter(f);
            PrintWriter pw = new PrintWriter(fw);
            for (Hotel hotel : hotels) {
                pw.println(
                        hotel.getHotelId() + ";"
                        + hotel.getHotelName() + ";"
                        + hotel.getHotelRoomAvailable() + ";"
                        + hotel.getHotelAddress() + ";"
                        + hotel.getHotelPhone() + ";"
                        + hotel.getHotelRating()
                );
            }
            pw.close();
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

//  ===================================================================================================================================
    //OPTION_2
    public void checkExistHotel() {
        String hotelId = Handler.getStringreg("Enter hotel ID (Ex: HIDxxx): ", "HID\\d{3}",
                "Hotel ID Empty choice, please choose the option!", "Wrong ID format!");

        for (Hotel hotel : hotels) {
            if (hotel.getHotelId().equalsIgnoreCase(hotelId)) {
                searchResults.add(hotel);
            }
        }

        if (searchResults.isEmpty()) {
            System.out.println("No hotel found");
            searchHotelById();
        } else {
            System.out.println("Hotel does Exist!");
            System.out.println("|========|===============================|================|=======================================================================|============|========|");
            String header = String.format("|%-8s| %-30s| %-15s| %-70s| %-11s| %-7s|",
                    " Id", "Hotel Name", "Room Available", "Hotel Address", "Phone", "Rating");
            System.out.println(header);
            System.out.println("|--------|-------------------------------|----------------|-----------------------------------------------------------------------|------------|--------|");
            for (Hotel hotel : searchResults) {
                String p = String.format("%-9s| %-30s| %-15s| %-70s| %-11s| %-7s|", "| "
                        + hotel.getHotelId(), hotel.getHotelName(), hotel.getHotelRoomAvailable(),
                        hotel.getHotelAddress(), hotel.getHotelPhone(), hotel.getHotelRating() + " star");
                System.out.println(p);
            }
            System.out.println("|========|===============================|================|=======================================================================|============|========|");
        }
        searchResults.clear();
    }

//  ===================================================================================================================================
    //OPTION_3
    public void updateHotel() {
        String hotelId = Handler.getStringreg("Enter hotel ID you want to modify (Ex: HIDxxx): ", "HID\\d{3}",
                "Hotel ID Empty choice, please choose the option!", "Wrong ID format!");

        Hotel hotelToUpdate = null;
        for (Hotel hotel : hotels) {
            if (hotel.getHotelId().equalsIgnoreCase(hotelId)) {
                hotelToUpdate = hotel;
                searchResults.add(hotel);
                break;
            }
        }

        if (hotelToUpdate == null) {
            System.err.println("Hotel does not exist");
            updateHotel();
        }

        System.out.println("Search Results:");
        for (Hotel hotel : searchResults) {
            System.out.println("|========|===============================|================|=======================================================================|============|========|");
            String header = String.format("|%-8s| %-30s| %-15s| %-70s| %-11s| %-7s|",
                    " Id", "Hotel Name", "Room Available", "Hotel Address", "Phone", "Rating");
            System.out.println(header);
            System.out.println("|--------|-------------------------------|----------------|-----------------------------------------------------------------------|------------|--------|");
            for (Hotel hotels : searchResults) {

                String p = String.format("%-9s| %-30s| %-15s| %-70s| %-11s| %-7s|", "| "
                        + hotels.getHotelId(), hotels.getHotelName(), hotels.getHotelRoomAvailable(),
                        hotels.getHotelAddress(), hotels.getHotelPhone(), hotels.getHotelRating() + " star");
                System.out.println(p);
            }
            System.out.println("|========|===============================|================|=======================================================================|============|========|");
        }

        System.out.println("Give new information to modify (Press Enter to skip)");

        String hotelName = Handler.getBlankString("Enter hotel name: ");
        if (hotelName != null && !hotelName.trim().isEmpty()) {
            hotelToUpdate.setHotelName(hotelName);
        }

        String hotelRoomInput;
        int hotelRoomAvailable = 0;
        do {
            hotelRoomInput = Handler.getBlankString("Enter number of available rooms: ");
            if (!hotelRoomInput.isEmpty()) {
                hotelRoomAvailable = Integer.parseInt(hotelRoomInput);
                if (hotelRoomAvailable >= 0 && hotelRoomAvailable <= 500) {
                    hotelToUpdate.setHotelRoomAvailable(hotelRoomAvailable);
                } else {
                    System.err.println("Room availability should be between 0 and 500.");
                }
            }
        } while (!hotelRoomInput.isEmpty() && (hotelRoomAvailable < 0 || hotelRoomAvailable > 1000));

        String hotelAddress = Handler.getBlankString("Enter hotel address: ");
        if (hotelAddress != null && !hotelAddress.trim().isEmpty()) {
            hotelToUpdate.setHotelAddress(hotelAddress);
        }

        String hotelPhone;
        boolean isValidPhone = false;
        do {
            hotelPhone = Handler.getBlankString("Enter hotel phone number (Ex: 0xxxxxxxxx): ");
            if (hotelPhone != null && !hotelPhone.isEmpty()) {
                if (hotelPhone.matches("0\\d{9}")) {
                    hotelToUpdate.setHotelPhone(hotelPhone);
                    isValidPhone = true;
                } else {
                    System.err.println("Invalid Phone Number");
                }
            } else {
                isValidPhone = true;
            }
        } while (!isValidPhone);

        String hotelRatingInput;
        int hotelRating = 0;
        do {
            hotelRatingInput = Handler.getBlankString("Enter hotel rating (from 1 to 5): ");
            if (!hotelRatingInput.isEmpty()) {
                hotelRating = Integer.parseInt(hotelRatingInput);
                if (hotelRating >= 1 && hotelRating <= 5) {
                    hotelToUpdate.setHotelRating(hotelRating);
                } else {
                    System.err.println("Rating should be between 1 and 6.");
                }
            }
        } while (!hotelRatingInput.isEmpty() && (hotelRating < 1 || hotelRating > 5));

        System.out.println("Hotel information updated successfully.");
        saveToFile(filename);

        String continueDo;
        do {
            continueDo = Handler.getString("Do you want to continue to update? (Y/N): ", "Empty choice, please choose the option");
            if (continueDo.equalsIgnoreCase("n")) {
                searchResults.clear();
                System.out.println("Returning to the main menu...");
            } else if (continueDo.equalsIgnoreCase("y")) {
                searchResults.clear();
                updateHotel();
            } else {
                System.err.println("Invalid input. Please enter 'Y' or 'N'.");
            }
        } while (!continueDo.equalsIgnoreCase("y") && !continueDo.equalsIgnoreCase("n"));
    }

//  ===================================================================================================================================
    //OPTION_4
    public void deleteHotel() {
        String hotelId = Handler.getStringreg("Enter hotel ID (Ex: HIDxxx): ", "HID\\d{3}",
                "Hotel ID Empty choice, please choose the option!", "Wrong ID format!");

        Hotel hotelToDelete = null;
        for (Hotel hotel : hotels) {
            if (hotel.getHotelId().equalsIgnoreCase(hotelId)) {
                hotelToDelete = hotel;
                searchResults.add(hotel);
                break;
            }
        }

        if (hotelToDelete == null) {
            System.err.println("Hotel does not exist");
            deleteHotel();
        }

        System.out.println("Search Results:");
        for (Hotel hotel : searchResults) {
            System.out.println("|========|===============================|================|=======================================================================|============|========|");
            String header = String.format("|%-8s| %-30s| %-15s| %-70s| %-11s| %-7s|",
                    " Id", "Name", "RoomAvailable", "Address", "Phone", "Rating");
            System.out.println(header);
            System.out.println("|--------|-------------------------------|----------------|-----------------------------------------------------------------------|------------|--------|");
            for (Hotel hotels : searchResults) {

                String p = String.format("%-9s| %-30s| %-15s| %-70s| %-11s| %-7s|", "| "
                        + hotels.getHotelId(), hotels.getHotelName(), hotels.getHotelRoomAvailable(),
                        hotels.getHotelAddress(), hotels.getHotelPhone(), hotels.getHotelRating() + " star");
                System.out.println(p);
            }
            System.out.println("|========|===============================|================|=======================================================================|============|========|");
        }

        String confirmDelete;
        do {
            confirmDelete = Handler.getString("Do you really want to delete this hotel? (Y/N): ", "Cannot empty");
            if (confirmDelete.equalsIgnoreCase("n")) {
                System.out.println("Delete canceled");
            } else if (confirmDelete.equalsIgnoreCase("y")) {
                hotels.remove(hotelToDelete);
                System.out.println("Delete successfully");
            } else {
                System.err.println("Invalid input. Please enter 'Y' or 'N'.");
            }
        } while (!confirmDelete.equalsIgnoreCase("y") && !confirmDelete.equalsIgnoreCase("n"));
        saveToFile(filename);

        String continueDo;
        do {
            continueDo = Handler.getString("Do you want to continue to delete? (Y/N): ", "Empty choice, please choose the option");
            if (continueDo.equalsIgnoreCase("n")) {
                searchResults.clear();
                System.out.println("Returning to the main menu...");
            } else if (continueDo.equalsIgnoreCase("y")) {
                searchResults.clear();
                deleteHotel();
            } else {
                System.err.println("Invalid input. Please enter 'Y' or 'N'.");
            }
        } while (!continueDo.equalsIgnoreCase("y") && !continueDo.equalsIgnoreCase("n"));
    }

//  ===================================================================================================================================
    //OPTION_5
    public void searchHotel() {
        System.out.println("Look for hotel");
        System.out.println("1.By hotel ID");
        System.out.println("2.By hotel name");
        int searchChoice = Handler.getInt("Please enter your choice: ", 1, 2);

        switch (searchChoice) {
            case 1:
                searchHotelById();
                break;
            case 2:
                searchHotelByName();
                break;
        }
    }
//  ***************************************************

    private void searchHotelById() {
        String hotelId = Handler.getStringreg("Enter hotel ID (Ex: HIDxxx): ", "HID\\d{3}",
                "Hotel ID Empty choice, please choose the option!", "Wrong ID format!");

        for (Hotel hotel : hotels) {
            if (hotel.getHotelId().equalsIgnoreCase(hotelId)) {
                searchResults.add(hotel);
            }
        }

        if (searchResults.isEmpty()) {
            System.out.println("No hotels found with the given ID.");
            searchHotelById();
        } else {
            System.out.println("Search Results:");
            System.out.println("|========|===============================|================|=======================================================================|============|========|");
            String header = String.format("|%-8s| %-30s| %-15s| %-70s| %-11s| %-7s|",
                    " Id", "Name", "RoomAvailable", "Address", "Phone", "Rating");
            System.out.println(header);
            System.out.println("|--------|-------------------------------|----------------|-----------------------------------------------------------------------|------------|--------|");
            for (Hotel hotel : searchResults) {
                String p = String.format("%-9s| %-30s| %-15s| %-70s| %-11s| %-7s|", "| "
                        + hotel.getHotelId(), hotel.getHotelName(), hotel.getHotelRoomAvailable(),
                        hotel.getHotelAddress(), hotel.getHotelPhone(), hotel.getHotelRating() + " star");
                System.out.println(p);
            }
            System.out.println("|========|===============================|================|=======================================================================|============|========|");
        }
        searchResults.clear();
    }

//  ***************************************************
    private void searchHotelByName() {
        String hotelName = Handler.getString("Enter hotel name: ", "Hotel name Empty choice, please choose the option.");

        for (Hotel hotel : hotels) {
            if (hotel.getHotelName().toLowerCase().contains(hotelName.toLowerCase())) {
                searchResults.add(hotel);
            }
        }

        if (searchResults.isEmpty()) {
            System.out.println("No hotels found with the given name.");
            searchHotelByName();
        } else {
            System.out.println("Search Results:");
            System.out.println("|========|===============================|================|=======================================================================|============|========|");
            String header = String.format("|%-8s| %-30s| %-15s| %-70s| %-11s| %-7s|",
                    " Id", "Name", "RoomAvailable", "Address", "Phone", "Rating");
            System.out.println(header);
            System.out.println("|--------|-------------------------------|----------------|-----------------------------------------------------------------------|------------|--------|");
            for (Hotel hotel : searchResults) {
                String Result = String.format("%-9s| %-30s| %-15s| %-70s| %-11s| %-7s|", "| "
                        + hotel.getHotelId(), hotel.getHotelName(), hotel.getHotelRoomAvailable(),
                        hotel.getHotelAddress(), hotel.getHotelPhone(), hotel.getHotelRating() + " star");
                System.out.println(Result);
                System.out.println("|--------|-------------------------------|----------------|-----------------------------------------------------------------------|------------|--------|");
                System.out.println("");
            }
        }
        searchResults.clear();
    }

//  ===================================================================================================================================
    //OPTION_6
    public void displayHotels() {
        if (hotels.isEmpty()) {
            System.err.println("No hotels found.");
        } else {
            hotels.sort(Comparator.comparing(Hotel::getHotelName).reversed());

            System.out.println("                                                          ***** HOTEL LIST ***** ");
            System.out.println("|========|===============================|================|=======================================================================|============|========|");
            String header = String.format("|%-8s| %-30s| %-15s| %-70s| %-11s| %-7s|",
                    " Id", "Hotel Name", "Room Available", "Hotel Address", "Phone", "Rating");
            System.out.println(header);
            System.out.println("|--------|-------------------------------|----------------|-----------------------------------------------------------------------|------------|--------|");
            for (Hotel hotels : hotels) {

                String p = String.format("%-9s| %-30s| %-15s| %-70s| %-11s| %-7s|", "| "
                        + hotels.getHotelId(), hotels.getHotelName(), hotels.getHotelRoomAvailable(),
                        hotels.getHotelAddress(), hotels.getHotelPhone(), hotels.getHotelRating() + " star");
                System.out.println(p);
                System.out.println("|--------|-------------------------------|----------------|-----------------------------------------------------------------------|------------|--------|");
            }
        }
    }
    
    
    
//    =====================================================
        public void listDistrict() {
        if (hotels.isEmpty()) {
            System.err.println("No hotels found.");
        } else {
            hotels.sort(Comparator.comparing(Hotel::getHotelAddress).reversed());

            System.out.println("                                                          ***** HOTEL LIST ***** ");
            System.out.println("|========|===============================|================|=======================================================================|============|========|");
            String header = String.format("|%-8s| %-30s| %-15s| %-70s| %-11s| %-7s|",
                    " Id", "Hotel Name", "Room Available", "Hotel Address", "Phone", "Rating");
            System.out.println(header);
            System.out.println("|--------|-------------------------------|----------------|-----------------------------------------------------------------------|------------|--------|");
            for (Hotel hotels : hotels) {

                String p = String.format("%-9s| %-30s| %-15s| %-70s| %-11s| %-7s|", "| "
                        + hotels.getHotelId(), hotels.getHotelName(), hotels.getHotelRoomAvailable(),
                        hotels.getHotelAddress(), hotels.getHotelPhone(), hotels.getHotelRating() + " star");
                System.out.println(p);
                System.out.println("|--------|-------------------------------|----------------|-----------------------------------------------------------------------|------------|--------|");
            }
        }
    }
    

    //  ===================================================================================================================================
    //OPTION_7
    public void searchHotelByDistrict() {
        System.out.println("\n|========================== SPECIAL SEARCH ==========================|");
        String hotelAddress = Handler.getBlankString("District you want to find: ");
        String hotelPhone = Handler.getBlankString("4 last digit of hotel phone: ");

        for (Hotel hotel : hotels) {
            if ((hotel.getHotelAddress().toLowerCase().contains(hotelAddress.toLowerCase()) || hotelAddress.isEmpty())
                    && (hotel.getHotelPhone().substring(6, 10).equals(hotelPhone) || hotelPhone.isEmpty())) {
                searchResults.add(hotel);
            }
        }
//      *****************************************************************        
        if (searchResults.isEmpty()) {
            System.err.println("We can not found in any hotel with infomation you gave");
            ContinueSpecialSearch();

//      *****************************************************************                    
        } else {
            searchResults.sort(Comparator.comparing(Hotel::getHotelRating).reversed()
                    .thenComparing(Comparator.comparing(Hotel::getHotelRoomAvailable)));
            System.out.println("|========|===============================|================|=======================================================================|============|========|");
            String header = String.format("|%-8s| %-30s| %-15s| %-70s| %-11s| %-7s|",
                    " Id", "Name", "RoomAvailable", "Address", "Phone", "Rating");
            System.out.println(header);
            System.out.println("|--------|-------------------------------|----------------|-----------------------------------------------------------------------|------------|--------|");
            for (Hotel hotel : searchResults) {
                String Result = String.format("%-9s| %-30s| %-15s| %-70s| %-11s| %-7s|", "| "
                        + hotel.getHotelId(), hotel.getHotelName(), hotel.getHotelRoomAvailable(),
                        hotel.getHotelAddress(), hotel.getHotelPhone(), hotel.getHotelRating() + " star");
                System.out.println(Result);
                System.out.println("|--------|-------------------------------|----------------|-----------------------------------------------------------------------|------------|--------|");
            }
            ContinueSpecialSearch();
        }
        
    }

    //  ===================================================================================================================================
    public void ContinueSpecialSearch() {
        String continueExe;
        do {
            continueExe = Handler.getString("Do you want to continue to special search? (Y/N): ", "Empty choice, please choose the option");
            if (continueExe.equalsIgnoreCase("n")) {
                searchResults.clear();
                System.out.println("Returning to the main menu...");
            } else if (continueExe.equalsIgnoreCase("y")) {
                searchResults.clear();
                searchHotelByDistrict();
            } else {
                System.err.println("Invalid input. Please enter 'Y' or 'N'.");
            }
        } while (!continueExe.equalsIgnoreCase("y") && !continueExe.equalsIgnoreCase("n"));
    }
}
