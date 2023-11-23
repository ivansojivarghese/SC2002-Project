package cams.dashboards;

import cams.dashboards.enquiry_menus.Replier;
import cams.dashboards.suggestion_menus.Suggester;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CommitteeMenuState extends StudentMenuState{
    private Dashboard dashboard;
    public CommitteeMenuState(){
    }
    public void display(Dashboard dashboard) {
        //Initialise Dashboard and User variable
        this.dashboard = dashboard;

        //Initialise variables and scanner for user input
        int option = 0;
        Scanner sc = new Scanner(System.in);

        // Display options of the main menu
        this.mainMenu();

        //GET user choice
        do {
            try {
                System.out.print("SELECT AN ACTION: ");
                option = sc.nextInt();
                sc.nextLine(); //consume new line
                break;
            }
            catch (InputMismatchException e){
                System.out.println("Invalid input, please enter an integer.");
            }
        } while(option < 1 || option > 9);

        System.out.println();
        sc.close();

        menuLogic(option);
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

    protected void menuLogic(int option){
        String userInput;
        Scanner sc = new Scanner(System.in);

        super.menuLogic(option);

        switch (option){
            case 7 -> dashboard.setState(new Suggester());
            case 8 -> dashboard.setState(new Replier());
            //TODO implement report generator
            case 9 -> System.out.println("TODO");
        }
        sc.close();
    }
}
