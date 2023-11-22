package cams.dashboards;
import cams.Camp;
import cams.users.User;
import cams.database.UnifiedCampRepository;
import cams.util.Faculty;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class OrganiserMenu implements DashboardState{
    @Override
    public void display(Dashboard dashboard) {

        Scanner sc = new Scanner(System.in);
        User user = dashboard.getAuthenticatedUser();

        String userInput = "";
        int index = -1, option;

        //SELECT camp to edit
        Camp selectedCamp = null;

        //DISPLAYS list of user's camp and gets number of camps
        int numberOfCamps = user.displayMyCamps();

        if (numberOfCamps == 0) //if no camps to edit
        {
            dashboard.loggedIn();
            return;
        }

        UnifiedCampRepository repo = UnifiedCampRepository.getInstance();

        do {
            try {
                System.out.printf("Select a Camp to edit through its index number: ");
                index = sc.nextInt();

                selectedCamp = repo.retrieveCamp(user.getMyCamps().get(index));
            } catch (InputMismatchException e) {
                System.out.println("Invalid input.");
            }
            catch (NullPointerException e) {
                System.out.println("Camp selected not found.");
                return;
            }
        } while (index < 0 || index >= numberOfCamps);

        System.out.println("SELECTED CAMP: " + selectedCamp.getCampName());

        System.out.println("Camp Editing Menu");
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

        while(true) {
            try {
                System.out.printf("SELECT ACTION: ");
                option = sc.nextInt();
                if(option >= 0 && option <= 9)
                    break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, please try again.");
            }
        }

        switch (option) {
            case 0:
                break;
            case 1:
                break;
            case 2: //Toggle visibility
                do {
                    System.out.println("Camp visibility is: " + selectedCamp.getVisible());
                    System.out.println("Change visbility to " + !selectedCamp.getVisible() + "? (Y / N)");
                    userInput = sc.next();
                } while(!userInput.equalsIgnoreCase("y") || !userInput.equalsIgnoreCase("n"));

                System.out.println("Proceeding to change visibility to " + !selectedCamp.getVisible() + ".");
                selectedCamp.setVisible(!selectedCamp.getVisible());
                break;
            case 3: // toggle faculty restriction
                System.out.println("Camp was previously open to " + selectedCamp.getFacultyRestriction() + ".");

                if (selectedCamp.getFacultyRestriction() != Faculty.ALL) {
                    selectedCamp.setFacultyRestriction(Faculty.ALL);
                } else {
                    selectedCamp.setFacultyRestriction(user.getFaculty());
                }

                System.out.println("Camp is now open to " + selectedCamp.getFacultyRestriction() + ".");
                break;

            case 9: // deletion
                //TODO Add error prevention if needed
                if(selectedCamp.getNumAttendees() > 0)
                {
                    System.out.println("Camps with participants cannot be deleted.");
                }
                user.removeCamp(selectedCamp.getCampName());
                repo.deleteCamp(selectedCamp.getCampName());

                System.out.println("This Camp has been deleted.");
                break;
            default:
                System.out.println("Invalid input.");
                break;
        }
    }
}
