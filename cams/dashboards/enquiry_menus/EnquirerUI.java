package cams.dashboards.enquiry_menus;

import cams.dashboards.Dashboard;
import cams.dashboards.DashboardState;
import cams.dashboards.post_menus.PosterUI;
import cams.users.User;

import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class EnquirerUI extends EnquiryViewerUI implements PosterUI, DashboardState {
    public EnquirerUI() {
    }

    public void display(Dashboard dashboard){
        Scanner userInput = new Scanner(System.in);
        int choice;
        int postIndex;
        String content;
        User user = dashboard.getAuthenticatedUser();
        //Displays user's enquiries and returns 1 if user has enquiries, else returns 0
        boolean hasEnquiries = view(user) > 0;
        System.out.println();

        //Attempt to display enquiries
        //If display fails provide a different menu
        System.out.println("(1) Submit a new enquiry");
        if(hasEnquiries) {
            System.out.println("(2) Edit an enquiry");
            System.out.println("(3) Delete an enquiry");
        }
        System.out.println("(-1) Back to user dashboard");
        System.out.print("SELECT AN ACTION: ");
        try{
            choice = userInput.nextInt();
            userInput.nextLine();  // Consume the leftover newline

            switch (choice) {
                case -1 ->
                    //Set dashboard state to the loggedIn menu
                        dashboard.loggedIn();
                case 1 -> { //submit a new enquiry
                    System.out.println("Name of camp you are enquiring about: ");
                    String campName = userInput.nextLine();
                    System.out.println("Input query: ");
                    content = userInput.nextLine();
                    try {
                        if (submit(campName, user.getUserID(), content))
                            System.out.println("Success!");
                    } catch (NullPointerException e) {
                        System.out.println("Unsuccessful: " + e.getMessage());
                    }
                }
                case 2 -> { //Edit an enquiry
                    System.out.println("Enter index of enquiry to edit: ");
                    postIndex = userInput.nextInt();
                    System.out.println("Input modified query: ");
                    content = userInput.nextLine();
                    try {
                        if (edit(user, postIndex, content))
                            System.out.println("Success!");
                    } catch (NullPointerException e) {
                        System.out.println("Unsuccessful: " + e.getMessage());
                    }
                }
                case 3 -> { //Delete an enquiry
                    System.out.println("Enter index of enquiry to delete: ");
                    postIndex = userInput.nextInt();
                    try {
                        if (delete(user, postIndex))
                            System.out.println("Success!");
                    } catch (NullPointerException e) {
                        System.out.println("Unsuccessful: " + e.getMessage());
                    }
                }
                default -> System.out.println("Invalid integer input.");
            }
        }
        catch (InputMismatchException e){
            System.out.println("Invalid input. Please enter a number.");
            userInput.nextLine();  // Consume the invalid input
        }
        //At the end of the display method, the main APP will redisplay the set menu
    }

    public abstract boolean submit(String campName, String userID, String text);
    public abstract boolean edit(User user, int postIndex, String content);
    public abstract boolean delete(User user, int postIndex);
}
