package cams.dashboards;
import cams.users.CampDetails;
import cams.users.Organiser;
import cams.users.StaffOrganiserActions;
import cams.users.User;
import cams.util.Faculty;
import cams.util.UserInput;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
            case 0 -> { // Assign students
            	
            }
            case 1 -> { // Change camp name
            	/*
            	String update;
            	System.out.println("Camp's current name is: " + details.getCampName());
            	System.out.println("Set a new name for the Camp: ");
				update = UserInput.getStringInput();
				details.setCampName(update);
				
				organiser.editCamp(selectedCamp, details);
				System.out.println("New Camp name: " + details.getCampName());
				dashboard.loggedIn();
				*/
            }
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
			case 4 -> { // change number of attendee slots
				System.out.println("Camp currently has a total of " + details.getAttendeeSlots() + " attendee slots.");
				
				//GET number of slots for attendees
                System.out.println("Camp must have minimum of 10 and maximum of 5000 attendee slots.");
                option = UserInput.getIntegerInput(10, 5000, "No. of new attendee slots: ");
                details.setAttendeeSlots(option);
				
                organiser.editCamp(selectedCamp, details);
                System.out.println("Camp has been set to a total of " + details.getAttendeeSlots() + " attendee slots.");
                dashboard.loggedIn();
			}
			case 5 -> { // change number of camp committee slots
				System.out.println("Camp currently has a total of " + details.getCommitteeSlots() + " committee slots.");
				
				//GET number of slots for committee
				System.out.println("Camp must have minimum of 1 and maximum of 10 committee slots.");
                option = UserInput.getIntegerInput(1, 10, "No. of new committee member slots available (Max 10): ");
                details.setCommitteeSlots(option);
				
                organiser.editCamp(selectedCamp, details);
                System.out.println("Camp has been set to a total of " + details.getCommitteeSlots() + " committee slots.");
                dashboard.loggedIn();
			}
			case 6 -> { // change camp description
				String update;
				System.out.println("Camp's current description: " + details.getDescription());
				System.out.println("Set a new description for the Camp: ");
				update = UserInput.getStringInput();
				details.setDescription(update);
				
				organiser.editCamp(selectedCamp, details);
				System.out.println("New Camp description: " + details.getDescription());
				dashboard.loggedIn();
			}
			case 7 -> { // change closing date of registration
				
				Scanner sc = new Scanner(System.in);
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
				
				organiser.editCamp(selectedCamp, details);
				System.out.println("New closing date of registration: " + details.getClosingDate());
				dashboard.loggedIn();
			}
			case 8 -> { // change start and end dates
				
				Scanner sc = new Scanner(System.in);
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
				
				organiser.editCamp(selectedCamp, details);
				System.out.println("Current period of dates: " + details.getStartDate() + " - " + details.getEndDate());
				dashboard.loggedIn();
			}
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
