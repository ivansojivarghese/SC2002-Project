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

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("UserID");
            headerRow.createCell(1).setCellValue("Name");
            headerRow.createCell(2).setCellValue("Role");
            headerRow.createCell(3).setCellValue("Camp Name");
            headerRow.createCell(4).setCellValue("Camp Date");
            headerRow.createCell(5).setCellValue("Faculty");
            headerRow.createCell(6).setCellValue("Points"); // New column for points

            // Access committee directly from the camp instance
            HashMap<String, Integer> committee = camp.getCommittee();

            // Populate data rows for committee members
            int rowNum = 1;
            for (Map.Entry<String, Integer> entry : committee.entrySet()) {
                User user = UnifiedUserRepository.getInstance().retrieveUser(entry.getKey());
                if (user != null) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(user.getUserID()); // UserID
                    row.createCell(1).setCellValue(user.getName()); // Name
                    row.createCell(2).setCellValue("Committee Member"); // Role
                    row.createCell(3).setCellValue(camp.getCampName()); // Camp Name
                    row.createCell(4).setCellValue(camp.getStartDate().toString()); // Camp Date
                    row.createCell(5).setCellValue(user.getFaculty().toString()); // Faculty
                    row.createCell(6).setCellValue(entry.getValue()); // Points
                }
            }

            // Access attendees directly from the camp instance
            for (String attendee : camp.getAttendees()) {
                User user = UnifiedUserRepository.getInstance().retrieveUser(attendee);
                if (user != null) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(user.getUserID()); // UserID
                    row.createCell(1).setCellValue(user.getName()); // Name
                    row.createCell(2).setCellValue("Attendee"); // Role
                    row.createCell(3).setCellValue(camp.getCampName()); // Camp Name
                    row.createCell(4).setCellValue(camp.getStartDate().toString()); // Camp Date
                    row.createCell(5).setCellValue(user.getFaculty().toString()); // Faculty
                    row.createCell(6).setCellValue("NA"); // Points for attendees
                }
            }

            // Allow the user to input the file name
            System.out.print("Enter the file name (without extension): ");
            Scanner scanner = new Scanner(System.in);
            String fileName = scanner.nextLine().trim();

            // Save the Excel file in the same directory as the Java class with the user-inputted file name
            String outputPath = fileName + ".xlsx";
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
