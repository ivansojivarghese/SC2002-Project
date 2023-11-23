package cams.dashboards.enquiry_menus;

import cams.dashboards.Dashboard;
import cams.dashboards.DashboardState;
import cams.dashboards.post_menus.PostReplierUI;
import cams.users.User;

import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public abstract class ReplierUI extends EnquiryViewerUI implements PostReplierUI {
    public ReplierUI() {
    }

    public void display(Dashboard dashboard){
        User user = dashboard.getAuthenticatedUser();
        Scanner userInput = new Scanner(System.in);
        int choice;
        int postIndex;

        boolean hasEnquiry = view(user) > 0;
        if(hasEnquiry) {
            System.out.println("(1) Reply to an enquiry");
        }
        System.out.println("(-1) Back");
        System.out.print("SELECT AN ACTION: ");
        try {
            choice = userInput.nextInt();
            userInput.nextLine(); //Consume the new line
            switch (choice) {
                //Set dashboard to loggedIn menu state
                case -1 -> dashboard.loggedIn();

                //Reply to an enquiry
                case 1 -> {
                    String content;
                    do {
                        System.out.println("Enter index of enquiry to reply: ");
                        choice = userInput.nextInt();
                        userInput.nextLine(); //consume new line character
                        postIndex = choice;
                        System.out.println("Input reply: ");
                        content = userInput.nextLine();
                    }
                    while (Objects.equals(content.strip(), ""));
                    try {
                        if (reply(user, postIndex, content))
                            System.out.println("Success!");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
            }
        }
        catch (InputMismatchException e){
            System.out.println("Invalid input. Please enter a number.");
            userInput.nextLine();  // Consume the invalid input
        }
    }

    @Override
    public abstract boolean reply(User user, int postIndex, String reply);
}
