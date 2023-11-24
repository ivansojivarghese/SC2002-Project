package cams;

import cams.dashboards.Dashboard;
import cams.database.UnifiedCampRepository;
import cams.database.UnifiedUserRepository;
import cams.util.UserType;

public class App {
	public static void main(String[] args) {
		
		//DECLARE databases
		UnifiedCampRepository unifiedCampRepository = UnifiedCampRepository.getInstance();
		UnifiedUserRepository unifiedUserRepository = UnifiedUserRepository.getInstance();

		System.out.println("Initialising user data files...");
		// If failed to initialise serializable data and running repository is empty
		if(!unifiedUserRepository.initialiseData() && unifiedUserRepository.isEmpty()) {
			System.out.println("User data files not found, creating data files...");
			// THEN DECLARE user (staff and students) information from Excel file ONCE
			if (!(unifiedUserRepository.initialiseData("staff_list.xlsx", UserType.STAFF) &&
					unifiedUserRepository.initialiseData("student_list.xlsx", UserType.STUDENT))) {
				//IF failed to initialise
				System.out.println("Failed to create user data files!");
				return;
			}
		}
		System.out.println("Initialising camp data files...");
		if(!unifiedCampRepository.initialiseData() && unifiedCampRepository.isEmpty())
			System.out.println("No camps have been created.");

		//Proceed to login page
		Dashboard dashboard = new Dashboard();
		dashboard.startMain();

		while(!dashboard.isQuit()) {
			System.out.println("______________________________________________________________");
			dashboard.request();
		}

		System.out.println("APP TERMINATED");
	}
}
