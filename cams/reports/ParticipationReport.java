package cams.reports;

import java.util.List;

import cams.Camp;
import cams.users.User;

public class ParticipationReport implements ReportGenerator{
    @Override
    public void displayMenu() {
        // display participation report menu options
        System.out.println("1. Generate Camp Attendance Report");
        System.out.println("2. Back to Main Menu");
    }

    @Override
    public void generateReport() {
        // implementation
        System.out.println("Generating Camp Attendance Report...");

        // Add your logic to interact with the list of camps
        
    }
}
