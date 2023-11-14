package cams.EnquiryHandler;

import cams.PostHandler.PostViewerUI;
import cams.PostTypes.Post;
import cams.User;

import java.util.List;
import java.util.Scanner;

public abstract class EnquiryViewerUI implements PostViewerUI {
    public void displayMenu(User user){
        Post currentPost;
        Scanner userInput = new Scanner(System.in);
        int choice;
        do {
            System.out.println("Select an action: ");
            System.out.println("1. View my enquiries");
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
        System.out.println("My Camp Enquiries: ");
        List<Post> myEnquiries = user.getEnquiries();
        for (int i = 0; i < myEnquiries.size(); i++) {
            currentPost = myEnquiries.get(i);
            System.out.println(i + ": ");
            currentPost.displayContent();
            System.out.println("__________________________");
        }
        return 1;
    }
}
