package cams.EnquiryHandler;

import cams.Dashboard;
import cams.DashboardState;
import cams.PostHandler.PostReplierUI;
import cams.PostTypes.Post;
import cams.User;

import java.util.List;
import java.util.Scanner;

public abstract class ReplierUI extends EnquiryViewerUI implements PostReplierUI, DashboardState {
    public ReplierUI() {
    }

    public void display(Dashboard dashboard){
        User user = dashboard.getAuthenticatedUser();
        Post currentPost;
        Scanner userInput = new Scanner(System.in);
        int choice;
        int postIndex;

        boolean hasEnquiry = view(user) > 0;

        System.out.println("Select an action: ");
        if(hasEnquiry) {
            System.out.println("(1) Reply to an enquiry");
        }
        System.out.println("(-1) Back");

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

    @Override
    public abstract int reply(User user, int postIndex, String reply);
}
