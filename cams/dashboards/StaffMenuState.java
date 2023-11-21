package cams.dashboards;

import cams.Camp;
import cams.util.Faculty;
import cams.users.Staff;
import cams.users.User;
import cams.database.UnifiedCampRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class StaffMenuState implements DashboardState {
    //> FACULTY INFO.
    //> CHANGE PASSWORD
    //> LOGOUT (BACK TO LOG IN PAGE)
    @Override
    public void display(Dashboard dashboard) {
        int option;
        String input;
        Scanner sc = new Scanner(System.in);
        User user = dashboard.getAuthenticatedUser();

        // Code to display options
        System.out.println("                          DASHBOARD                           ");
        System.out.println("______________________________________________________________");
        System.out.println("STAFF Name: " + user.getName());
        System.out.println("Username: " + user.getUserID());
        System.out.println("Faculty: " + user.getFaculty());
        System.out.println("______________________________________________________________");
        System.out.println("                             MENU                              ");
        System.out.println("(1) Change your password");
        System.out.println("(2) Logout");
        System.out.println("(3) View my Camps");

        if (user instanceof Staff) {
            System.out.println("(4) View all Camps");
            System.out.println("(5) Edit your Camp(s)");
            System.out.println("(6) Create a new Camp");
            System.out.println("(7) Reply to my camps' suggestions");
            System.out.println("(8) Respond to camp enquiries");
        }
        System.out.printf("SELECT AN ACTION: ");
        option = sc.nextInt();
        sc.nextLine(); //consume new line
        System.out.println();

        switch (option){
            case 1:
                String newPassword;

                System.out.printf("Enter new password: ");
                newPassword = sc.next();
                user.setPassword(newPassword);
                System.out.println("Password successfully changed!");
                break;

            case 2:
                dashboard.logout();
                break;
            case 3:
                user.displayMyCamps();
                break;
            case 4:
                //TODO maybe put this method for viewing available camps in camp repo instead? for better encapsulation
                //Users should be able to see all camps available to their faculty, irregardless of timing clashes
                //so they can choose to withdraw to attend other camps
                user.viewAllCamps(); //maybe put this method for viewing available camps in camp repo instead
                break;
            //TODO implement camp editing (case 5)
            case 5:
                user.viewAllCamps();
                break;
            case 6:
                //TODO abstract all of this away into the camp organiser interface? or just leave it here?
                int value;
                int visible;
                String campName;
                LocalDate startDate;
                LocalDate endDate;
                LocalDate closingDate;
                String location;
                int attendeeSlots;
                int committeeSlots;
                String description;
                String inCharge = user.getUserID();
                Faculty visibility = Faculty.ALL;

                //Use TRY-CATCH block with WHILE loop for getting valid input
                //GET Camp Name
                while(true){
                    System.out.println("You are creating a new Camp.");
                    System.out.printf("Name of Camp: ");
                    campName = sc.nextLine();

                    UnifiedCampRepository repo = UnifiedCampRepository.getInstance();
                    if (repo.retrieveCamp(campName) == null) {
                        break; // Break the loop if the name doesn't exist in the map
                    }
                    System.out.println("This name already exists. Please enter a different name.");
                }
                //Get start and end dates
                while(true) {
                    try {
                        //INPUT START DATE
                        System.out.printf("Enter start date (dd-MM-yyyy): ");
                        input = sc.nextLine();
                        startDate = LocalDate.parse(input, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

                        if(!startDate.isAfter(LocalDate.now()))
                        {
                            System.out.println("Start date of camp must be after the date of creation.");
                            continue;
                        }

                        System.out.println("Start Date: " + startDate);

                        while (true) {
                            //INPUT end date
                            System.out.printf("Enter end date (dd-MM-yyyy): ");
                            input = sc.nextLine();

                            endDate = LocalDate.parse(input, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                            System.out.println("End Date: " + endDate);

                            if (startDate.isEqual(endDate) || endDate.isAfter(startDate)) {
                                break;
                            } else {
                                System.out.println("End Date is before Start Date. Please re-enter End Date.");
                            }
                        }
                        break;
                    } catch (Exception e) {
                        System.out.println("Invalid date format! Please try again.");
                    }
                }
                //Get closing date of registration
                while(true) {
                    try {
                        System.out.printf("Enter registration closing date (dd-MM-yyyy): ");
                        input = sc.nextLine();
                        closingDate = LocalDate.parse(input, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

                        //Closing date of registration must be before the start of the camp and after today
                        if(closingDate.isBefore(startDate) && closingDate.isAfter(LocalDate.now())){
                            System.out.println("Closing Date: " + closingDate);
                            break;
                        }
                        else {
                            System.out.println("Closing date for registration must be after today and before the camp begins.");
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid date format!");
                    }
                }

                //Get visibility
                while (true) {
                    System.out.printf("Camp is (1) open to the whole of NTU OR (2) only to your faculty: ");
                    input = sc.nextLine(); //Use nextLine instead of nextInt to prevent integer mismatch exception
                    if ("1".equals(input) || "2".equals(input)) {
                        value = Integer.parseInt(input); // Convert to integer

                        if (value == 1 || value == 2) {
                            //If user chooses to open only to faculty, set visibility accordingly.
                            //Otherwise, maintain the default visibility value (Open to all)
                            if(value == 2){
                                visibility = user.getFaculty();
                            }
                            System.out.println("Camp is available to " + visibility.toString());
                            break;
                        }
                        else {
                            System.out.println("Invalid input. Please enter 1 or 2.");
                        }
                    }
                }

                //GET location
                System.out.printf("Camp location: ");
                location = sc.nextLine().strip();

                //GET number of slots for attendees
                while (true) {
                    try{
                        System.out.printf("No. of attendee slots available: ");
                        attendeeSlots = sc.nextInt();
                        sc.nextLine(); //consume newline
                        if(attendeeSlots < 10 || attendeeSlots > 5000){
                            System.out.println("Camp must have minimum of 10 and maximum of 5000 attendee slots.");
                            continue;
                        }
                        break;
                    }
                    catch (InputMismatchException e){
                        System.out.println("Invalid input. Please enter an integer.");
                    }
                }

                //GET number of slots for committee
                while (true) {
                    try{
                        System.out.printf("No. of committee member slots available (Max 10): ");
                        committeeSlots = sc.nextInt();
                        sc.nextLine(); //consume newline

                        if(committeeSlots < 1)
                            System.out.println("Invalid number. Please create at least one slot. Try again.");

                        else if(committeeSlots > 10)
                            System.out.println("Maximum 10 committee members allowed. Try again.");

                        else break;
                    }
                    catch (InputMismatchException e){
                        System.out.println("Invalid input. Please enter an integer.");
                    }
                }

                //GET description
                System.out.printf("Please provide a description of the camp: ");
                description = sc.nextLine();

                /* Can't remember if this is part of the requirements or if visibility only refers to faculty-visibility
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

                //Use a builder for better readability
                Camp newCamp = new Camp.Builder()
                    .campName(campName)
                    .startDate(startDate)
                    .endDate(endDate)
                    .closingDate(closingDate)
                    .location(location)
                    .attendeeSlots(attendeeSlots)
                    .committeeSlots(committeeSlots)
                    .description(description)
                    .inCharge(user.getUserID())
                    .visibility(user.getFaculty())
                    .build();
                UnifiedCampRepository repo = UnifiedCampRepository.getInstance();
                repo.addCamp(newCamp);
                user.addCamp(newCamp);

                System.out.println("Camp created successfully!");
                break;
            case 7:
                dashboard.approverMenu();
                break;
            case 8:
                dashboard.replierMenu();
                break;
        }
    }
}
