package cams;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class StaffMenuState implements DashboardState {
    @Override
    public void display(Dashboard dashboard) {
        int option;
        String input;
        Scanner sc = new Scanner(System.in);
        User user = dashboard.getAuthenticatedUser();

        // Code to display options
        System.out.println("-- DASHBOARD --");
        System.out.println("STAFF Username: " + user.getUserID() + ", Faculty: " + user.getFaculty());
        System.out.println("-- OPTIONS --");
        System.out.println("(1) Change your password");
        System.out.println("(2) Logout");
        System.out.println("(3) View my Camps");

        if (user instanceof Staff) {
            System.out.println("(4) View all Camps");
            System.out.println("(5) Edit your Camp(s)");
            System.out.println("(6) Create a new Camp");
        }
        option = sc.nextInt();

        switch (option){
            case 1:
                String newPassword;

                System.out.println("Enter new password:");
                newPassword = sc.next();
                user.setPassword(newPassword);
                System.out.println("Password successfully changed!");
                break;

            case 2:
                dashboard.logout();

            case 3:
                user.displayMyCamps();

            case 4:
                user.viewAllCamps(); //maybe put this method for viewing available camps in camp repo instead
                break;
            //TODO implement camp editing (case 5)
            case 5:
                user.viewAllCamps();
                break;
            case 6:
                int value;
                int visible;
                String campName;
                LocalDate startDate = null;
                LocalDate endDate = null;
                LocalDate closingDate = null;
                String location;
                int slots = 0;
                Student[] studentsList = null;
                Student[] committee = null;
                String description;
                String inCharge = user.getUserID();
                Faculty visibility = Faculty.ALL;

                System.out.println("You are creating a new Camp.");
                System.out.println("What is its name?");
                campName = sc.next();

                try {
                    //INPUT START DATE
                    System.out.println("Enter start date (format: yyyy-MM-dd): ");
                    input = sc.nextLine();
                    startDate = LocalDate.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    System.out.println("Date entered: " + startDate);

                    while (true) {
                        //INPUT end date
                        System.out.println("Enter end date (format: yyyy-MM-dd): ");
                        input = sc.nextLine();

                        endDate = LocalDate.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        System.out.println("Date entered: " + endDate);

                        if (startDate.isEqual(endDate) || endDate.isAfter(startDate)) {
                            break;
                        } else {
                            System.out.println("End Date is before Start Date. Please re-enter End Date.");
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Invalid date format!");
                }

                try {
                    System.out.println("Enter registration closing date (format: yyyy-MM-dd): ");
                    input = sc.nextLine();
                    LocalDate date = LocalDate.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    System.out.println("Date entered: " + date);
                } catch (Exception e) {
                    System.out.println("Invalid date format!");
                }

                do {
                    System.out.println("Is this open to the whole of NTU (1), or just only to your faculty? (2)");
                    value = sc.nextInt();
                    if (value == 2) {
                        visibility = user.getFaculty();
                    }
                } while (value != 1 && value != 2);

                System.out.println("Where is its location? (Leave no spacings)");
                location = sc.next();

                System.out.println("How many slots are available?");
                slots = sc.nextInt();
                //COMMENT why the need to leave no spacing? what does this mean
                System.out.println("Please provide a description. (Leave no spacings)");
                description = sc.next();
                /*
                do {
                    System.out.println("Should the Camp be visible? [1: YES, 0: NO]");
                    visible = sc.nextInt();
                    if (visible == 1) {
                        visibility = true;
                    } else if (visible == 0) {
                        visibility = false;
                    }
                } while (visible != 1 && visible != 0);
                */
                //TODO creation of camp committee with input size
                Camp newCamp = new Camp.Builder()
                    .campName(campName)
                    .startDate(startDate)
                    .endDate(endDate)
                    .closingDate(closingDate)
                    .location(location)
                    .totalSlots(slots)
                    .description(description)
                    .inCharge(user.getUserID())
                    .visibility(user.getFaculty())
                    .build();
                System.out.println("Camp created successfully!");

        }
    }
}
