package cams.reports;
import cams.camp.Camp;

public interface ReportGenerator {
    void displayMenu();
    void generateReport(Camp camp);
}
