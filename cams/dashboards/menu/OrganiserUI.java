package cams.dashboards.menu;
import cams.camp.CampDetails;
import cams.camp.CampDisplayService;
import cams.camp.CampService;
import cams.dashboards.organiser_controllers.OrganiserController;
import cams.dashboards.organiser_controllers.StaffOrganiserController;
import cams.users.User;
import cams.util.Faculty;
import cams.util.InputScanner;
import cams.util.UserInput;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Class containing methods for various camp management functionalities in the dashboard.
 * These methods include actions like assigning students to camps, changing camp details,
 * and deleting camps.
 */
public class OrganiserUI implements DashboardState {
    private final OrganiserController organiserController;

    OrganiserUI() {
        this.organiserController = new StaffOrganiserController();
    }

    @Override
    public void display(Dashboard dashboard) {
        User user = dashboard.getAuthenticatedUser();

        //DISPLAYS list of user's camp and gets number of camps
        int numCamps = this.displayMyCamps(user);
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
        if (organiserController.isCampNameUnique(selectedCamp)) {
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
        CampDetails details = organiserController.getCampDetails(selectedCamp);
        actions.put(0, dashboard::loggedIn);
        actions.put(1, () -> assignStudent(dashboard, selectedCamp));
        actions.put(2, () -> changeCampName(dashboard, details));
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

    /**
     * Displays the camps created by the staff member using the CampDisplayService
     *
     * @param user User whose camps are to be displayed
     * @return The number of camps created by the staff member.
     */
    protected int displayMyCamps(User user) {
        CampService campDisplay = new CampDisplayService();
        int index = 0;
        System.out.println("My Created Camps: ");
        try {
            index = campDisplay.displayMyCamps(user);
        } catch (NullPointerException e){
            System.out.println("No camps created.");
        }
        if(index == 0)
            System.out.println("No camps created.");
        return index;
    }
    /**
     * UI interaction that assigns a student to a selected camp as a participationController.
     * This method prompts for the userID of a student and assigns them to the specified camp.
     * @param dashboard     The dashboard context.
     * @param selectedCamp  The name of the camp to assign the student to.
     */
    protected void assignStudent(Dashboard dashboard, String selectedCamp) {
        System.out.println("Enter the userID of a student to be assigned: ");
        String student = UserInput.getStringInput();
        organiserController.assignCamp(student, selectedCamp);
        dashboard.loggedIn();
    }

    /**
     * Handles user interaction process to change the name of a camp.
     * <p>
     * Prompts the user to enter a new name for the camp and updates it accordingly.
     *
     * @param dashboard The dashboard context.
     * @param details   The {@link CampDetails} object containing information about the camp.
     */
    protected void changeCampName(Dashboard dashboard, CampDetails details) {
        String updatedName;
        System.out.println("Camp's current name is: " + details.getCampName());
        System.out.println("Set a new name for the Camp: ");
        updatedName = UserInput.getStringInput();
        details.setCampName(updatedName);

        organiserController.editCamp(details.getCampName(), details);
        System.out.println("New Camp name: " + details.getCampName());
        dashboard.loggedIn();
    }

    /**
     * Handles user interaction process to toggle the visibility of a camp.
     * <p>
     * Allows changing the camp's visibility status, making it either visible or invisible to potential participants.
     *
     * @param dashboard The dashboard context.
     * @param details   The {@link CampDetails} object containing information about the camp.
     */
    protected void toggleVisibility(Dashboard dashboard, CampDetails details) {
        boolean bool;
        System.out.println("Camp is visible: " + String.valueOf(details.isVisible()).toUpperCase());
        System.out.println("Only visible camps can be viewed by potential participants.");
        bool = UserInput.getBoolInput("Change visible to " + String.valueOf(!details.isVisible()).toUpperCase() + "? (Y / N)");
        if (bool) {
            System.out.println("Proceeding to change visibility to " + String.valueOf(!details.isVisible()).toUpperCase() + ".");
            details.setVisibility(!details.isVisible());
            organiserController.editCamp(details.getCampName(), details);
        }
        dashboard.loggedIn();
    }

    /**
     * Handles user interaction to toggle the visibility of the camp
     * @param dashboard The dashboard context.
     * @param details The {@link CampDetails} object containing information about the camp.
     */
    protected void changeFaculty(Dashboard dashboard, CampDetails details) {
        System.out.println("Camp was previously open to " + details.getFacultyRestriction() + ".");
        if (details.getFacultyRestriction() != Faculty.ALL) {
            details.setFacultyRestriction(Faculty.ALL);
        } else {
            details.setFacultyRestriction(dashboard.getAuthenticatedUser().getFaculty());
        }
        organiserController.editCamp(details.getCampName(), details);
        System.out.println("Camp is now open to " + details.getFacultyRestriction() + ".");
        dashboard.loggedIn();
    }

    /**
     * Handles user interaction process to change the number of slots for camp attendees
     * @param dashboard The dashboard context.
     * @param details The {@link CampDetails} object containing information about the camp.
     */
    protected void changeAttendeeSlots(Dashboard dashboard, CampDetails details) {
        System.out.println("Camp currently has a total of " + details.getAttendeeSlots() + " attendee slots.");

        //GET number of slots for attendees
        System.out.println("Camp must have minimum of 10 and maximum of 5000 attendee slots.");
        int option = UserInput.getIntegerInput(10, 5000, "No. of new attendee slots: ");
        details.setAttendeeSlots(option);

        organiserController.editCamp(details.getCampName(), details);
        System.out.println("Camp has been set to a total of " + details.getAttendeeSlots() + " attendee slots.");
        dashboard.loggedIn();
    }

    /**
     * Changes the number of committee slots available for a camp.
     * <p>
     * Prompts for the new number of committee slots and updates the camp details accordingly.
     *
     * @param dashboard The dashboard context.
     * @param details   The {@link CampDetails} object containing information about the camp.
     */
    protected void changeCommitteeSlots(Dashboard dashboard, CampDetails details) {
        System.out.println("Camp currently has a total of " + details.getCommitteeSlots() + " committee slots.");

        //GET number of slots for committee
        System.out.println("Camp must have minimum of 1 and maximum of 10 committee slots.");
        int option = UserInput.getIntegerInput(1, 10, "No. of new committee member slots available (Max 10): ");
        details.setCommitteeSlots(option);

        organiserController.editCamp(details.getCampName(), details);
        System.out.println("Camp has been set to a total of " + details.getCommitteeSlots() + " committee slots.");
        dashboard.loggedIn();
    }

    /**
     * Changes the description of a camp.
     * <p>
     * Prompts for a new description and updates the camp details accordingly.
     *
     * @param dashboard The dashboard context.
     * @param details   The {@link CampDetails} object containing information about the camp.
     */
    protected void changeDescription(Dashboard dashboard, CampDetails details) {
        String update;
        System.out.println("Camp's current description: " + details.getDescription());
        System.out.println("Set a new description for the Camp: ");
        update = UserInput.getStringInput();
        details.setDescription(update);

        organiserController.editCamp(details.getCampName(), details);
        System.out.println("New Camp description: " + details.getDescription());
        dashboard.loggedIn();
    }
    /**
     * Changes the closing date of camp registration.
     * <p>
     * Prompts the user to set a new closing date for camp registration, ensuring it is before the camp start date
     * and after the current date.
     *
     * @param dashboard The dashboard context.
     * @param details   The {@link CampDetails} object containing information about the camp.
     */
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

        organiserController.editCamp(details.getCampName(), details);
        System.out.println("New closing date of registration: " + details.getClosingDate());
        dashboard.loggedIn();
    }

    /**
     * Responsible for handling user interaction and initiating the change in camp dates.
     * @param dashboard The dashboard context.
     * @param details The {@link CampDetails} object containing information about the camp.
     */
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

        organiserController.editCamp(details.getCampName(), details);
        System.out.println("Current period of dates: " + details.getStartDate() + " - " + details.getEndDate());
        dashboard.loggedIn();
    }

    /**
     * Responsible for user interaction in the process of deleting a camp.
     * <p>
     * Prompts for confirmation before deleting a camp. Camps with existing participants cannot be deleted.
     *
     * @param dashboard    The dashboard context.
     * @param selectedCamp The name of the camp to be deleted.
     */
    protected void deleteCamp(Dashboard dashboard, String selectedCamp) {
        if (organiserController.getNumAttendees(selectedCamp) > 0) {
            System.out.println("Camps with participants cannot be deleted.");
            return;
        }

        boolean bool = UserInput.getBoolInput("Are you sure you want to delete " + selectedCamp + " ? (Y / N)");
        if (bool) {
            organiserController.deleteCamp(selectedCamp);
            System.out.println("This Camp has been deleted.");
        } else {
            System.out.println("Abandoning delete action, returning to main menu...");
        }
        dashboard.loggedIn();
    }
}