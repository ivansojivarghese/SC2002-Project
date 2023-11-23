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

        view(user);
        dashboard.loggedIn();
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
        return mySuggestions.size();
    }
}
