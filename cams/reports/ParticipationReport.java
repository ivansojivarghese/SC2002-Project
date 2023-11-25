package cams.reports;
import cams.Camp;
import cams.users.User;
import cams.database.UnifiedUserRepository;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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

            // Populate data rows for committee members
            int rowNum = 11;
            for (Map.Entry<String, Integer> entry : committee.entrySet()) {
                User user = UnifiedUserRepository.getInstance().retrieveUser(entry.getKey());
                if (user != null) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(user.getUserID()); // UserID
                    row.createCell(1).setCellValue(user.getName()); // Name
                    row.createCell(2).setCellValue(user.getFaculty().toString()); // Faculty
                    row.createCell(3).setCellValue("Committee Member"); // Role
                    row.createCell(4).setCellValue(entry.getValue()); // Points
                }
            }

            // Access attendees directly from the camp instance
            for (String attendee : camp.getAttendees()) {
                User user = UnifiedUserRepository.getInstance().retrieveUser(attendee);
                if (user != null) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(user.getUserID()); // UserID
                    row.createCell(1).setCellValue(user.getName()); // Name
                    row.createCell(2).setCellValue(user.getFaculty().toString()); // Faculty
                    row.createCell(3).setCellValue("Attendee"); // Role
                    row.createCell(4).setCellValue("NA"); // Points for attendees
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
