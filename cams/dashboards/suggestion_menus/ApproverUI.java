package cams.dashboards.suggestion_menus;

import cams.dashboards.Dashboard;
import cams.dashboards.DashboardState;
import cams.dashboards.post_menus.PostApproverUI;
import cams.post_types.Post;
import cams.users.User;

import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class ApproverUI extends SuggestionViewerUI implements PostApproverUI, DashboardState {
    public void display(Dashboard dashboard){
        Scanner userInput = new Scanner(System.in);
        User user = dashboard.getAuthenticatedUser();
        int choice;
        int postIndex;

        boolean hasSuggestion = view(user) > 0;

        System.out.println("Select an action: ");
        if(hasSuggestion)
            System.out.println("(1) Approve/Reject Suggestion");
        System.out.println("(-1) Back");

        try {
            choice = userInput.nextInt();

            switch (choice) {
                case -1:
                    dashboard.loggedIn();
                    break;
                case 1:
                    System.out.println("Enter index of Suggestion: ");
                    postIndex = userInput.nextInt();
                    userInput.nextLine();

                    System.out.println("Input 0 to reject, or 1 to approve: ");
                    String content = userInput.nextLine();

                    if (approve(user, postIndex, content) == 1)
                        System.out.println("Success!");
                    break;
            }
        }
        catch (InputMismatchException e){
            System.out.println("Invalid input. Please enter a number.");
            userInput.nextLine();  // Consume the invalid input
        }
    }

    public abstract int approve(User user, int postIndex, String isApproved);
}