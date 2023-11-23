package cams.dashboards.suggestion_menus;

import cams.dashboards.Dashboard;
import cams.dashboards.post_menus.PostApproverUI;
import cams.users.User;

import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class ApproverUI extends SuggestionViewerUI implements PostApproverUI {
    public void display(Dashboard dashboard){
        Scanner sc = new Scanner(System.in);
        User user = dashboard.getAuthenticatedUser();
        int option;
        int postIndex;
        String userInput;

        boolean hasSuggestion = view(user) > 0;

        if(hasSuggestion)
            System.out.println("(1) Approve/Reject Suggestion");
        else {
            System.out.println("No suggestions to display");
            dashboard.loggedIn();
            return;
        }
        System.out.println("(0) Back");

        while(true) {
            try {
                System.out.print("SELECT AN ACTION: ");
                userInput = sc.nextLine().strip();
                option = Integer.parseInt(userInput);
                if(option >= 0 && option <= 1)
                    break;
                System.out.println("Invalid input, please try again.");
            }
            catch (Exception e){
                System.out.println("Error: " + e.getMessage());
            }
        }

        try {
            switch (option) {
                case 0 -> dashboard.loggedIn();
                case 1 -> {
                    System.out.println("Enter index of Suggestion: ");
                    postIndex = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Input 0 to reject, or 1 to approve: ");
                    String content = sc.nextLine();
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
            sc.nextLine();  // Consume the invalid input
        }
    }

    public abstract boolean approve(User user, int postIndex, String isApproved);
}
