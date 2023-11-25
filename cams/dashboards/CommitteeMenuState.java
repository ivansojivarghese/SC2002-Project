package cams.dashboards;

import cams.Camp;
import cams.dashboards.enquiry_menus.Replier;
import cams.dashboards.suggestion_menus.Suggester;
import cams.database.UnifiedCampRepository;
import cams.reports.PerformanceReport;
import cams.reports.ReportGenerator;
import cams.users.Committable;
import cams.users.User;
import cams.util.UserInput;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommitteeMenuState extends StudentMenuState {
    public CommitteeMenuState() {
        super();
    }

    @Override
    public void display(Dashboard dashboard) {
        // Display User information
        this.userInfo(dashboard.getAuthenticatedUser());

        // Initialise and display hash map of menu options storing methods
        Map<Integer, MenuAction> options = initializeActions(dashboard);
        describeOptions(options);

        // Get user's choice
        int option = UserInput.getIntegerInput(0, options.size() , "SELECT AN ACTION: ");

        // Executes the lambda expression associated with the user's option
        options.getOrDefault(option, () -> System.out.println("Invalid option selected")).execute();
    }

    @Override
    protected void userInfo(User user) {
        // Code to display options
        System.out.println("                          DASHBOARD                           ");
        System.out.println("______________________________________________________________");

        System.out.println("STUDENT Name: " + user.getName());
        System.out.println("Username: " + user.getUserID());
        System.out.println("Faculty: " + user.getFaculty());
        if (user instanceof Committable)
            System.out.println("Committee Member of: " + ((Committable) user).getCommittee());
    }

    @Override
    protected Map<Integer, MenuAction> initializeActions(Dashboard dashboard) {
        Map<Integer, MenuAction> actions = super.initializeActions(dashboard);
        actions.put(6, () -> goToSuggest(dashboard));
        actions.put(7, () -> goToReply(dashboard));
        actions.put(8, () -> reportGenerator(dashboard));
        return actions;
    }

    @Override
    protected void describeOptions(Map<Integer, MenuAction> actions) {
        for (Integer key : actions.keySet()) {
            switch (key) {
                case 0 -> System.out.println("(0) Logout");
                case 1 -> System.out.println("(1) Change your password");
                case 2 -> System.out.println("(2) View my Camps");
                case 3 -> System.out.println("(3) Register for a Camp");
                case 4 -> System.out.println("(4) Withdraw from a Camp");
                case 5 -> System.out.println("(5) View enquiries menu");
                case 6 -> System.out.println("(6) View suggestions menu");
                case 7 -> System.out.println("(7) Reply to enquiries for my camp");
                case 8 -> System.out.println("(8) Generate report for my camp");
            }
        }
    }

    protected void goToSuggest(Dashboard dashboard) {
        dashboard.setState(new Suggester());
    }

    protected void goToReply(Dashboard dashboard) {
        dashboard.setState(new Replier());
    }

    protected void reportGenerator(Dashboard dashboard) {
        // Assuming the authenticated user is a committee member, you can get the list of camps they are part of
        User authenticatedUser = dashboard.getAuthenticatedUser();
        if (authenticatedUser instanceof Committable) {
            Committable committeeMember = (Committable) authenticatedUser;

            // Get the list of camps the committee member is part of, using singleton list here
            List<String> committeeCamps = Collections.singletonList(committeeMember.getCommittee());

            if (committeeCamps.isEmpty()) {
                System.out.println("You are not part of any camps. Unable to generate reports.");
            } else {
                // Display the list of camps the committee member is part of
                System.out.println("Select a camp to generate a report:");
                for (int i = 0; i < committeeCamps.size(); i++) {
                    System.out.println((i + 1) + ". " + committeeCamps.get(i));
                }

                // Get user input for the selected camp
                int selectedCampIndex = UserInput.getIntegerInput(1, committeeCamps.size(), "Enter the number of the camp: ") - 1;
                String selectedCampName = committeeCamps.get(selectedCampIndex);

                // Retrieve the Camp object based on the selected camp name
                Camp selectedCamp = UnifiedCampRepository.getInstance().retrieveCamp(selectedCampName);

                // Display menu for report generation (similar to staff menu)
                ReportGenerator performanceReport = new PerformanceReport();
                performanceReport.generateReport(selectedCamp);
            }
        } else {
            System.out.println("Error: Invalid user type for report generation.");
        }
    }

}