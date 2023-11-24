package cams.dashboards.enquiry_menus;

import cams.dashboards.Dashboard;
import cams.dashboards.post_menus.PostReplierUI;
import cams.users.User;
import cams.util.UserInput;

import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public abstract class ReplierUI extends EnquiryViewerUI implements PostReplierUI {
    public ReplierUI() {
    }

    public void display(Dashboard dashboard){
        User user = dashboard.getAuthenticatedUser();
        Scanner sc = new Scanner(System.in);
        String input;
        int option;
        int postIndex;

        int numEnquiries = view(user);
        if(numEnquiries > 0) {
            System.out.println("(1) Reply to an enquiry");
            System.out.println("(0) Back");
        }
        else {
            System.out.println("No enquiries to display");
            dashboard.loggedIn();
            return;
        }

        option = UserInput.getIntegerInput(0, 1, "SELECT AN ACTION: ");

        try {
            switch (option) {
                //Set dashboard to loggedIn menu state
                case 0 -> dashboard.loggedIn();

                //Reply to an enquiry
                case 1 -> {
                    String content;
                    option = UserInput.getIntegerInput(0, numEnquiries-1, "Enter index of enquiry to reply: ");
                    postIndex = option;
                    do {
                        System.out.println("Input reply: ");
                        content = sc.nextLine();
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
            sc.nextLine();  // Consume the invalid input
        }
    }

    @Override
    public abstract boolean reply(User user, int postIndex, String reply);
}
