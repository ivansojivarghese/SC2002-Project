package cams.dashboards.enquiry_menus;

import cams.dashboards.Dashboard;
import cams.dashboards.DashboardState;
import cams.dashboards.post_menus.PostViewerUI;
import cams.post_types.Post;
import cams.users.User;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public abstract class EnquiryViewerUI implements PostViewerUI, DashboardState {
    public EnquiryViewerUI(){}
    public void display(Dashboard dashboard){
        User user = dashboard.getAuthenticatedUser();
        Scanner userInput = new Scanner(System.in);
        int choice;

        if (view(user) == 0)
            System.out.println("No enquiries to display");
        System.out.println("(-1) Back");
        System.out.printf("SELECT AN ACTION: ");

        try {
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
            System.out.println("Index" + i + ": ");
            currentPost.displayContent();
            System.out.println("__________________________");
        }
        return 1;
    }
}
