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
            System.out.println("1. View/edit/delete my enquiries");
            System.out.println("2. Submit a new enquiry");
            System.out.println("3. Back");
            choice = userInput.nextInt();

            switch (choice){
                case 1: //view, edit or delete an enquiry
                    if(user instanceof Student){
                        Student student = (Student) user;
                        System.out.println("My Enquiries: ");
                        for (int i = 0; i < student.getMyEnquiries().size(); i++) {
                            currentPost = student.getMyEnquiries().get(i);
                            System.out.println(i + ": " + currentPost.getContent());
                            System.out.println("__________________________");
                        }
                        System.out.println("Select an action: ");
                        System.out.println("1. Edit an enquiry");
                        System.out.println("2. Delete an Enquiry");
                        System.out.println("3. Back");
                        choice = userInput.nextInt();

                        switch (choice){
                            case 1:
                                System.out.println("Enter index of enquiry to edit: ");
                                choice = userInput.nextInt();
                                currentPost = student.getMyEnquiries().get(choice);
                                System.out.println("Input new content: ");
                                String content = userInput.nextLine();

                                if (edit(currentPost, content)==1)
                                    System.out.println("Success!");
                                break;
                            case 2:
                                System.out.println("Enter index of enquiry to delete: ");
                                choice = userInput.nextInt();
                                currentPost = student.getMyEnquiries().get(choice);

                                if(delete(currentPost) == 1)
                                    System.out.println("Success!");
                                /*
                                if(currentPost instanceof Enquiry){
                                    Enquiry enquiry = (Enquiry) currentPost;
                                    if(enquiry.getReply() == null)
                                        delete(currentPost);
                                    else
                                        System.out.println("Unable to delete enquiry with responses.");
                                }*/
                                break;
                            case 3: //return to previous menu
                                choice = 0;
                                break;
                        }
                    }
                    break;
                case 2: //submit a new enquiry
                    System.out.println("Name of camp you are enquiring about: ");
                    String campName = userInput.nextLine().toLowerCase(Locale.ROOT);
                    UnifiedCampRepository repo = UnifiedCampRepository.getInstance();
                    Camp camp = repo.retrieveCamp(campName);
                    System.out.println("Input new content: ");
                    String content = userInput.nextLine();

                    if(submit(camp, user.getUserID(), content) == 1)
                        System.out.println("Success!");
                    break;
                case 3: //return
                    break;
            }
        } while(choice != 3);
    }

    public abstract int submit(Camp camp, String userID, String text);
    public abstract int edit(Post post, String content);
    public abstract int delete(Post post);
}
