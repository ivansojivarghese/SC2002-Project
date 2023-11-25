package cams.dashboards;

import cams.Camp;
import cams.dashboards.enquiry_menus.Replier;
import cams.dashboards.suggestion_menus.Approver;
import cams.database.UnifiedCampRepository;
import cams.reports.ParticipationReport;
import cams.reports.PerformanceReport;
import cams.reports.ReportGenerator;
import cams.users.*;
import cams.util.InputScanner;
import cams.util.UserInput;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class StaffMenuState extends UserMenuState implements DashboardState {
    private final Organiser organiser = new StaffOrganiserActions();
    /**
     * Displays the student menu to the user and handles user input for various operations.
     * @param dashboard The dashboard through which the user interacts.
     */
    @Override
    public void display(Dashboard dashboard) {
        User user = dashboard.getAuthenticatedUser();

        // Display User information and the main menu
        this.userInfo(user);
        // Initialise and display hash map of menu options storing methods
        Map<Integer, MenuAction> options = initializeActions(dashboard);
        System.out.println("______________________________________________________________");
        System.out.println("                             MENU                              ");
        describeOptions(options);

        // Get user's choice
        int option = UserInput.getIntegerInput(0, options.size() - 1, "SELECT AN ACTION: ");

        // Executes the lambda expression associated with the user's option
        options.getOrDefault(option, () -> System.out.println("Invalid option selected")).execute();
    }
    /**
     * Initialises a hashmap of methods corresponding to each choice in the user menu
     * @param dashboard The dashboard object
     * @return Hash map of methods keyed to menu choices
     */
    protected Map<Integer, MenuAction> initializeActions(Dashboard dashboard) {
        Map<Integer, MenuAction> actions = new HashMap<>();
        User user = dashboard.getAuthenticatedUser();

        actions.put(0, dashboard::logout);
        actions.put(1, () -> changePassword(user));
        actions.put(2, user::displayMyCamps);
        actions.put(3, () -> viewAllCamps(user));
        actions.put(4, () -> editCamp(dashboard));
        actions.put(5, () -> createCamp(dashboard));
        actions.put(6, () -> respondSuggestions(dashboard));
        actions.put(7, () -> respondEnquiries(dashboard));
        actions.put(8, () -> seeAttendees(dashboard));
        actions.put(9, () -> generateReport(dashboard, new ParticipationReport())); // Add the option for generating the participation report
        actions.put(10, () -> generatePerformanceReport(dashboard, new PerformanceReport())); // Add the option for generating the performance report
        return actions;
    }

    /**
     * Displays the corresponding description of each menu choice
     * @param actions Hash map of methods keyed to menu choices
     */
    protected void describeOptions(Map<Integer, MenuAction> actions) {
        for (Integer key : actions.keySet()) {
            switch (key) {
                case 0 -> System.out.println("(0) Logout");
                case 1 -> System.out.println("(1) Change your password");
                case 2 -> System.out.println("(2) View my Camps");
                case 3 -> System.out.println("(3) View all Camps");
                case 4 -> System.out.println("(4) Edit your Camp(s)");
                case 5 -> System.out.println("(5) Create a new Camp");
                case 6 -> System.out.println("(6) Respond to camp suggestions");
                case 7 -> System.out.println("(7) Respond to camp enquiries");
                case 8 -> System.out.println("(8) View Participants/Committee Members for a camp");
                case 9 -> System.out.println("(9) Generate Participation Report");
                case 10 -> System.out.println("(10) Generate Performance Report");
            }
        }
    }
    protected void editCamp(Dashboard dashboard){
        // DELETE, AND TOGGLE camps
        dashboard.setState(new OrganiserMenu());
    }
    protected void createCamp(Dashboard dashboard){
        CampDetails campDetails = new CampDetails();
        Scanner sc = InputScanner.getInstance();
        String input;
        boolean bool;
        int option;
        User user = dashboard.getAuthenticatedUser();
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
        bool = UserInput.getBoolInput("Camp is (0) open to the whole of NTU OR (1) only to your faculty: "); //Use nextLine instead of nextInt to prevent integer mismatch exception
        if (bool) {
            //If user chooses to open only to facultyRestriction, set facultyRestriction accordingly.
            //Otherwise, maintain the default facultyRestriction value (Open to all)
            campDetails.setFacultyRestriction(user.getFaculty());
        }
        System.out.println("Camp is available to " + campDetails.getFacultyRestriction().toString());

        //GET location
        System.out.print("Camp location: ");
        campDetails.setLocation(sc.nextLine().strip());

        //GET number of slots for attendees
        System.out.println("Camp must have minimum of 10 and maximum of 5000 attendee slots.");
        option = UserInput.getIntegerInput(10, 5000, "No. of attendee slots available: ");
        campDetails.setAttendeeSlots(option);

        //GET number of slots for committee
        System.out.println("Camp must have minimum of 1 and maximum of 10 committee slots.");
        option = UserInput.getIntegerInput(1, 10, "No. of committee member slots available (Max 10): ");
        campDetails.setCommitteeSlots(option);

        //GET description
        System.out.print("Please provide a description of the camp: ");
        campDetails.setDescription(sc.nextLine());

        //SET visibility
        bool = UserInput.getBoolInput("Should the Camp be visible? [1: YES, 0: NO]");
        campDetails.setVisibility(bool);

        //Set staff in charge of camp to be current staff
        campDetails.setInCharge(user.getUserID());

        //Business logic of camp creation completed through abstracted Organiser interface
        organiser.createCamp(campDetails);
        System.out.println("Camp created successfully!");
    }
    protected void respondSuggestions(Dashboard dashboard){
        dashboard.setState(new Approver());
    }
    protected void respondEnquiries(Dashboard dashboard){
        dashboard.setState(new Replier());
    }
    protected void seeAttendees(Dashboard dashboard){}
    protected void viewAllCamps(User user){
        user.viewAllCamps();
    }

    protected void userInfo(User user){
        // Code to display options
        System.out.println("                          DASHBOARD                           ");
        System.out.println("______________________________________________________________");
        System.out.println("STAFF Name: " + user.getName());
        System.out.println("Username: " + user.getUserID());
        System.out.println("Faculty: " + user.getFaculty());
    }

    private void generateReport(Dashboard dashboard, ReportGenerator reportGenerator) {
        User user = dashboard.getAuthenticatedUser();

        List<String> staffCamps = ((Staff) user).getMyCamps();

        if (staffCamps.isEmpty()) {
            System.out.println("You have not organized any camps. Unable to generate the report.");
        } else {
            // Display the list of camps organized by the staff
            System.out.println("Select a camp to generate the report:");
            for (int i = 0; i < staffCamps.size(); i++) {
                System.out.println((i + 1) + ". " + staffCamps.get(i));
            }

            int selectedCampIndex = -1;

            // Get user input for the selected camp, handle non-integer input
            while (selectedCampIndex == -1) {
                try {
                    selectedCampIndex = UserInput.getIntegerInput(1, staffCamps.size(), "Enter the number of the camp: ") - 1;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                }
            }

            String selectedCampName = staffCamps.get(selectedCampIndex);
            Camp selectedCamp = UnifiedCampRepository.getInstance().retrieveCamp(selectedCampName);

            reportGenerator.generateReport(selectedCamp);

        }
    }

    private void generatePerformanceReport(Dashboard dashboard, ReportGenerator reportGenerator) {
        User user = dashboard.getAuthenticatedUser();

        List<String> staffCamps = ((Staff) user).getMyCamps();

        if (staffCamps.isEmpty()) {
            System.out.println("You have not organized any camps. Unable to generate the report.");
        } else {
            // Display the list of camps organized by the staff
            System.out.println("Select a camp to generate the report:");
            for (int i = 0; i < staffCamps.size(); i++) {
                System.out.println((i + 1) + ". " + staffCamps.get(i));
            }

            int selectedCampIndex = -1;

            // Get user input for the selected camp, handle non-integer input
            while (selectedCampIndex == -1) {
                try {
                    selectedCampIndex = UserInput.getIntegerInput(1, staffCamps.size(), "Enter the number of the camp: ") - 1;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                }
            }

            String selectedCampName = staffCamps.get(selectedCampIndex);
            Camp selectedCamp = UnifiedCampRepository.getInstance().retrieveCamp(selectedCampName);

            reportGenerator.generateReport(selectedCamp);
        }
    }



}
