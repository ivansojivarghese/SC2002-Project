package cams.dashboards;
import cams.users.CampDetails;
import cams.users.Organiser;
import cams.users.StaffOrganiserActions;
import cams.users.User;
import cams.util.Faculty;
import cams.util.InputScanner;
import cams.util.UserInput;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class OrganiserMenu implements DashboardState {
    private final Organiser organiser;

    OrganiserMenu() {
        this.organiser = new StaffOrganiserActions();
    }

    @Override
    public void display(Dashboard dashboard) {
        User user = dashboard.getAuthenticatedUser();

        //DISPLAYS list of user's camp and gets number of camps
        int numCamps = user.displayMyCamps();
        if (numCamps == 0) //if no camps to edit
        {
            System.out.println("Returning to main menu...");
            dashboard.loggedIn();
            return;
        }

        //Get user to select a camp
        int option = UserInput.getIntegerInput(0, numCamps - 1, "Select a Camp to edit through its index number: ");
        String selectedCamp = user.getMyCamps().get(option);
        //If camp does not exist
        if (organiser.isCampNameUnique(selectedCamp)) {
            System.out.println("Camp selected not found, returning to main menu.");
            dashboard.loggedIn();
            return;
        }

        // Initialise and display hash map of menu options storing methods
        System.out.println();
        Map<Integer, MenuAction> options = initializeActions(dashboard, selectedCamp);
        System.out.println("______________________________________________________________");
        System.out.println("                         EDITING CAMP                          ");
        System.out.println("SELECTED CAMP: " + selectedCamp);
        describeOptions(options);

        // Get user's choice
        option = UserInput.getIntegerInput(0, options.size() - 1, "SELECT AN ACTION: ");

        // Executes the lambda expression associated with the user's option
        options.getOrDefault(option, () -> System.out.println("Invalid option selected")).execute();
    }

    /**
     * Initialises a hashmap of methods corresponding to each choice in the user menu
     *
     * @param dashboard The dashboard object
     * @return Hash map of methods keyed to menu choices
     */
    protected Map<Integer, MenuAction> initializeActions(Dashboard dashboard, String selectedCamp) {
        Map<Integer, MenuAction> actions = new HashMap<>();
        CampDetails details = organiser.getCampDetails(selectedCamp);
        actions.put(0, dashboard::loggedIn);
        actions.put(1, () -> assignStudent(dashboard, selectedCamp));
        actions.put(2, () -> changeName(dashboard, details));
        actions.put(3, () -> toggleVisibility(dashboard, details));
        actions.put(4, () -> changeFaculty(dashboard, details));
        actions.put(5, () -> changeAttendeeSlots(dashboard, details));
        actions.put(6, () -> changeCommitteeSlots(dashboard, details));
        actions.put(7, () -> changeDescription(dashboard, details));
        actions.put(8, () -> changeClosingDate(dashboard, details));
        actions.put(9, () -> changeCampDates(dashboard, details));
        actions.put(10, () -> deleteCamp(dashboard, selectedCamp));
        return actions;
    }

    /**
     * Displays the corresponding description of each menu choice
     * @param actions Hash map of methods keyed to menu choices
     */
    protected void describeOptions(Map<Integer, MenuAction> actions) {
        for (Integer key : actions.keySet()) {
            switch (key) {
                case 0 -> System.out.println("(0) Back to main menu");
                case 1 -> System.out.println("(1) Assign a student to the camp");
                case 2 -> System.out.println("(2) Change Camp Name");
                case 3 -> System.out.println("(3) Toggle visibility");
                case 4 -> System.out.println("(4) Change faculty restrictions");
                case 5 -> System.out.println("(5) Change number of attendee slots");
                case 6 -> System.out.println("(6) Change number of camp committee slots");
                case 7 -> System.out.println("(7) Change camp description");
                case 8 -> System.out.println("(8) Change closing date of registration");
                case 9 -> System.out.println("(9) Change start and end date of camp");
                case 10 -> System.out.println("(10) Delete camp");
            }
        }
    }

    protected void assignStudent(Dashboard dashboard, String selectedCamp) {
        System.out.println("Enter the userID of a student to be assigned: ");
        String student = UserInput.getStringInput();
        organiser.assignCamp(student, selectedCamp);
        dashboard.loggedIn();
    }

    protected void changeName(Dashboard dashboard, CampDetails details) {
        String update;
        System.out.println("Camp's current name is: " + details.getCampName());
        System.out.println("Set a new name for the Camp: ");
        update = UserInput.getStringInput();
        details.setCampName(update);

        organiser.editCamp(details.getCampName(), details);
        System.out.println("New Camp name: " + details.getCampName());
        dashboard.loggedIn();
    }

    protected void toggleVisibility(Dashboard dashboard, CampDetails details) {
        boolean bool;
        System.out.println("Camp is visible: " + String.valueOf(details.isVisible()).toUpperCase());
        System.out.println("Only visible camps can be viewed by potential participants.");
        bool = UserInput.getBoolInput("Change visible to " + String.valueOf(!details.isVisible()).toUpperCase() + "? (Y / N)");
        if (bool) {
            System.out.println("Proceeding to change visibility to " + String.valueOf(!details.isVisible()).toUpperCase() + ".");
            details.setVisibility(!details.isVisible());
            organiser.editCamp(details.getCampName(), details);
        }
        dashboard.loggedIn();
    }

    protected void changeFaculty(Dashboard dashboard, CampDetails details) {
        System.out.println("Camp was previously open to " + details.getFacultyRestriction() + ".");
        if (details.getFacultyRestriction() != Faculty.ALL) {
            details.setFacultyRestriction(Faculty.ALL);
        } else {
            details.setFacultyRestriction(dashboard.getAuthenticatedUser().getFaculty());
        }
        organiser.editCamp(details.getCampName(), details);
        System.out.println("Camp is now open to " + details.getFacultyRestriction() + ".");
        dashboard.loggedIn();
    }

    protected void changeAttendeeSlots(Dashboard dashboard, CampDetails details) {
        System.out.println("Camp currently has a total of " + details.getAttendeeSlots() + " attendee slots.");

        //GET number of slots for attendees
        System.out.println("Camp must have minimum of 10 and maximum of 5000 attendee slots.");
        int option = UserInput.getIntegerInput(10, 5000, "No. of new attendee slots: ");
        details.setAttendeeSlots(option);

        organiser.editCamp(details.getCampName(), details);
        System.out.println("Camp has been set to a total of " + details.getAttendeeSlots() + " attendee slots.");
        dashboard.loggedIn();
    }

    protected void changeCommitteeSlots(Dashboard dashboard, CampDetails details) {
        System.out.println("Camp currently has a total of " + details.getCommitteeSlots() + " committee slots.");

        //GET number of slots for committee
        System.out.println("Camp must have minimum of 1 and maximum of 10 committee slots.");
        int option = UserInput.getIntegerInput(1, 10, "No. of new committee member slots available (Max 10): ");
        details.setCommitteeSlots(option);

        organiser.editCamp(details.getCampName(), details);
        System.out.println("Camp has been set to a total of " + details.getCommitteeSlots() + " committee slots.");
        dashboard.loggedIn();
    }

    protected void changeDescription(Dashboard dashboard, CampDetails details) {
        String update;
        System.out.println("Camp's current description: " + details.getDescription());
        System.out.println("Set a new description for the Camp: ");
        update = UserInput.getStringInput();
        details.setDescription(update);

        organiser.editCamp(details.getCampName(), details);
        System.out.println("New Camp description: " + details.getDescription());
        dashboard.loggedIn();
    }

    protected void changeClosingDate(Dashboard dashboard, CampDetails details) {
        Scanner sc = InputScanner.getInstance();
        String input;

        System.out.println("Current closing date of registration: " + details.getClosingDate());
        // System.out.println("Set a new closing date of registration (dd-MM-yyyy): ");

        while (true) {
            try {
                System.out.print("Set a new closing date of registration (dd-MM-yyyy): ");
                input = sc.nextLine();
                details.setClosingDate(LocalDate.parse(input, DateTimeFormatter.ofPattern("dd-MM-yyyy")));

                //Closing date of registration must be before the start of the camp and after today
                if (details.getClosingDate().isBefore(details.getStartDate()) &&
                        details.getClosingDate().isAfter(LocalDate.now())) {
                    System.out.println("Closing Date: " + details.getClosingDate());
                    break;
                } else {
                    System.out.println("Closing date for registration must be after today and before the camp begins.");
                }
            } catch (Exception e) {
                System.out.println("Invalid date format!");
            }
        }

        organiser.editCamp(details.getCampName(), details);
        System.out.println("New closing date of registration: " + details.getClosingDate());
        dashboard.loggedIn();
    }

    protected void changeCampDates(Dashboard dashboard, CampDetails details) {
        Scanner sc = InputScanner.getInstance();
        String input;

        System.out.println("Current period of dates: " + details.getStartDate() + " - " + details.getEndDate());

        while (true) {
            try {
                //INPUT START DATE
                System.out.print("Enter start date (dd-MM-yyyy): ");
                input = sc.nextLine();
                details.setStartDate(LocalDate.parse(input, DateTimeFormatter.ofPattern("dd-MM-yyyy")));

                if (!details.getStartDate().isAfter(LocalDate.now())) {
                    System.out.println("Start date of camp must be after the date of creation.");
                    continue;
                }

                System.out.println("Start Date: " + details.getStartDate());

                while (true) {
                    //INPUT end date
                    System.out.print("Enter end date (dd-MM-yyyy): ");
                    input = sc.nextLine();

                    details.setEndDate(LocalDate.parse(input, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                    System.out.println("End Date: " + details.getEndDate());

                    if (details.getEndDate().isBefore(details.getStartDate())) {
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

        organiser.editCamp(details.getCampName(), details);
        System.out.println("Current period of dates: " + details.getStartDate() + " - " + details.getEndDate());
        dashboard.loggedIn();
    }

    protected void deleteCamp(Dashboard dashboard, String selectedCamp) {
        if (organiser.getNumAttendees(selectedCamp) > 0) {
            System.out.println("Camps with participants cannot be deleted.");
            return;
        }

        boolean bool = UserInput.getBoolInput("Are you sure you want to delete " + selectedCamp + " ? (Y / N)");
        if (bool) {
            organiser.deleteCamp(selectedCamp);
            System.out.println("This Camp has been deleted.");
        } else {
            System.out.println("Abandoning delete action, returning to main menu...");
        }
        dashboard.loggedIn();
    }
}