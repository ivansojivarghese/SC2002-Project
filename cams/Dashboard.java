package cams;

import java.util.Scanner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Dashboard {
	//> FACULTY INFO.
	//> CHANGE PASSWORD
	//> LOGOUT (BACK TO LOG IN PAGE)
	private DashboardState currentState;
	private User authenticatedUser;

	public Dashboard() {
		// Initially, the dashboard might be in the options state
		this.currentState = new LogoutState();
	}

	public User getAuthenticatedUser() {
		return authenticatedUser;
	}

	public void setAuthenticatedUser(User authenticatedUser) {
		this.authenticatedUser = authenticatedUser;
	}

	public void setState(DashboardState state) {
		this.currentState = state;
	}

	public void request() {
		currentState.display(this);
	}

	// Method to simulate logout
	public void logout() {
		System.out.println("Logging out...");
		setState(new LogoutState());
		request();
	}
	//Method to start main menu
	public void startMain() {
		setState(new LogoutState());
		request();
	}
}
/*
	Scanner sc = new Scanner(System.in);
	int option;
	int dStatus = 0;
	
	Dashboard(int identity, String name, String userID, String faculty, String type) {
		
		do {
		
			System.out.println("-- DASHBOARD --");
			if (type == "Staff") {
				System.out.println("Name: " + name + ", Faculty: " + faculty + ", Type: " + type);
			} else if (type == "Student") {
				System.out.println("Name: " + name + ", Faculty: " + faculty + ", Type: " + type + ", Committee Member: " + studentArr[identity].isCommittee);
			}
			System.out.println("-- OPTIONS --");
			System.out.println("(1) Change your password");
			System.out.println("(2) Logout");
			if (type == "Staff") {
				System.out.println("(3) View all Camps");
				System.out.println("(4) Edit your Camp(s)");
				System.out.println("(5) Create a new Camp");
			} else if (type == "Student") {
				System.out.println("(3) Register for a Camp");
				System.out.println("(4) View your registered Camps");
				System.out.println("(5) Withdraw from a Camp");
			}
			option = sc.nextInt();
			
			if (option == 1) { // change password
				String newPassword;
				System.out.println("Enter new password:");
				newPassword = sc.next();

				UnifiedUserRepository repo = UnifiedUserRepository.getInstance();
				User user = repo.retrieveUser(userID);
				user.setPassword(newPassword);
				
				System.out.println("Password successfully changed!");
				
				// BACK TO DASHBOARD				
				qStatus = 1;
				
			} else if (option == 2) { // logout
				
				System.out.println("You have logged out.");
				
				// BACK TO LOGIN			
				// String[] args = new String[0]; // Or String[] args = {};
				// main(args); // RESTART main function
				dStatus = 1;
				qStatus = 0;
				
			} else if (type == "Staff") {
			
				if (option == 3) { // VIEW all camps
					
					if (campsArr.size() != 0) {
						System.out.println("A list of all existing Camps:");
						for(Object campName:campsArr) {
				            System.out.println(campName);
				        }
					} else {
						System.out.println("No camps have been created by anyone.");
					}
					
				} else if (option == 4) { // Create a new camp
					
					SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
					
					int value;
					int visible;
					String campName;
					int[] startDate;
					int[] endDate;
					int[] closingDate;
					String userGroup = null;
					String location;
					int slots = 0;
					Student[] studentsList = null;
					Student[] committee = null;
					String description;
					String inCharge = userID;
					boolean visibility = false;
					
					System.out.println("You are creating a new Camp.");
					System.out.println("What is its name?");
					campName = sc.next();
					System.out.println("On which date is it starting?");
					
					startDate = enterDate(0, 0, 0, true);
					
					System.out.println("On which date is it ending?");
					
					endDate = enterDate(startDate[0], startDate[1], startDate[2], true); // HAS to be after startDATE
					
					System.out.println("On which date is the registration closing?");
					
					closingDate = enterDate(startDate[0], startDate[1], startDate[2], false); // HAS TO be before startDATE
					
					do {
						System.out.println("Is this open to the whole of NTU (1), or just only to your faculty? (2)");
						value = sc.nextInt();
						if (value == 1) {
							userGroup = "NTU";
						} else if (value == 2) {
							userGroup = faculty;
						}
					} while (value != 1 && value != 2);
					
					System.out.println("Where is its location? (Leave no spacings)");
					
					location = sc.next();
					
					System.out.println("How many slots are available?");
					
					slots = sc.nextInt();
					
					System.out.println("Please provide a description. (Leave no spacings)");
					
					description = sc.next();
					
					do {
						System.out.println("Should the Camp be visible? [1: YES, 0: NO]");
						visible = sc.nextInt();
						if (visible == 1) {
							visibility = true;
						} else if (visible == 0) {
							visibility = false;
						}
					} while (visible != 1 && visible != 0);
					
					campsArr.add(new Camp(campName, startDate, endDate, closingDate, userGroup, location, slots, studentsList, committee, description, inCharge, visibility));
					
					System.out.println("Camp created successfully!");
					
				} else if (option == 5) { // Edit your camp(s)
					
					int count = 0;
					int list = 1;
					
					int selection = 0;
					
					for (Object inCharge:campsArr) {
						if (inCharge.toString().contains(userID)) {
							count++;
						}
					}
					
					if (count != 0) {
						System.out.println("These are your Camps. Select a Camp (to edit) by entering its index number:");
						for (Object b: campsArr) {
							if (b.toString().contains(userID)) {
								System.out.println(list + " : " + b.toString() + "\n");
								list++;
							}
						}
						
						do {
							selection = sc.nextInt();
							int option;
							
							System.out.println("Camp Name: " + ((Camp) campsArr.get(selection - 1)).getName());
							System.out.println("--++ OPTIONS ++--");
							System.out.println("(1) Toggle visibility.");
							System.out.println("(2) Delete.");
							
							option = sc.nextInt();
							
							switch (option) {
							case 1: // change visibility
								
								boolean oldVisibility = ((Camp) campsArr.get(selection - 1)).getVisibility(); // get current visibility
								boolean newVisibility = !oldVisibility; // reverse
								
								((Camp) campsArr.get(selection - 1)).updateVisibility(newVisibility); // update visibility
								
								System.out.println("Visibility updated!");
								
								
							break;
							case 2: // delete
								campsArr.remove(campsArr.get(selection - 1));
								
								System.out.println("Camp has been deleted.");
								
							break;
							}
						} while (option != 1 && option != 2);
						
					} else {
						System.out.println("You have not created any Camps.");
					}
					
				} else { // invalid
					
					// TRY AGAIN
					System.out.println("Invalid option!");
					
					// BACK TO DASHBOARD
					qStatus = 1;
				}
				
			} else if (type == "Student") {
				
				if (option == 3) { // REGISTER
					
					// FILTERING

					
				} else if (option == 4) { // view registered camps
					
					int list = 1;
					
					for (int i = 0; i < studentArr[identity].joinedCamps.size(); i++) {
						String role = ((Camp) campsArr.get(studentArr[identity].joinedCamps.get(i))).checkRole(userID);
						
						System.out.println(list + " : " + " Role : " + role + ", " + campsArr.get(studentArr[identity].joinedCamps.get(i)));
						list++;
					}
					
					if (list == 1) {
						System.out.println("You have not registered for any Camps.");
					}
					
				} else if (option == 5) { // withdraw FROM A CAMP
				
					int list = 1;
					int option;
					
					for (int i = 0; i < studentArr[identity].joinedCamps.size(); i++) {
						String role = ((Camp) campsArr.get(studentArr[identity].joinedCamps.get(i))).checkRole(userID);
						
						if (role == "Attendee") {
							System.out.println(list + " : " + " Role : " + role + ", " + campsArr.get(studentArr[identity].joinedCamps.get(i)));
							list++;
						}
					}
					
					if (list == 1) {
						System.out.println("Sorry, you do not have any Camps to withdraw from.");
					} else {
						System.out.println("Choose the index of the Camp you would like to withdraw from:");
						
						option = sc.nextInt();
					}
					
				} else {
					// TRY AGAIN
					System.out.println("Invalid option!");
					
					// BACK TO DASHBOARD
					qStatus = 1;
				}
			}
			
		} while (dStatus == 0);
	}
}*/
