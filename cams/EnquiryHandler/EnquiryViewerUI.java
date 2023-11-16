package cams.EnquiryHandler;

import cams.Dashboard;
import cams.DashboardState;
import cams.PostHandler.PostViewerUI;
import cams.PostTypes.Post;
import cams.User;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public abstract class EnquiryViewerUI implements PostViewerUI, DashboardState {
    public EnquiryViewerUI(){}
    public void display(Dashboard dashboard){
        User user = dashboard.getAuthenticatedUser();
        Scanner userInput = new Scanner(System.in);
        int choice;

        try {
            if (view(user) == 0)
                System.out.println("No enquiries to display");
            System.out.println("Select an action: ");
            System.out.println("(-1) Back");
            choice = userInput.nextInt();

            switch (choice) {
                case -1:
                    //Return to loggedIn menu
                    dashboard.loggedIn();
                    break;
                default:
                    System.out.println("Invalid input.");
                    break;
            }
        }
        catch (InputMismatchException e){
            System.out.println("Invalid input. Please enter a number.");
            userInput.nextLine();  // Consume the invalid input
        }
    }

    public int view(User user){
        Post currentPost;
        System.out.println("My Camp Enquiries: ");
        List<Post> myEnquiries = user.getEnquiries();
        if(myEnquiries.isEmpty()){
            System.out.println("No enquiries to display.");
            return 0;
        }
        for (int i = 0; i < myEnquiries.size(); i++) {
            currentPost = myEnquiries.get(i);
            System.out.println(i + ": ");
            currentPost.displayContent();
            System.out.println("__________________________");
        }
        return 1;
    }
}
