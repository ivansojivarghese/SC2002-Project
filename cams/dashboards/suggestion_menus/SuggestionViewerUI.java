package cams.dashboards.suggestion_menus;

import cams.dashboards.Dashboard;
import cams.post_types.Post;
import cams.users.User;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class SuggestionViewerUI {
    public void display(Dashboard dashboard){
        User user = dashboard.getAuthenticatedUser();
        Scanner userInput = new Scanner(System.in);
        int choice;
        view(user);

        System.out.println("(-1) Back");
        System.out.printf("SELECT AN ACTION: ");
        try {
            choice = userInput.nextInt();

            switch (choice) {
                case -1:
                    dashboard.loggedIn();
                default:
                    System.out.println("Invalid input.");
            }
        }
        catch (InputMismatchException e){
            System.out.println("Invalid input. Please enter a number.");
            userInput.nextLine();  // Consume the invalid input
        }
    }

    public int view(User user){
        Post currentPost;
        System.out.println("Camp Suggestions: ");
        List<Post> mySuggestions = user.getSuggestions();
        if(mySuggestions.isEmpty()){
            System.out.println("No suggestions to display.");
            return 0;
        }
        for (int i = 0; i < mySuggestions.size(); i++) {
            currentPost = mySuggestions.get(i);
            System.out.println(i + ": ");
            currentPost.displayContent();
            System.out.println("__________________________");
        }
        return 1;
    }
}
