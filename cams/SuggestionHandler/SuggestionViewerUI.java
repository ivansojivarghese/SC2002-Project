package cams.SuggestionHandler;

import cams.PostTypes.Post;
import cams.User;

import java.util.List;
import java.util.Scanner;

public class SuggestionViewerUI {
    public void displayMenu(User user){
        Post currentPost;
        Scanner userInput = new Scanner(System.in);
        int choice;
        do {
            System.out.println("Select an action: ");
            System.out.println("1. View my suggestions");
            System.out.println("-1. Back");
            choice = userInput.nextInt();

            switch (choice){
                case 1:
                    view(user);
                    break;
                case 2:
                    break;
            }
        } while(choice != -1);
    }

    public int view(User user){
        Post currentPost;
        System.out.println("Camp Suggestions: ");
        List<Post> mySuggestions = user.getSuggestions();
        for (int i = 0; i < mySuggestions.size(); i++) {
            currentPost = mySuggestions.get(i);
            System.out.println(i + ": ");
            currentPost.displayContent();
            System.out.println("__________________________");
        }
        return 1;
    }
}
