package cams.dashboards.suggestion_menus;

import cams.dashboards.Dashboard;
import cams.dashboards.DashboardState;
import cams.dashboards.post_menus.PostViewerUI;
import cams.post_types.Post;
import cams.users.Student;
import cams.users.User;

import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class SuggesterUI extends SuggestionViewerUI implements PostViewerUI, DashboardState {
    public void display(Dashboard dashboard){
        Scanner userInput = new Scanner(System.in);
        User user = dashboard.getAuthenticatedUser();
        String content;
        int choice;
        int postIndex;

        boolean hasSuggestion = view(user) > 0;

        System.out.println("(1) Submit a new suggestion");
        if(hasSuggestion){
            System.out.println("(2) Edit a suggestion");
            System.out.println("(3) Delete a suggestions");
        }
        System.out.println("(-1) Back");
        System.out.printf("SELECT AN ACTION: ");
        try {
            choice = userInput.nextInt();

            switch (choice) {
                case -1:
                    dashboard.loggedIn();
                    break;
                case 1:
                    String campName = ((Student)user).getCommittee();
                    System.out.println("Name of camp you are suggesting to: " +campName);

                    System.out.println("Input content: ");
                    content = userInput.nextLine();

                    if (submit(campName, user.getUserID(), content) == 1)
                        System.out.println("Success!");
                    break;
                case 2: //submit a new enquiry
                    System.out.println("Enter index of suggestion to edit: ");
                    choice = userInput.nextInt();
                    postIndex = choice;
                    System.out.println("Input new content: ");
                    content = userInput.nextLine();
                    if (edit(user, postIndex, content) == 1)
                        System.out.println("Success!");
                    break;
                case 3:
                    System.out.println("Enter index of suggestion to delete: ");
                    choice = userInput.nextInt();
                    postIndex = choice;
                    if (delete(user, postIndex) == 1)
                        System.out.println("Success!");
                    else
                        System.out.println("Failed to delete post.");
                    break;

                default:
                    System.out.println("Invalid input!");
            }
        }
        catch (InputMismatchException e){
            System.out.println("Invalid input. Please enter a number.");
            userInput.nextLine();  // Consume the invalid input
        }
    }
    public abstract int submit(String camp, String userID, String text);
    public abstract int edit(User user, int postIndex, String content);
    public abstract int delete(User user, int postIndex);
}
