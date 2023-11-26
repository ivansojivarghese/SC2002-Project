package cams.reports;

import cams.camp.Camp;
import cams.database.UnifiedUserRepository;
import cams.filters.Filter;
import cams.users.User;

import cams.util.InputScanner;
import cams.util.UserInput;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParticipationReport implements ReportGenerator {
    private Scanner scanner; // Declare scanner as an instance variable

    @Override
    public void displayMenu() {
        // Display participation report menu options
        System.out.println("1. Generate Participation Report");
        System.out.println("2. Back to Main Menu");
        System.out.println("3. Exit"); // Added option to exit the application
    }

    @Override
    public void generateReport(Camp camp) {
        try (Workbook workbook = new XSSFWorkbook()) {
            scanner = InputScanner.getInstance(); // Initialize the scanner

            Sheet sheet = workbook.createSheet("Participation Report");

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

            // Create header row for participation details
            Row headerRow = sheet.createRow(10);
            headerRow.createCell(0).setCellValue("UserID");
            headerRow.createCell(1).setCellValue("Name");
            headerRow.createCell(2).setCellValue("Faculty");
            headerRow.createCell(3).setCellValue("Role");
            headerRow.createCell(4).setCellValue("Points");

            // Access committee directly from the camp instance
            HashMap<String, Integer> committee = camp.getCommittee();

            // Retrieve attendees and committee members from the camp instance
            List<User> attendees = camp.getAttendees().stream()
                    .map(userId -> UnifiedUserRepository.getInstance().retrieveUser(userId))
                    .collect(Collectors.toList());

            List<User> committeeMembers = committee.keySet().stream()
                    .map(committeeMemberId -> UnifiedUserRepository.getInstance().retrieveUser(committeeMemberId))
                    .collect(Collectors.toList());

            // Combine attendees and committee members, avoiding duplications
            List<User> attendeesAndCommittee = Stream.concat(attendees.stream(), committeeMembers.stream())
                    .distinct()
                    .collect(Collectors.toList());

            // After retrieving attendees and committee members, apply sorting methods based on user's choice
            // Get user input for sorting method
            int sortingOption = getSortingOption();

            scanner.nextLine();

            List<String> sortedAttendeesAndCommitteeNames = applySortingMethod(sortingOption, attendeesAndCommittee, committee);

            // Populate data rows for sorted attendees and committee members
            int rowNum = 11;
            for (String userName : sortedAttendeesAndCommitteeNames) {
                User user = attendeesAndCommittee.stream()
                        .filter(u -> u.getName().equals(userName))
                        .findFirst()
                        .orElse(null);

                if (user != null) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(user.getUserID());
                    row.createCell(1).setCellValue(user.getName());
                    row.createCell(2).setCellValue(user.getFaculty().toString());
                    row.createCell(3).setCellValue(committee.containsKey(user.getUserID()) ? "Committee Member" : "Attendee");
                    row.createCell(4).setCellValue(committee.getOrDefault(user.getUserID(), 0));
                }
            }

            // Allow the user to input the file name
            System.out.print("Enter the file name (without extension): ");
            String fileName = scanner.nextLine().trim();

            // Modify the outputPath to use a relative path to the "outputs" folder
            String outputPath = "outputs" + File.separator + fileName + ".xlsx";

            // Write to file
            try (FileOutputStream fileOut = new FileOutputStream(outputPath)) {
                workbook.write(fileOut);
            }

            System.out.println("Participation Report generated successfully. File saved at: " + outputPath);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private int getSortingOption() {
        // Display sorting options
        System.out.println("Select Sorting Method:");
        System.out.println("1. Ascending Sort");
        System.out.println("2. Descending Sort");
        System.out.println("3. Points Sort");
        System.out.println("4. Role Sort");

        // Get user input
        int choice = UserInput.getIntegerInput(0, 4, "Enter your choice: ");
        return choice;
    }

    private List<String> applySortingMethod(int sortingOption, List<User> attendeesAndCommittee, HashMap<String, Integer> committee) {
        return switch (sortingOption) {
            case 1 -> Filter.ascendingSort(attendeesAndCommittee.stream()
                    .map(User::getName)
                    .collect(Collectors.toList()));
            case 2 -> Filter.descendingSort(attendeesAndCommittee.stream()
                    .map(User::getName)
                    .collect(Collectors.toList()));
            case 3 -> Filter.pointsSort(attendeesAndCommittee, committee);
            case 4 -> Filter.roleSort(attendeesAndCommittee, committee);
            default -> {
                // Handle invalid option, default to ascending sort
                System.out.println("You selected an invalid option, defaulting to ascending sort!");
                yield Filter.ascendingSort(attendeesAndCommittee.stream()
                        .map(User::getName)
                        .collect(Collectors.toList()));
            }
        };
    }
}
