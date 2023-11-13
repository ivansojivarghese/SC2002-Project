package cams.EnquiryHandler;
import cams.Camp;
import cams.PostHandler.PostViewerUI;
import cams.Student;
import cams.UnifiedCampRepository;
import cams.User;
import cams.PostTypes.*;
import java.io.*;
import java.util.Locale;
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
        if(user instanceof Student) {
            Student student = (Student) user;
            System.out.println("My Enquiries: ");
            for (int i = 0; i < student.getMyEnquiries().size(); i++) {
                currentPost = student.getMyEnquiries().get(i);
                System.out.println(i + ": " + currentPost.getContent());
                System.out.println("__________________________");
            }
            return 1;
        }
        return -1;
    }
}
