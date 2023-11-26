package cams.reports;
import cams.camp.Camp;

/**
 * The {@code ReportGenerator} interface provides methods for generating and displaying reports.
 */
public interface ReportGenerator {
    /**
     * Displays the menu options for the specific report type.
     */
    void displayMenu();
    /**
     * Generates a report based on the provided camp information.
     *
     * @param camp The camp for which the report is generated.
     */
    void generateReport(Camp camp);
}
