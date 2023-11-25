package cams.reports;
import cams.Camp;

public interface ReportGenerator {
    void displayMenu();
    void generateReport(Camp camp);
}
