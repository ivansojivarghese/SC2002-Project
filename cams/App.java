package cams;

import cams.dashboards.Dashboard;
import cams.database.UnifiedCampRepository;
import cams.database.UnifiedUserRepository;
import cams.users.User;
import cams.util.ReadExcelFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class App {
	public static Boolean initialiseUserData(){
		
		if (!UnifiedUserRepository.getInstance().isEmpty()) {
			return true;
		}
		
        return false;
    }

	public static void main(String[] args) {
		
		// final HashMap<String, User> staff;
		
		//DECLARE databases
		UnifiedCampRepository unifiedCampRepository = UnifiedCampRepository.getInstance();
		UnifiedUserRepository unifiedUserRepository = UnifiedUserRepository.getInstance();

		// declaring user (staff and students) information from Excel file
		if (unifiedUserRepository.isEmpty()) { // DECLARE ONCE
			
			System.out.println("wow");
			
			ArrayList<User> staff = null;
			ArrayList<User> student = null;
			staff = ReadExcelFile.updateFromFile("staff_list.xlsx"); // return a stack of user class objects
			student = ReadExcelFile.updateFromFile("student_list.xlsx"); // return a stack of user class objects
			
			for (int i = 0; i < staff.size() - 1; i++) {
				unifiedUserRepository.addUser(staff.get(i)); // add each staff user object into repository
			}
			for (int j = 0; j < student.size() - 1; j++) {
				unifiedUserRepository.addUser(student.get(j)); // add each student user object into repository
			}
			
			if(!(initialiseUserData())) //IF failed to initialise
				
				return;
		}
		
		System.out.println("wow");

		//Proceed to login page
		Dashboard dashboard = new Dashboard();
		dashboard.startMain();

		while(!dashboard.isQuit()) {
			dashboard.request();
		}

		System.out.println("APP TERMINATED");
/*
		if (authenticatedUser instanceof Student) { // student
			// GO TO DASHBOARD
			System.out.println("Welcome STUDENT " + authenticatedUser.getUserID() + "!");
			Dashboard dashboard = new Dashboard(userStatus[1], studentArr[userStatus[1]].name, studentArr[userStatus[1]].userID, studentArr[userStatus[1]].faculty, "Student");
		}

		else if (authenticatedUser instanceof Staff) { // staff
			System.out.println("Welcome STAFF " + authenticatedUser.getUserID() + "!");
			// GO TO DASHBOARD
			Dashboard dashboard = new Dashboard(userStatus[1], staffArr[userStatus[1]].name, staffArr[userStatus[1]].userID, staffArr[userStatus[1]].faculty, "Staff");
		}

		else { // does not exist
			System.out.println("User does not exist.");
		}*/
	}
}
