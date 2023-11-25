package cams.dashboards;

import cams.dashboards.enquiry_menus.Replier;
import cams.dashboards.suggestion_menus.Suggester;
import cams.users.Committable;
import cams.util.UserInput;

public class CommitteeMenuState extends StudentMenuState{
    public CommitteeMenuState(){
        super();
    }

    @Override
    public void display(Dashboard dashboard) {
        //Initialise Dashboard and User variables
        this.dashboard = dashboard;
        this.user = dashboard.getAuthenticatedUser();

        // Null check for dashboard
        if (dashboard == null) {
            throw new IllegalArgumentException("Dashboard cannot be null");
        }
        // Null check for user
        if (user == null) {
            throw new IllegalStateException("User cannot be null in dashboard");
        }

        // Display User information and the main menu
        this.userInfo();
        this.mainMenu();

        // Get user choice and process it
        int option = UserInput.getIntegerInput(0, 9, "SELECT AN ACTION: ");
        System.out.println();

        menuLogic(option);
    }

    @Override
    protected void userInfo(){
        // Code to display options
        System.out.println("                          DASHBOARD                           ");
        System.out.println("______________________________________________________________");

        System.out.println("STUDENT Name: " + user.getName());
        System.out.println("Username: " + user.getUserID());
        System.out.println("Faculty: " + user.getFaculty());
        if(user instanceof Committable)
            System.out.println("Committee Member of: " + ((Committable) user).getCommittee());
    }
    @Override
    protected void mainMenu() {
        //Displays the student main menu
        super.mainMenu();
        //Displays additional options available to committee members
        System.out.println("(7) View suggestions menu");
        System.out.println("(8) Reply to enquiries for my camp");
        System.out.println("(9) Generate report for my camp");
    }

    @Override
    protected void menuLogic(int option){
        super.menuLogic(option);

        switch (option){
            case 7 -> dashboard.setState(new Suggester());
            case 8 -> dashboard.setState(new Replier());
            //TODO implement report generator
            case 9 -> System.out.println("TODO");
        }
    }
}
