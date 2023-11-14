package cams.EnquiryHandler;

import cams.PostHandler.PostReplierUI;
import cams.PostTypes.Post;
import cams.User;

import java.util.List;
import java.util.Scanner;

public abstract class ReplierUI extends EnquiryViewerUI implements PostReplierUI {
    public void displayMenu(User user){
        Post currentPost;
        Scanner userInput = new Scanner(System.in);
        int choice;
        int postIndex;
        do {
            System.out.println("Select an action: ");
            System.out.println("1. View/Reply to enquiries");
            System.out.println("-1. Back");
            choice = userInput.nextInt();

            switch (choice){
                case 1:
                    if(view(user)==1) {
                        System.out.println("Select an action: ");
                        System.out.println("1. Reply to an enquiry");
                        System.out.println("-1. Back");
                        choice = userInput.nextInt();

                        switch (choice) {
                            case -1:
                                choice = 0;
                                break;
                            case 1:
                                System.out.println("Enter index of enquiry to reply: ");
                                choice = userInput.nextInt();
                                postIndex = choice;
                                System.out.println("Input reply: ");
                                String content = userInput.nextLine();
                                if (reply(user, postIndex, content) == 1)
                                    System.out.println("Success!");
                                break;
                        }
                    }
                    break;
            }
        } while(choice != -1);
    }

    @Override
    public abstract int reply(User user, int postIndex, String reply);

}
