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

		// declaring user (staff and students) information from Excel file
		if (unifiedUserRepository.isEmpty()) { // DECLARE ONCE
			if(!(unifiedUserRepository.initialiseData("staff_list.xlsx", UserType.STAFF) &&
				unifiedUserRepository.initialiseData("student_list.xlsx", UserType.STUDENT)))
				//IF failed to initialise
				return;
		}

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
