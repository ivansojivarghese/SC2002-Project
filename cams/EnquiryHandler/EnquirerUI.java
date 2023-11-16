package cams.EnquiryHandler;

import cams.Dashboard;
import cams.DashboardState;
import cams.PostHandler.PosterUI;
import cams.PostTypes.Post;
import cams.User;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

public abstract class EnquirerUI extends EnquiryViewerUI implements PosterUI, DashboardState {
    public EnquirerUI() {
    }

    public void display(Dashboard dashboard){
        Post currentPost;
        Scanner userInput = new Scanner(System.in);
        int choice = 0;
        int postIndex;
        String content;
        User user = dashboard.getAuthenticatedUser();
        //Displays user's enquiries and returns 1 if user has enquiries, else returns 0
        boolean hasEnquiries = view(user) > 0;
            //Attempt to display enquiries
            //If display fails provide a different menu
            System.out.println("Select an action: ");
            System.out.println("(1) Submit a new enquiry");
            if(hasEnquiries) {
                System.out.println("(2) Edit an enquiry");
                System.out.println("(3) Delete an enquiry");
            }
            System.out.println("(-1) Back to user dashboard");

            try{
                choice = userInput.nextInt();
                userInput.nextLine();  // Consume the leftover newline

                switch (choice) {
                    case -1:
                        //Set dashboard state to the loggedIn menu
                        dashboard.loggedIn();
                        break;

                    case 1: //submit a new enquiry
                        System.out.println("Name of camp you are enquiring about: ");
                        String campName = userInput.nextLine();

                        System.out.println("Input new content: ");
                        content = userInput.nextLine();

                        if(submit(campName, user.getUserID(), content) == 1)
                            System.out.println("Success!");
                        break;

                    case 2: //Edit an enquiry
                        System.out.println("Enter index of enquiry to edit: ");
                        postIndex = userInput.nextInt();

                        System.out.println("Input new content: ");
                        content = userInput.nextLine();
                        if (edit(user, postIndex, content) == 1)
                            System.out.println("Success!");
                        else
                            System.out.println("Failed to submit, please ensure a valid Camp Name!");
                        break;

                    case 3: //Delete an enquiry
                        System.out.println("Enter index of enquiry to delete: ");
                        postIndex = userInput.nextInt();

                        if(delete(user, postIndex) == 1)
                            System.out.println("Success!");
                        else
                            System.out.println("Failed to delete post.");
                        break;

                    default:
                        System.out.println("Invalid integer input.");
                        break;
                }
            }
            catch (InputMismatchException e){
                System.out.println("Invalid input. Please enter a number.");
                userInput.nextLine();  // Consume the invalid input
            }
            //At the end of the display method, the main APP will redisplay the set menu
    }

    public abstract int submit(String campName, String userID, String text);
    public abstract int edit(User user, int postIndex, String content);
    public abstract int delete(User user, int postIndex);
}
