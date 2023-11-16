package cams.SuggestionHandler;

import cams.Dashboard;
import cams.DashboardState;
import cams.PostHandler.PostApproverUI;
import cams.PostHandler.PostViewerUI;
import cams.PostTypes.Post;
import cams.User;

import java.util.Scanner;

public abstract class ApproverUI extends SuggestionViewerUI implements PostApproverUI, DashboardState {
    public void display(Dashboard dashboard){
        Post currentPost;
        Scanner userInput = new Scanner(System.in);
        User user = dashboard.getAuthenticatedUser();
        int choice;
        int postIndex;
        do {
            System.out.println("Select an action: ");
            System.out.println("1. View/Approve/Reject Suggestions");
            System.out.println("-1. Back");
            choice = userInput.nextInt();

            switch (choice){
                case 1:
                    if(view(user)==1) {
                        System.out.println("Select an action: ");
                        System.out.println("1. Select a Suggestion");
                        System.out.println("-1. Back");
                        choice = userInput.nextInt();

                        switch (choice) {
                            case -1:
                                choice = 0;
                                break;
                            case 1:
                                System.out.println("Enter index of Suggestion: ");
                                choice = userInput.nextInt();
                                postIndex = choice;
                                System.out.println("Input 0 to reject, or 1 to approve: ");
                                String content = userInput.nextLine();
                                if (approve(user, postIndex, content) == 1)
                                    System.out.println("Success!");
                                break;
                        }
                    }
                    break;
            }
        } while(choice != -1);
    }

    public abstract int approve(User user, int postIndex, String isApproved);
}
