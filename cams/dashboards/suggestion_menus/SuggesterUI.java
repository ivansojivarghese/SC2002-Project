package cams.dashboards.suggestion_menus;

import cams.dashboards.Dashboard;
import cams.dashboards.DashboardState;
import cams.dashboards.post_menus.PostViewerUI;
import cams.users.Student;
import cams.users.User;

import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class SuggesterUI extends SuggestionViewerUI implements PostViewerUI {
    public void display(Dashboard dashboard){
        Scanner userInput = new Scanner(System.in);
        User user = dashboard.getAuthenticatedUser();
        String content;
        int choice = 0;
        int postIndex;

        boolean hasSuggestion = view(user) > 0;

        System.out.println("(1) Submit a new suggestion");
        if(hasSuggestion){
            System.out.println("(2) Edit a suggestion");
            System.out.println("(3) Delete a suggestions");
        }
        System.out.println("(-1) Back");

        //GET user input for menu action
        System.out.print("SELECT AN ACTION: ");
        try {
            choice = userInput.nextInt();
        }
        catch (InputMismatchException e){
            System.out.println("Invalid input. Please enter a number.");
            userInput.nextLine();  // Consume the invalid input
        }

        //SELECT menu choice
        switch (choice) {
            case -1 -> dashboard.loggedIn();
            case 1 -> {
                String campName = ((Student) user).getCommittee();
                System.out.println("Name of camp you are suggesting to: " + campName);
                System.out.println("Input content: ");
                content = userInput.nextLine();
                try {
                    if (submit(campName, user.getUserID(), content))
                        System.out.println("Success!");
                } catch (Exception e) {
                    System.out.println("Unsuccessful: " + e.getMessage());
                }
            }
            case 2 -> { //submit a new enquiry
                System.out.println("Enter index of suggestion to edit: ");
                choice = userInput.nextInt();
                postIndex = choice;
                System.out.println("Input new content: ");
                content = userInput.nextLine();
                try {
                    if (edit(user, postIndex, content))
                        System.out.println("Success!");
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
            case 3 -> {
                System.out.println("Enter index of suggestion to delete: ");
                choice = userInput.nextInt();
                postIndex = choice;
                try {
                    if (delete(user, postIndex))
                        System.out.println("Success!");
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
            default -> System.out.println("Invalid input!");
        }

    }
    public abstract boolean submit(String camp, String userID, String text);
    public abstract boolean edit(User user, int postIndex, String content);
    public abstract boolean delete(User user, int postIndex);
}
