package cams.dashboards;
import cams.users.CampDetails;
import cams.users.Organiser;
import cams.users.StaffOrganiserActions;
import cams.users.User;
import cams.util.Faculty;
import cams.util.UserInput;

import java.util.InputMismatchException;
import java.util.Scanner;

public class OrganiserMenu implements DashboardState{
    private final Organiser organiser;

    OrganiserMenu(){
        this.organiser = new StaffOrganiserActions();
    }
    @Override
    public void display(Dashboard dashboard) {

        User user = dashboard.getAuthenticatedUser();

        //Variables to store options
        String userInput;
        boolean bool;
        int option;

        //SELECT camp to edit
        String selectedCamp;

        //DISPLAYS list of user's camp and gets number of camps
        int numCamps = user.displayMyCamps();
        if (numCamps == 0) //if no camps to edit
        {
            System.out.println("Returning to main menu...");
            dashboard.loggedIn();
            return;
        }

        //GET Choice
        option = UserInput.getIntegerInput(0, numCamps-1, "Select a Camp to edit through its index number: ");
        selectedCamp = user.getMyCamps().get(option);

        try {
            //If camp exists
            if(organiser.isCampNameUnique(selectedCamp)){
                System.out.println("Camp selected not found, returning to main menu.");
                dashboard.loggedIn();
                return;
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
        }
        catch (NullPointerException e) {
            System.out.println("Camp selected not found.");
            return;
        }

        System.out.println();
        System.out.println("______________________________________________________________");
        System.out.println("                         EDITING CAMP                          ");
        System.out.println("SELECTED CAMP: " + selectedCamp);
        CampDetails details = organiser.getCampDetails(selectedCamp);

        System.out.println();
        System.out.println("(0) Assign a student to the camp");
        System.out.println("(1) Change Camp Name");
        System.out.println("(2) Toggle visibility");
        System.out.println("(3) Change faculty restrictions");
        System.out.println("(4) Change number of attendee slots");
        System.out.println("(5) Change number of camp committee slots");
        System.out.println("(6) Change camp description");
        System.out.println("(7) Change closing date of registration");
        System.out.println("(8) Change start and end date of camp");
        System.out.println("(9) Delete camp");

        option = UserInput.getIntegerInput(0, 9, "SELECT ACTION: ");

        switch (option) {
            case 0 -> {
            }
            //TODO Assign students
            case 1 -> {
            }
            //TODO Change camp name
            case 2 -> { //Toggle visibility
                System.out.println("Camp is visible: " + String.valueOf(details.isVisible()).toUpperCase());
                System.out.println("Only visible camps can be viewed by potential participants.");
                bool = UserInput.getBoolInput("Change visible to " + String.valueOf(!details.isVisible()).toUpperCase() + "? (Y / N)");
                if (bool) {
                    System.out.println("Proceeding to change visibility to " + String.valueOf(!details.isVisible()).toUpperCase() + ".");
                    details.setVisibility(!details.isVisible());
                    organiser.editCamp(selectedCamp, details);
                }
                dashboard.loggedIn();
            }
            case 3 -> { // toggle faculty restriction
                System.out.println("Camp was previously open to " + details.getFacultyRestriction() + ".");
                if (details.getFacultyRestriction() != Faculty.ALL) {
                    details.setFacultyRestriction(Faculty.ALL);
                } else {
                    details.setFacultyRestriction(user.getFaculty());
                }
                organiser.editCamp(selectedCamp, details);
                System.out.println("Camp is now open to " + details.getFacultyRestriction() + ".");
                dashboard.loggedIn();
            }

            //TODO other functionalities

            case 9 -> { // deletion
                //TODO Add error prevention if needed
                if (organiser.getNumAttendees(selectedCamp) > 0) {
                    System.out.println("Camps with participants cannot be deleted.");
                    return;
                }

                bool = UserInput.getBoolInput("Are you sure you want to delete " + selectedCamp + " ? (Y / N)");
                if (bool) {
                    organiser.deleteCamp(selectedCamp);
                    System.out.println("This Camp has been deleted.");
                } else {
                    System.out.println("Abandoning delete action, returning to main menu...");
                }
                dashboard.loggedIn();
            }
            default -> {
                System.out.println("Invalid input.");
                dashboard.loggedIn();
            }
        }
    }
}
