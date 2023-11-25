package cams.reports;

import cams.Camp;
import cams.database.UnifiedUserRepository;
import cams.users.Student;
import cams.users.Staff;
import cams.users.User;
import cams.util.Faculty;

import java.time.LocalDate;
import java.util.HashMap;

public class test2 {
    public static void main(String[] args) {
        // Create and add users to UnifiedUserRepository
        UnifiedUserRepository userRepository = UnifiedUserRepository.getInstance();

        // Creating users
        User student1 = new Student("Alice Adams", "alice@student.com", Faculty.SCSE);
        User student2 = new Student("Zob Brown", "bob@student.com", Faculty.CCEB);
        User student3 = new Student("Dharlie Clark", "charlie@student.com", Faculty.NBS);
        User staff1 = new Staff("Jaze Doe", "john@staff.com", Faculty.EEE);
        User staff2 = new Staff("Jane Smith", "jane@staff.com", Faculty.NIE);

        // Adding users to UnifiedUserRepository
        userRepository.addUser(student1);
        userRepository.addUser(student2);
        userRepository.addUser(student3);
        userRepository.addUser(staff1);
        userRepository.addUser(staff2);

        // Create a new camp using the Builder pattern
        Camp newCamp = new Camp.Builder()
                .campName("New Camp")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(7))
                .committeeSlots(10)
                .description("Sample Camp Description")
                .inCharge("John Doe")
                .facultyRestriction(Faculty.EEE)
                .visible(true)
                .build();

        // Add committee members to the camp
        HashMap<String, Integer> committeeMembers = new HashMap<>();
        committeeMembers.put(student1.getUserID(), 60);
        committeeMembers.put(student3.getUserID(), 45);
        committeeMembers.put(staff1.getUserID(), 35);

        // Set committee members for the camp
        newCamp.setCommitteeMembers(committeeMembers);

        // Add attendees to the camp using their user IDs
        newCamp.addAttendee(student2.getUserID());
        newCamp.addAttendee(staff2.getUserID());

        // Create a ParticipationReport instance
        ParticipationReport participationReport = new ParticipationReport();

        // Generate the report by passing the Camp instance
        participationReport.generateReport(newCamp);

        // Retrieve and display user information from UnifiedUserRepository
        System.out.println("Retrieving user information from UnifiedUserRepository:");

        // Retrieve users from UnifiedUserRepository using user IDs
        User retrievedStudent1 = userRepository.retrieveUser("alice@student.com");
        User retrievedStudent2 = userRepository.retrieveUser("bob@student.com");
        User retrievedStudent3 = userRepository.retrieveUser("charlie@student.com");
        User retrievedStaff1 = userRepository.retrieveUser("john@staff.com");
        User retrievedStaff2 = userRepository.retrieveUser("jane@staff.com");

        // Display user information
        displayUserInfo("Student 1", retrievedStudent1);
        displayUserInfo("Student 2", retrievedStudent2);
        displayUserInfo("Student 3", retrievedStudent3);
        displayUserInfo("Staff 1", retrievedStaff1);
        displayUserInfo("Staff 2", retrievedStaff2);
    }

    // Helper method to display user information
    private static void displayUserInfo(String label, User user) {
        if (user != null) {
            System.out.println("Retrieved " + label + ": " + user.getName());
        } else {
            System.out.println(label + " not found in UnifiedUserRepository.");
        }
    }
}
