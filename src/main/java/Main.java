import utility.DatabaseOperations;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Get Pincode by Area");
            System.out.println("2. Get Area by Pincode");
            System.out.println("3. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Area: ");
                    String area = scanner.nextLine();
                    String pincode = DatabaseOperations.getPincode(area);
                    if (pincode != null) {
                        System.out.println("Pincode for area " + area + " is: " + pincode);
                    } else {
                        System.out.println("Area not found.");
                    }
                    break;
                case 2:
                    System.out.print("Enter Pincode: ");
                    String pincodeInput = scanner.nextLine();
                    String areaName = String.valueOf(DatabaseOperations.getAreas(pincodeInput));
                    if (areaName != null) {
                        System.out.println("Area for pincode " + pincodeInput + " is: " + areaName);
                    } else {
                        System.out.println("Pincode not found.");
                    }
                    break;
                case 3:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}