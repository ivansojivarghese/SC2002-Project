package cams.reports;

import cams.camp.Camp;
import cams.database.UnifiedUserRepository;
import cams.util.Filter;
import cams.users.User;

import cams.util.UserInput;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;
/**
 * Implementation of the {@link ReportGenerator} interface for generating participation reports.
 */
public class ParticipationReport implements ReportGenerator {
    private Scanner scanner; // Declare scanner as an instance variable
    /**
     * Displays the menu options for the participation report.
     */
    @Override
    public void displayMenu() {
        // Display participation report menu options
        System.out.println("1. Generate Participation Report");
        System.out.println("2. Back to Main Menu");
        System.out.println("3. Exit"); // Added option to exit the application
    }
    /**
     * Generates a participation report for the given camp.
     *
     * @param camp The camp for which the participation report is generated.
     */
    @Override
    public void generateReport(Camp camp) {
        try {
            Scanner scanner = new Scanner(System.in);

            // Allow the user to input the file name
            System.out.print("Enter the file name (without extension): ");
            String fileName = scanner.nextLine().trim();

            // Modify the outputPath to use a relative path to the "outputs" folder
            String outputPath = "outputs/" + fileName + ".txt";

            // Write to text file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
                // Write camp details at the top of the file
                writer.write("==================== PARTICIPATION REPORT ====================");
                writer.newLine();
                writer.write("Camp Name: " + camp.getCampName());
                writer.newLine();
                writer.write("Dates: " + camp.getStartDate() + " to " + camp.getEndDate());
                writer.newLine();
                writer.write("Registration closes on: " + camp.getClosingDate());
                writer.newLine();
                writer.write("Open to: " + camp.getFacultyRestriction());
                writer.newLine();
                writer.write("Location: " + camp.getLocation());
                writer.newLine();
                writer.write("Available Attendee Slots: " + camp.getRemainingAttendeeSlots() + " / " + camp.getAttendeeSlots());
                writer.newLine();
                writer.write("Committee Size: " + camp.getCommittee().size() + " / " + camp.getCommitteeSlots());
                writer.newLine();
                writer.write("Description: " + camp.getDescription());
                writer.newLine();
                writer.write("Staff-in-Charge: " + camp.getInCharge());
                writer.newLine();
                writer.newLine();  // Add an extra line for separation

                // Create header row for participation details with improved spacing
                writer.write(String.format("%-15s%-20s%-15s%-20s%-10s", "UserID", "Name", "Faculty", "Role", "Points"));
                writer.newLine();

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
                int sortingOption = getSortingOption(scanner);

                List<String> sortedAttendeesAndCommitteeNames = applySortingMethod(sortingOption, attendeesAndCommittee, committee);

                // Populate data rows for sorted attendees and committee members with improved spacing
                for (String userName : sortedAttendeesAndCommitteeNames) {
                    User user = attendeesAndCommittee.stream()
                            .filter(u -> u.getName().equals(userName))
                            .findFirst()
                            .orElse(null);

                    if (user != null) {
                        writer.write(String.format("%-15s%-20s%-15s%-20s%-10d",
                                user.getUserID(),
                                user.getName(),
                                user.getFaculty().toString(),
                                committee.containsKey(user.getUserID()) ? "Committee Member" : "Attendee",
                                committee.getOrDefault(user.getUserID(), 0)));
                        writer.newLine();
                    }
                }

                System.out.println("Camp Participation Report generated successfully. File saved at: " + outputPath);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    /**
     * Gets the user's choice for the sorting method.
     *
     * @return The user's choice for the sorting method.
     */
    private int getSortingOption(Scanner scanner) {
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
    /**
     * Applies the selected sorting method to the list of attendees and committee members.
     *
     * @param sortingOption           The selected sorting method.
     * @param attendeesAndCommittee   The list of attendees and committee members.
     * @param committee               The committee members and their associated points.
     * @return                        The list of sorted attendee and committee member names.
     */
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
