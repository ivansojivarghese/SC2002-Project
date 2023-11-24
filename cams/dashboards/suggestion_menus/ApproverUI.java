package cams.dashboards.suggestion_menus;

import cams.dashboards.Dashboard;
import cams.dashboards.post_menus.PostApproverUI;
import cams.users.User;
import cams.util.UserInput;

import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class ApproverUI extends SuggestionViewerUI implements PostApproverUI {
    public void display(Dashboard dashboard){
        Scanner sc = new Scanner(System.in);
        User user = dashboard.getAuthenticatedUser();
        int option;
        int postIndex;
        String userInput;

        int numSuggestions = view(user);

        if(numSuggestions > 0) {
            System.out.println("(1) Approve/Reject Suggestion");
            System.out.println("(0) Back");
        }
        else {
            dashboard.loggedIn();
            return;
        }

        option = UserInput.getIntegerInput(0, 1, "SELECT AN ACTION: ");

        try {
            switch (option) {
                case 0 -> dashboard.loggedIn();
                case 1 -> {
                    postIndex = UserInput.getIntegerInput(0, numSuggestions-1, "Enter index of suggestion: ");
                    boolean isApproved = UserInput.getBoolInput("Enter 0 to reject, or 1 to approve: ");

                    try {
                        if (approve(user, postIndex, isApproved))
                            System.out.println("Success!");
                    } catch (Exception e) {
                        System.out.println("Unsuccessful: " + e.getMessage());
                    }
                }
            }
        }
        catch (InputMismatchException e){
            System.out.println("Invalid input. Please enter a number.");
            sc.nextLine();  // Consume the invalid input
        }
    }

    public abstract boolean approve(User user, int postIndex, boolean isApproved);
}
