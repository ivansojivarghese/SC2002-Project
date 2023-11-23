package cams.dashboards.suggestion_menus;

import cams.dashboards.Dashboard;
import cams.dashboards.DashboardState;
import cams.dashboards.post_menus.PostApproverUI;
import cams.users.User;

import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class ApproverUI extends SuggestionViewerUI implements PostApproverUI {
    public void display(Dashboard dashboard){
        Scanner userInput = new Scanner(System.in);
        User user = dashboard.getAuthenticatedUser();
        int choice;
        int postIndex;

        boolean hasSuggestion = view(user) > 0;

        if(hasSuggestion)
            System.out.println("(1) Approve/Reject Suggestion");
        System.out.println("(-1) Back");
        System.out.printf("SELECT AN ACTION: ");

        try {
            choice = userInput.nextInt();

            switch (choice) {
                case -1 -> dashboard.loggedIn();
                case 1 -> {
                    System.out.println("Enter index of Suggestion: ");
                    postIndex = userInput.nextInt();
                    userInput.nextLine();
                    System.out.println("Input 0 to reject, or 1 to approve: ");
                    String content = userInput.nextLine();
                    try {
                        if (approve(user, postIndex, content))
                            System.out.println("Success!");
                    } catch (Exception e) {
                        System.out.println("Unsuccessful: " + e.getMessage());
                    }
                }
            }
        }
        catch (InputMismatchException e){
            System.out.println("Invalid input. Please enter a number.");
            userInput.nextLine();  // Consume the invalid input
        }
    }

    public abstract boolean approve(User user, int postIndex, String isApproved);
}
