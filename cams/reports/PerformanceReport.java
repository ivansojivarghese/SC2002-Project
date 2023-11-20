package cams.reports;

import java.util.List;
import cams.database.UnifiedCampRepository;
import cams.database.UnifiedUserRepository;
import cams.Camp;
import cams.users.User;

public class PerformanceReport implements ReportGenerator{
    @Override
    public void displayMenu() {
        // Display performance report menu options
        System.out.println("1. Generate Performance Report for Camp Committee Members");
        System.out.println("2. Back to Main Menu");

    }

    @Override
    public void generateReport() {
        // implementation
        System.out.println("Generating Performance Report for Camp Committee Members...");

        
    }
}
