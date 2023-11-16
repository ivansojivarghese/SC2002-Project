package cams.SuggestionHandler;

import cams.Dashboard;
import cams.DashboardState;
import cams.PostHandler.PostViewerUI;
import cams.PostTypes.Post;
import cams.User;

import java.util.Scanner;

public abstract class SuggesterUI extends SuggestionViewerUI implements PostViewerUI, DashboardState {
    public void display(Dashboard dashboard){
        Post currentPost;
        Scanner userInput = new Scanner(System.in);
        User user = dashboard.getAuthenticatedUser();
        int choice;
        int postIndex;
        do {
            System.out.println("Select an action: ");
            System.out.println("1. View/edit/delete my suggestions");
            System.out.println("2. Submit a new suggestion");
            System.out.println("-1. Back");
            choice = userInput.nextInt();

            switch (choice){
                case 1: //view, edit or delete an enquiry
                    //displays user's enquiries
                    if(view(user) == 1){
                        System.out.println("Select an action: ");
                        System.out.println("1. Edit a suggestion");
                        System.out.println("2. Delete a suggestion");
                        System.out.println("-1. Back");
                        choice = userInput.nextInt();

                        switch (choice) {
                            case -1: choice = 0; break;
                            case 1:
                                System.out.println("Enter index of suggestion to edit: ");
                                choice = userInput.nextInt();
                                postIndex = choice;
                                System.out.println("Input new content: ");
                                String content = userInput.nextLine();
                                if (edit(user, postIndex, content)==1)
                                    System.out.println("Success!");
                                break;
                            case 2:
                                System.out.println("Enter index of suggestion to delete: ");
                                choice = userInput.nextInt();
                                postIndex = choice;
                                if(delete(user, postIndex) == 1)
                                    System.out.println("Success!");
                                else
                                    System.out.println("Failed to delete post.");
                                break;
                        }
                    } //else failed to display
                    break;
                case 2: //submit a new enquiry
                    System.out.println("Name of camp you are suggesting to: ");
                    String campName = userInput.nextLine();

                    System.out.println("Input content: ");
                    String content = userInput.nextLine();

                    if(submit(campName, user.getUserID(), content) == 1)
                        System.out.println("Success!");
                    break;
                case -1: //return
                    break;
            }
        } while(choice != -1);
    }
    public abstract int submit(String camp, String userID, String text);
    public abstract int edit(User user, int postIndex, String content);
    public abstract int delete(User user, int postIndex);
}
