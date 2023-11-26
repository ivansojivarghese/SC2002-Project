package cams.dashboards;

import cams.camp.Camp;
import cams.enquiry_menus.ReplierUI;
import cams.suggestion_menus.SuggesterUI;
import cams.database.UnifiedCampRepository;
import cams.reports.PerformanceReport;
import cams.reports.ReportGenerator;
import cams.users.Committable;
import cams.users.User;
import cams.util.UserInput;

import java.util.List;
import java.util.Map;

/**
 * Represents the dashboard state for a committee member.
 * This class extends {@link StudentUI} and adds additional functionalities specific to {@link Committable committee members},
 * such as managing suggestions, replying to enquiries, and generating reports.
 */
public class CommitteeUI extends StudentUI {
    /**
     * Constructs a CommitteeUI instance.
     */
    public CommitteeUI() {
        super();
    }

    /**
     * Displays the committee member menu options and handles user input for various actions.
     *
     * @param dashboard The dashboard through which the user interacts.
     */
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
        // Displaying user details and committee membership if applicable
        System.out.println("                          DASHBOARD                           ");
        System.out.println("______________________________________________________________");

        System.out.println("STUDENT Name: " + user.getName());
        System.out.println("Username: " + user.getUserID());
        System.out.println("Faculty: " + user.getFaculty());
        if (user instanceof Committable)
            System.out.println("Committee Member of: " + ((Committable) user).getCommittee());
    }

    // Initializing actions for committee-specific functionalities
    @Override
    protected Map<Integer, MenuAction> initializeActions(Dashboard dashboard) {
        Map<Integer, MenuAction> actions = super.initializeActions(dashboard);
        actions.put(6, () -> goToSuggest(dashboard));
        actions.put(7, () -> goToReply(dashboard));
        actions.put(8, () -> reportGenerator(dashboard, new PerformanceReport()));
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
                case 8 -> System.out.println("(8) Generate participation report for my camp");
            }
        }
    }

    /**
     * Sets the state of the dashboard to the suggestion menu
     * @param dashboard The current dashboard context object
     */
    protected void goToSuggest(Dashboard dashboard) {
        dashboard.setState(new SuggesterUI());
    }

    /**
     * Sets the state of the dashboard to the reply menu
     * @param dashboard The current dashboard context object
     */
    protected void goToReply(Dashboard dashboard) {
        dashboard.setState(new ReplierUI());
    }

    /**
     * Gets user input required to generate a report for the camp that the user is a committee member of.
     * Outputs the report in .xlsx format
     * @param dashboard The dashboard context object
     * @param reportGenerator The {@link ReportGenerator} dependency that is injected
     */
    protected void reportGenerator(Dashboard dashboard, ReportGenerator reportGenerator) {
        User authenticatedUser = dashboard.getAuthenticatedUser();

        List<String> userCamps = authenticatedUser.getMyCamps();

        if (userCamps.isEmpty()) {
            System.out.println("You are not part of any camps. Unable to generate reports.");
        } else {
            // Display the list of camps the user is part of
            System.out.println("Generating reports for your camps:");

            // Iterate over each camp
            for (String campName : userCamps) {
                // Retrieve the Camp object based on the camp name
                Camp currentCamp = UnifiedCampRepository.getInstance().retrieveCamp(campName);

                // Now you have the currentCamp object, you can proceed with generating the report.
                reportGenerator.generateReport(currentCamp);
            }

            System.out.println("Reports generated successfully.");
        }
    }






}