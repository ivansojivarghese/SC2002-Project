package cams.dashboards;
import cams.dashboards.enquiry_menus.Replier;
import cams.dashboards.suggestion_menus.Approver;
import cams.users.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class StaffMenuState implements DashboardState {
    private final Organiser organiser = new StaffOrganiserActions();
    //> FACULTY INFO.
    //> CHANGE PASSWORD
    //> LOGOUT (BACK TO LOG IN PAGE)
    @Override
    public void display(Dashboard dashboard) {
        //Use string to store user's menu selection because it is safer
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


        System.out.println("(4) View all Camps");
        System.out.println("(5) Edit your Camp(s)");
        System.out.println("(6) Create a new Camp");
        System.out.println("(7) Respond to camp suggestions");
        System.out.println("(8) Respond to camp enquiries");
        System.out.println("(9) View Participants/Committee Members for a camp");

        //GET user choice
        while(true) {
            try {
                System.out.print("SELECT AN ACTION: ");
                input = sc.nextLine().strip();
                option = Integer.parseInt(input);
                if(option >= 1 && option <= 9)
                    break;
                System.out.println("Invalid input, please try again.");
            }
            catch (Exception e){
                System.out.println("Error: " + e.getMessage());
            }
        }
        System.out.println();

        switch (option) {
            case 1 -> {
                String newPassword;
                System.out.print("Enter new password: ");
                newPassword = sc.next();
                user.setPassword(newPassword);
                System.out.println("Password successfully changed!");
            }
            case 2 -> dashboard.logout();
            case 3 -> user.displayMyCamps();
            case 4 ->
                    user.viewAllCamps();

            //TODO implement camp editing (case 5)
            case 5 -> // DELETE, AND TOGGLE camps
                    dashboard.setState(new OrganiserMenu());
            case 6 -> {
                //TODO abstract all of this away into the camp organiser interface? or just leave it here?
                int value;
                int visible;
                CampDetails campDetails = new CampDetails();

                //Use TRY-CATCH block with WHILE loop for getting valid input
                //GET Camp Name
                while (true) {
                    System.out.println("You are creating a new Camp.");
                    System.out.print("Name of Camp: ");
                    campDetails.setCampName(sc.nextLine());

                    if (organiser.isCampNameUnique(campDetails.getCampName())) {
                        break; // Break the loop if the name doesn't exist in the map
                    }
                    System.out.println("This name already exists. Please enter a different name.");
                }
                //Get start and end dates
                while (true) {
                    try {
                        //INPUT START DATE
                        System.out.print("Enter start date (dd-MM-yyyy): ");
                        input = sc.nextLine();
                        campDetails.setStartDate(LocalDate.parse(input, DateTimeFormatter.ofPattern("dd-MM-yyyy")));

                        if (!campDetails.getStartDate().isAfter(LocalDate.now())) {
                            System.out.println("Start date of camp must be after the date of creation.");
                            continue;
                        }

                        System.out.println("Start Date: " + campDetails.getStartDate());

                        while (true) {
                            //INPUT end date
                            System.out.print("Enter end date (dd-MM-yyyy): ");
                            input = sc.nextLine();

                            campDetails.setEndDate(LocalDate.parse(input, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                            System.out.println("End Date: " + campDetails.getEndDate());

                            if (campDetails.getEndDate().isBefore(campDetails.getStartDate())) {
                                System.out.println("End Date is before Start Date. Please re-enter End Date.");
                            } else {
                                break;
                            }
                        }
                        break;
                    } catch (Exception e) {
                        System.out.println("Invalid date format! Please try again.");
                    }
                }
                //Get closing date of registration
                while (true) {
                    try {
                        System.out.print("Enter registration closing date (dd-MM-yyyy): ");
                        input = sc.nextLine();
                        campDetails.setClosingDate(LocalDate.parse(input, DateTimeFormatter.ofPattern("dd-MM-yyyy")));

                        //Closing date of registration must be before the start of the camp and after today
                        if (campDetails.getClosingDate().isBefore(campDetails.getStartDate()) &&
                                campDetails.getClosingDate().isAfter(LocalDate.now())) {
                            System.out.println("Closing Date: " + campDetails.getClosingDate());
                            break;
                        } else {
                            System.out.println("Closing date for registration must be after today and before the camp begins.");
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid date format!");
                    }
                }

                //Get facultyRestriction
                while (true) {
                    System.out.print("Camp is (1) open to the whole of NTU OR (2) only to your faculty: ");
                    input = sc.nextLine(); //Use nextLine instead of nextInt to prevent integer mismatch exception
                    if ("1".equals(input) || "2".equals(input)) {
                        value = Integer.parseInt(input); // Convert to integer

                        if (value == 1 || value == 2) {
                            //If user chooses to open only to facultyRestriction, set facultyRestriction accordingly.
                            //Otherwise, maintain the default facultyRestriction value (Open to all)
                            if (value == 2) {
                                campDetails.setFacultyRestriction(user.getFaculty());
                            }
                            System.out.println("Camp is available to " + campDetails.getFacultyRestriction().toString());
                            break;
                        } else {
                            System.out.println("Invalid input. Please enter 1 or 2.");
                        }
                    }
                }

                //GET location
                System.out.print("Camp location: ");
                campDetails.setLocation(sc.nextLine().strip());

                //GET number of slots for attendees
                while (true) {
                    try {
                        System.out.print("No. of attendee slots available: ");
                        campDetails.setAttendeeSlots(sc.nextInt());
                        sc.nextLine(); //consume newline
                        if (campDetails.getAttendeeSlots() < 10 || campDetails.getAttendeeSlots() > 5000) {
                            System.out.println("Camp must have minimum of 10 and maximum of 5000 attendee slots.");
                            continue;
                        }
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter an integer.");
                    }
                }

                //GET number of slots for committee
                while (true) {
                    try {
                        System.out.print("No. of committee member slots available (Max 10): ");
                        campDetails.setCommitteeSlots(sc.nextInt());
                        sc.nextLine(); //consume newline

                        if (campDetails.getCommitteeSlots() < 1)
                            System.out.println("Invalid number. Please create at least one slot. Try again.");

                        else if (campDetails.getCommitteeSlots() > 10)
                            System.out.println("Maximum 10 committee members allowed. Try again.");

                        else break;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter an integer.");
                    }
                }

                //GET description
                System.out.print("Please provide a description of the camp: ");
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

                //Set staff in charge of camp to be current staff
                campDetails.setInCharge(user.getUserID());

                //Business logic of camp creation completed through abstracted Organiser interface
                organiser.createCamp(campDetails);
                System.out.println("Camp created successfully!");
            }
            case 7 -> dashboard.setState(new Approver());
            case 8 -> dashboard.setState(new Replier());
            case 9 -> {
            } //TODO implement view list of participants/committee members for a camp
        }
    }


}
