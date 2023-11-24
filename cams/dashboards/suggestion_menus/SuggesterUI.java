package cams.dashboards.suggestion_menus;

import cams.dashboards.Dashboard;
import cams.dashboards.post_menus.PostViewerUI;
import cams.users.Student;
import cams.users.User;
import cams.util.UserInput;

import java.util.Scanner;

public abstract class SuggesterUI extends SuggestionViewerUI implements PostViewerUI {
    public void display(Dashboard dashboard){
        Scanner sc = new Scanner(System.in);
        User user = dashboard.getAuthenticatedUser();
        String content;
        String userInput;
        int option;
        int postIndex;

        int numSuggestions = view(user);

        if(numSuggestions > 0){
            System.out.println("(1) Submit a new suggestion");
            System.out.println("(2) Edit a suggestion");
            System.out.println("(3) Delete a suggestions");
            System.out.println("(0) Back");
            //GET user input for menu action
            option = UserInput.getIntegerInput(0, 3, "SELECT AN ACTION: ");
        }
        else {
            System.out.println("(1) Submit a new suggestion");
            System.out.println("(0) Back");
            //GET user input for menu action
            option = UserInput.getIntegerInput(0, 1, "SELECT AN ACTION: ");
        }

        //SELECT menu choice
        switch (option) {
            case 0 -> dashboard.loggedIn();
            case 1 -> {
                String campName = ((Student) user).getCommittee();
                System.out.println("Name of camp you are suggesting to: " + campName);
                System.out.println("Input content: ");
                content = sc.nextLine();
                try {
                    if (submit(campName, user.getUserID(), content))
                        System.out.println("Success!");
                } catch (Exception e) {
                    System.out.println("Unsuccessful: " + e.getMessage());
                }
            }
            case 2 -> { //submit a new enquiry
                option = UserInput.getIntegerInput(0, numSuggestions-1, "Enter index of suggestion to edit: ");
                postIndex = option;
                System.out.println("Input new content: ");
                content = sc.nextLine();
                try {
                    if (edit(user, postIndex, content))
                        System.out.println("Success!");
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
            case 3 -> {
                option = UserInput.getIntegerInput(0, numSuggestions-1, "Enter index of suggestion to delete: ");
                postIndex = option;
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
