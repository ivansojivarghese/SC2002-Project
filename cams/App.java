package cams;

import cams.dashboards.Dashboard;
import cams.database.UnifiedCampRepository;
import cams.database.UnifiedUserRepository;
import cams.users.User;
import cams.util.ReadExcelFile;

import java.util.Scanner;

public class App {
	public static Boolean initialiseUserData(){
		//IMPLEMENT
		
        return false;
    }

	public static void main(String[] args) {
		//DECLARE databases
		UnifiedCampRepository unifiedCampRepository = UnifiedCampRepository.getInstance();
		UnifiedUserRepository unifiedUserRepository = UnifiedUserRepository.getInstance();

		// declaring user (staff and students) information from Excel file
		if (unifiedUserRepository.isEmpty()) { // DECLARE ONCE
			
			ReadExcelFile.updateFromFile("staff_list");
			
			ReadExcelFile.updateFromFile("student_list");
			
			// unifiedUserRepository.addUser("ac");
			
			// go to staff list
			// loop through all names and add to this
			
			// students list
			// loop through all
			
			if(!(initialiseUserData())) //IF failed to initialise
				return;
		}

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
