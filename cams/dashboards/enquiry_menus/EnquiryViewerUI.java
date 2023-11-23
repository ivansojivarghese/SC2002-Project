package cams.dashboards.enquiry_menus;

import cams.dashboards.Dashboard;
import cams.dashboards.post_menus.PostViewerUI;
import cams.post_types.Post;
import cams.users.User;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public abstract class EnquiryViewerUI implements PostViewerUI {
    public EnquiryViewerUI(){}
    public void display(Dashboard dashboard){
        User user = dashboard.getAuthenticatedUser();

        if (view(user) == 0)
            System.out.println("No enquiries to display");

        dashboard.loggedIn();
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
        return myEnquiries.size();
    }
}
