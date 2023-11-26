package cams.reports;

import cams.camp.Camp;
import cams.database.UnifiedUserRepository;
import cams.users.Student;
import cams.users.Staff;
import cams.users.User;
import cams.util.Faculty;

import java.time.LocalDate;
import java.util.HashMap;

public class test {
    public static void main(String[] args) {
        // Create and add users to UnifiedUserRepository
        UnifiedUserRepository userRepository = UnifiedUserRepository.getInstance();

        // Creating users
        User student1 = new Student("Alice Adams", "alice@student.com", Faculty.SCSE);
        User student2 = new Student("Bob Brown", "bob@student.com", Faculty.CCEB);
        User student3 = new Student("Charlie Clark", "charlie@student.com", Faculty.SCSE);
        User staff1 = new Staff("John Doe", "john@staff.com", Faculty.CCEB);
        User staff2 = new Staff("Jane Smith", "jane@staff.com", Faculty.NBS);
        User student4 = new Student("David Davis", "david@student.com", Faculty.MSE);
        User staff3 = new Staff("Eva Evans", "eva@staff.com", Faculty.WKWSCI);

        // Adding users to UnifiedUserRepository
        userRepository.addUser(student1);
        userRepository.addUser(student2);
        userRepository.addUser(student3);
        userRepository.addUser(staff1);
        userRepository.addUser(staff2);
        userRepository.addUser(student4);
        userRepository.addUser(staff3);

        // Create a new camp using the Builder pattern
        Camp newCamp = new Camp.Builder()
                .campName("New Camp")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(7))
                .committeeSlots(5)  // Set the number of committee slots to 5
                .description("Sample Camp Description")
                .inCharge("John Doe")
                .facultyRestriction(Faculty.EEE)
                .visible(true)
                .build();

        // Add committee members to the camp
        HashMap<String, Integer> committeeMembers = new HashMap<>();
        committeeMembers.put(student1.getUserID(), 60);
        committeeMembers.put(student2.getUserID(), 45);
        committeeMembers.put(student3.getUserID(), 35);
        committeeMembers.put(staff1.getUserID(), 50);
        committeeMembers.put(staff2.getUserID(), 40);

        // Set committee members for the camp
        newCamp.setCommitteeMembers(committeeMembers);

        // Create a PerformanceReport instance
        PerformanceReport performanceReport = new PerformanceReport();

        // Generate the report by passing the Camp instance
        performanceReport.generateReport(newCamp);
    }
}
