package cams.reports;

import cams.Camp;
import cams.database.UnifiedUserRepository;
import cams.filters.Filter;
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

public class PerformanceReport implements ReportGenerator {
    @Override
    public void displayMenu() {
        // Display performance report menu options
        System.out.println("1. Generate Camp Attendance Report");
        System.out.println("2. Back to Main Menu");
    }

    @Override
    public void generateReport(Camp camp) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Scanner scanner = new Scanner(System.in);
            Sheet sheet = workbook.createSheet("Camp Participation Report");

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

            // Create a new row for column headers
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

            // Display sorting options
            System.out.println("Select Sorting Method:");
            System.out.println("1. Ascending Sort");
            System.out.println("2. Descending Sort");
            System.out.println("3. Points Sort");

            // Get user input
            System.out.print("Enter your choice: ");
            int sortingOption = scanner.nextInt();

            // After retrieving attendees and committee members, apply sorting methods based on user's choice
            List<String> sortedCommittee;
            switch (sortingOption) {
                case 1:
                    sortedCommittee = Filter.ascendingSort(committeeMembers.stream()
                            .map(User::getName)
                            .collect(Collectors.toList()));
                    break;
                case 2:
                    sortedCommittee = Filter.descendingSort(committeeMembers.stream()
                            .map(User::getName)
                            .collect(Collectors.toList()));
                    break;
                case 3:
                    sortedCommittee = Filter.pointsSort(committeeMembers, committee);
                    break;
                default:
                    // Handle invalid option, default to ascending sort
                    System.out.println("You selected an invalid option, defaulting to ascending sort!");
                    sortedCommittee = Filter.ascendingSort(committeeMembers.stream()
                            .map(User::getName)
                            .collect(Collectors.toList()));
            }

            scanner.nextLine();

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

            // Modify the outputPath to include the "outputs" folder
            String outputPath = "outputs/" + fileName + ".xlsx";

            // Write to file
            try (FileOutputStream fileOut = new FileOutputStream(outputPath)) {
                workbook.write(fileOut);
            }

            System.out.println("Camp Participation Report generated successfully. File saved at: " + outputPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
