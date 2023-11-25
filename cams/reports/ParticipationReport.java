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
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ParticipationReport implements ReportGenerator {
    @Override
    public void displayMenu() {
        // Display participation report menu options
        System.out.println("1. Generate Participation Report");
        System.out.println("2. Back to Main Menu");
    }

    @Override
    public void generateReport(Camp camp) {
        try (Workbook workbook = new XSSFWorkbook()) {
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
            List<User> attendeesAndCommittee = camp.getAttendees().stream()
                    .map(userId -> UnifiedUserRepository.getInstance().retrieveUser(userId))
                    .collect(Collectors.toList());

            committee.keySet().forEach(committeeMemberId -> {
                User committeeMember = UnifiedUserRepository.getInstance().retrieveUser(committeeMemberId);
                if (committeeMember != null) {
                    attendeesAndCommittee.add(committeeMember);
                }
            });

            int option = 0;

            // After retrieving attendees and committee members, apply sorting methods
            List<String> sortedAttendeesAndCommitteeNames = Filter.ascendingSort(attendeesAndCommittee.stream()
                    .map(User::getName)
                    .collect(Collectors.toList()));



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
            Scanner scanner = new Scanner(System.in);
            String fileName = scanner.nextLine().trim();

            // Modify the outputPath to use a relative path to the "outputs" folder
            String outputPath = "outputs/" + fileName + ".xlsx";

            // Write to file
            try (FileOutputStream fileOut = new FileOutputStream(outputPath)) {
                workbook.write(fileOut);
            }

            System.out.println("Participation Report generated successfully. File saved at: " + outputPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
