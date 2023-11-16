package cams.dashboards.enquiry_menus;

import cams.dashboards.Dashboard;
import cams.dashboards.DashboardState;
import cams.dashboards.post_menus.PostReplierUI;
import cams.post_types.Post;
import cams.users.User;

import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class ReplierUI extends EnquiryViewerUI implements PostReplierUI, DashboardState {
    public ReplierUI() {
    }

    public void display(Dashboard dashboard){
        User user = dashboard.getAuthenticatedUser();
        Scanner userInput = new Scanner(System.in);
        int choice;
        int postIndex;

        boolean hasEnquiry = view(user) > 0;

        System.out.println("Select an action: ");
        if(hasEnquiry) {
            System.out.println("(1) Reply to an enquiry");
        }
        System.out.println("(-1) Back");
        try {
            choice = userInput.nextInt();
            userInput.nextLine(); //Consume the new line
            switch (choice) {
                //Set dashboard to loggedIn menu state
                case -1:
                    dashboard.loggedIn();
                    break;
                //Reply to an enquiry
                case 1:
                    System.out.println("Enter index of enquiry to reply: ");
                    choice = userInput.nextInt();
                    postIndex = choice;
                    System.out.println("Input reply: ");
                    String content = userInput.nextLine();
                    if (reply(user, postIndex, content) == 1)
                        System.out.println("Success!");
                    break;
            }
        }
        catch (InputMismatchException e){
            System.out.println("Invalid input. Please enter a number.");
            userInput.nextLine();  // Consume the invalid input
        }
    }

    @Override
    public abstract int reply(User user, int postIndex, String reply);
}
