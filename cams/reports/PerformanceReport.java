package cams.reports;

import cams.camp.Camp;
import cams.database.UnifiedUserRepository;
import cams.util.Filter;
import cams.users.User;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link ReportGenerator} interface for generating performance reports.
 */
public class PerformanceReport implements ReportGenerator {
    /**
     * Displays the menu options for the performance report.
     */
    @Override
    public void displayMenu() {
        // Display performance report menu options
        System.out.println("1. Generate Committee Member Performance Report");
        System.out.println("2. Back to Main Menu");
        System.out.println("3. Exit"); // Added option to exit the application
    }
    /**
     * Generates a performance report for the given camp based on user input.
     *
     * @param camp The camp for which the performance report is generated.
     */
    @Override
    public void generateReport(Camp camp) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Display performance report menu options
            displayMenu();

            // Get user input for the selected option
            System.out.print("Enter your choice: ");
            int userChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            // Handle user choices
            switch (userChoice) {
                case 1 -> generateCampAttendanceReport(camp);
                case 2 -> {
                    // Exit the report generation
                    System.out.println("Exiting committee member report generation.");
                    return;
                }
                case 3 -> {
                    // Exit the application
                    System.out.println("Exiting application.");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    /**
     * Generates a camp attendance report and saves it to an Excel file.
     *
     * @param camp The camp for which the attendance report is generated.
     */
    private void generateCampAttendanceReport(Camp camp) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Scanner scanner = new Scanner(System.in);
            Sheet sheet = workbook.createSheet("Camp Committee Member Performance Report");

            // Add camp details at the top of the Excel file
            Row detailsRow = sheet.createRow(0);
            detailsRow.createCell(0).setCellValue("Camp Name: " + camp.getCampName());
            detailsRow = sheet.createRow(1);
            detailsRow.createCell(0).setCellValue("Dates: " + camp.getStartDate() + " to " + camp.getEndDate());
            detailsRow = sheet.createRow(2);
            detailsRow.createCell(0).setCellValue("Registration closes on: " + camp.getClosingDate());
            detailsRow = sheet.createRow(3);
            detailsRow.createCell(0).setCellValue("Open to: " + camp.getFacultyRestriction());
            detailsRow = sheet.createRow(4);
            detailsRow.createCell(0).setCellValue("Location: " + camp.getLocation());
            detailsRow = sheet.createRow(5);
            detailsRow.createCell(0).setCellValue("Available Attendee Slots: " +
                    camp.getRemainingAttendeeSlots() + " / " + camp.getAttendeeSlots());
            detailsRow = sheet.createRow(6);
            detailsRow.createCell(0).setCellValue("Committee Size: " +
                    camp.getCommittee().size() + " / " + camp.getCommitteeSlots());
            detailsRow = sheet.createRow(7);
            detailsRow.createCell(0).setCellValue("Description: " + camp.getDescription());
            detailsRow = sheet.createRow(8);
            detailsRow.createCell(0).setCellValue("Staff-in-Charge: " + camp.getInCharge());

            // Create header row for attendance details
            Row headerRow = sheet.createRow(10);
            headerRow.createCell(0).setCellValue("UserID");
            headerRow.createCell(1).setCellValue("Name");
            headerRow.createCell(2).setCellValue("Faculty");
            headerRow.createCell(3).setCellValue("Points");

            // Access committee directly from the camp instance
            HashMap<String, Integer> committee = camp.getCommittee();

            // Retrieve committee members from the camp instance
            List<User> committeeMembers = committee.keySet().stream()
                    .map(userId -> UnifiedUserRepository.getInstance().retrieveUser(userId))
                    .collect(Collectors.toList());

            // Get user input for sorting method
            int sortingOption = getSortingOption(scanner);

            // Consume the newline character
            scanner.nextLine();

            // After retrieving committee members, apply sorting methods based on user's choice
            List<String> sortedCommittee = applySortingMethod(sortingOption, committeeMembers, committee);

            // Populate data rows for sorted committee members
            int rowNum = 11;
            for (String userName : sortedCommittee) {
                User user = committeeMembers.stream()
                        .filter(u -> u.getName().equals(userName))
                        .findFirst()
                        .orElse(null);

                if (user != null) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(user.getUserID());
                    row.createCell(1).setCellValue(user.getName());
                    row.createCell(2).setCellValue(user.getFaculty().toString());
                    row.createCell(3).setCellValue(committee.getOrDefault(user.getUserID(), 0));
                }
            }

            // Allow the user to input the file name
            System.out.print("Enter the file name (without extension): ");
            String fileName = scanner.nextLine().trim();

            // Modify the outputPath to use a relative path to the "outputs" folder
            String outputPath = "outputs/" + fileName + ".csv";

            // Write to file
            try (FileOutputStream fileOut = new FileOutputStream(outputPath)) {
                workbook.write(fileOut);
            }

            System.out.println("Camp Performance Report generated successfully. File saved at: " + outputPath);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    /**
     * Gets the user's choice for the sorting method.
     *
     * @param scanner The scanner object for user input.
     * @return The user's choice for the sorting method.
     */
    private int getSortingOption(Scanner scanner) {
        // Display sorting options
        System.out.println("Select Sorting Method:");
        System.out.println("1. Ascending Sort");
        System.out.println("2. Descending Sort");
        System.out.println("3. Points Sort");

        // Get user input
        System.out.print("Enter your choice: ");
        return scanner.nextInt();
    }
    /**
     * Applies the selected sorting method to the list of committee members.
     *
     * @param sortingOption     The selected sorting method.
     * @param committeeMembers  The list of committee members.
     * @param committee         The committee members and their associated points.
     * @return                  The list of sorted committee member names.
     */
    private List<String> applySortingMethod(int sortingOption, List<User> committeeMembers, HashMap<String, Integer> committee) {
        // Handle invalid option, default to ascending sort
        return switch (sortingOption) {
            case 1 -> Filter.ascendingSort(committeeMembers.stream()
                    .map(User::getName)
                    .collect(Collectors.toList()));
            case 2 -> Filter.descendingSort(committeeMembers.stream()
                    .map(User::getName)
                    .collect(Collectors.toList()));
            case 3 -> Filter.pointsSort(committeeMembers, committee);
            default -> {
                System.out.println("You selected an invalid option, defaulting to ascending sort!");
                yield Filter.ascendingSort(committeeMembers.stream()
                        .map(User::getName)
                        .collect(Collectors.toList()));
            }
        };
    }
}
