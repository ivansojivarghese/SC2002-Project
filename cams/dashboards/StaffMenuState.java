package cams.dashboards;

import cams.Camp;
import cams.database.CampRepository;
import cams.users.*;
import cams.util.Faculty;
import cams.database.UnifiedCampRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class StaffMenuState implements DashboardState {
    private final Organiser organiser = new StaffOrganiserActions();
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
            System.out.println("(7) Respond to camp suggestions");
            System.out.println("(8) Respond to camp enquiries");
        }
        System.out.printf("SELECT AN ACTION: ");
        option = sc.nextInt();
        int index;
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
                //Users should be able to see all camps available to their facultyRestriction, irregardless of timing clashes
                //so they can choose to withdraw to attend other camps
                user.viewAllCamps(); //maybe put this method for viewing available camps in camp repo instead
                break;
            //TODO implement camp editing (case 5)
            case 5: // DELETE, AND TOGGLE camps
                dashboard.organiserMenu();
                break;
                
            case 6:
                //TODO abstract all of this away into the camp organiser interface? or just leave it here?
                int value;
                int visible;

                CampDetails campDetails = new CampDetails();
                CampRepository repo = UnifiedCampRepository.getInstance();

                //Use TRY-CATCH block with WHILE loop for getting valid input
                //GET Camp Name
                while(true){
                    System.out.println("You are creating a new Camp.");
                    System.out.printf("Name of Camp: ");
                    campDetails.setCampName(sc.nextLine());

                    if (organiser.isCampNameUnique(campDetails.getCampName())) {
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
                        campDetails.setStartDate(LocalDate.parse(input, DateTimeFormatter.ofPattern("dd-MM-yyyy")));

                        if(!campDetails.getStartDate().isAfter(LocalDate.now()))
                        {
                            System.out.println("Start date of camp must be after the date of creation.");
                            continue;
                        }

                        System.out.println("Start Date: " + campDetails.getStartDate());

                        while (true) {
                            //INPUT end date
                            System.out.printf("Enter end date (dd-MM-yyyy): ");
                            input = sc.nextLine();

                            campDetails.setEndDate(LocalDate.parse(input, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                            System.out.println("End Date: " + campDetails.getEndDate());

                            if (campDetails.getStartDate().isEqual(campDetails.getEndDate()) ||
                                campDetails.getEndDate().isAfter(campDetails.getEndDate())) {
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
                        campDetails.setClosingDate(LocalDate.parse(input, DateTimeFormatter.ofPattern("dd-MM-yyyy")));

                        //Closing date of registration must be before the start of the camp and after today
                        if(campDetails.getClosingDate().isBefore(campDetails.getStartDate()) &&
                           campDetails.getClosingDate().isAfter(LocalDate.now())){
                            System.out.println("Closing Date: " + campDetails.getClosingDate());
                            break;
                        }
                        else {
                            System.out.println("Closing date for registration must be after today and before the camp begins.");
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid date format!");
                    }
                }

                //Get facultyRestriction
                while (true) {
                    System.out.printf("Camp is (1) open to the whole of NTU OR (2) only to your facultyRestriction: ");
                    input = sc.nextLine(); //Use nextLine instead of nextInt to prevent integer mismatch exception
                    if ("1".equals(input) || "2".equals(input)) {
                        value = Integer.parseInt(input); // Convert to integer

                        if (value == 1 || value == 2) {
                            //If user chooses to open only to facultyRestriction, set facultyRestriction accordingly.
                            //Otherwise, maintain the default facultyRestriction value (Open to all)
                            if(value == 2){
                                campDetails.setFacultyRestriction(user.getFaculty());
                            }
                            System.out.println("Camp is available to " + campDetails.getFacultyRestriction().toString());
                            break;
                        }
                        else {
                            System.out.println("Invalid input. Please enter 1 or 2.");
                        }
                    }
                }

                //GET location
                System.out.printf("Camp location: ");
                campDetails.setLocation(sc.nextLine().strip());

                //GET number of slots for attendees
                while (true) {
                    try{
                        System.out.printf("No. of attendee slots available: ");
                        campDetails.setAttendeeSlots(sc.nextInt());
                        sc.nextLine(); //consume newline
                        if(campDetails.getAttendeeSlots() < 10 || campDetails.getAttendeeSlots() > 5000){
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
                        campDetails.setCommitteeSlots(sc.nextInt());
                        sc.nextLine(); //consume newline

                        if(campDetails.getCommitteeSlots() < 1)
                            System.out.println("Invalid number. Please create at least one slot. Try again.");

                        else if(campDetails.getCommitteeSlots() > 10)
                            System.out.println("Maximum 10 committee members allowed. Try again.");

                        else break;
                    }
                    catch (InputMismatchException e){
                        System.out.println("Invalid input. Please enter an integer.");
                    }
                }

                //GET description
                System.out.printf("Please provide a description of the camp: ");
                campDetails.setDescription(sc.nextLine());

                do {
                    System.out.println("Should the Camp be visible? [1: YES, 0: NO]");
                    visible = sc.nextInt();
                    if (visible == 1) {
                        campDetails.setVisibility(true);
                    } else if (visible == 0) {
                        campDetails.setVisibility(false);
                    }
                } while (visible != 1 && visible != 0);

                //Business logic of camp creation completed through abstracted Organiser interface
                organiser.createCamp(campDetails);

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
