package cams;

import cams.dashboards.Dashboard;
import cams.database.UnifiedCampRepository;
import cams.database.UnifiedUserRepository;
import cams.users.User;

import java.util.Scanner;

public class App {

	//TODO implement this
	public static Boolean initialiseUserData(){
		//IMPLEMENT
        return false;
    }

	public static void main(String[] args) {
		//DECLARE databases
		UnifiedCampRepository unifiedCampRepository = UnifiedCampRepository.getInstance();
		UnifiedUserRepository unifiedUserRepository = UnifiedUserRepository.getInstance();

		// declaring user (staff and students) information from Excel file
		if (unifiedUserRepository == null) { // DECLARE ONCE
			if(!(initialiseUserData())) //IF failed to initialise
				return;
		}

		//Proceed to login page
		Dashboard dashboard = new Dashboard();
		dashboard.startMain();

		//Re-displays the menu until user chooses to quit the App
		while(!dashboard.isQuit()) {
			dashboard.request();
		}

		System.out.println("APP TERMINATED");
	}
}
