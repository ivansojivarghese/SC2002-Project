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
        Scanner sc = new Scanner(System.in);
        String input;
        int option;
        int postIndex;

        boolean hasEnquiry = view(user) > 0;
        if(hasEnquiry) {
            System.out.println("(1) Reply to an enquiry");
        }
        else {
            System.out.println("No enquiries to display");
            dashboard.loggedIn();
            return;
        }
        System.out.println("(0) Back");

        while(true) {
            try {
                System.out.print("SELECT AN ACTION: ");
                input = sc.nextLine().strip();
                option = Integer.parseInt(input);
                if(option >= 0 && option <= 1)
                    break;
                System.out.println("Invalid input, please try again.");
            }
            catch (Exception e){
                System.out.println("Error: " + e.getMessage());
            }
        }
        try {
            switch (option) {
                //Set dashboard to loggedIn menu state
                case 0 -> dashboard.loggedIn();

                //Reply to an enquiry
                case 1 -> {
                    String content;
                    do {
                        System.out.println("Enter index of enquiry to reply: ");
                        option = sc.nextInt();
                        sc.nextLine(); //consume new line character
                        postIndex = option;
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
