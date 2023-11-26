package cams.suggestion.suggestion_menus;

import cams.dashboards.UI.Dashboard;
import cams.post_menus.PostViewerUI;
import cams.post_types.Post;
import cams.users.User;

import java.util.ArrayList;
import java.util.List;
/**
 * Abstract class providing the user interface for viewing suggestions.
 * Implements {@link PostViewerUI} to handle the display of suggestions before returning to the main menu
 */
public class SuggestionViewerUI implements PostViewerUI {
    /**
     * Displays the suggestion menu to the user
     *
     * @param dashboard The dashboard through which the user interacts.
     */
    public void display(Dashboard dashboard){
        User user = dashboard.getAuthenticatedUser();

        view(user);
        dashboard.loggedIn();
    }

    /**
     * Logic for displaying the suggestions of a specified user
     * @param user The user whose posts are to be displayed.
     * @return
     */
    public int view(User user){
        System.out.println("Camp Suggestions: ");

        //Catch null pointer
        if(user.getSuggestions() == null)
        {
            System.out.println("No suggestions to display.");
            return 0;
        }

        List<Post> mySuggestions = new ArrayList<>(user.getSuggestions());

        if(mySuggestions.isEmpty()){
            System.out.println("No suggestions to display.");
            return 0;
        }
        for (int i = 0; i < mySuggestions.size(); i++) {
            Post currentPost = mySuggestions.get(i);
            System.out.println(i + ": ");
            currentPost.displayContent();
            System.out.println("__________________________");
        }
        return mySuggestions.size();
    }
}
