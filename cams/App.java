package cams;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class App {
	
	static Student[] studentArr;
	static Staff[] staffArr;
	
	// static Camp[] campsArr;
	
	static ArrayList<Object> campsArr = new ArrayList<>();
	
	static int qStatus = 0;
	
	static int[] checkForUser(String input) { // check if USER ID exists (and return its type)
		int r = 0; // outcome?
		int t = 0; // user type?
		int d = 0; // user identity
		int returnArray[] = new int[2]; // return val
		String q = "QUIT";
		
		if (!input.equals(q)) {
			
			for (int i = 0; i < 11; i++) { // student
				
				if (input.equals(studentArr[i].userID)) {
					
					r = 1;
					t = 1;
					returnArray[0] = t;
					returnArray[1] = i;
					break;
				}
			}
			
			if (r == 0) {
				for (int i = 0; i < 5; i++) { // staff
					if (input.equals(staffArr[i].userID)) {
						r = 1;
						t = 2;
						returnArray[0] = t;
						returnArray[1] = i;
						break;
					}
				}
			}
		} else { // IF QUIT
			returnArray[0] = -1;
			returnArray[1] = -1;
			qStatus = 1;
		}
	
		return returnArray;
	}
	
	static int checkFutureDate(int day, int month, int year, int dayFrom, int monthFrom, int yearFrom, boolean range, boolean text) { // check if the date is in the future
		int res = 1;
		
		int monthL = String.valueOf(month).length();
		int dayL = String.valueOf(day).length();
		
		String monthS = null;
		String dayS = null;
		
		if (monthL < 2) {
			monthS = "0" + String.valueOf(month);
		} else {
			monthS = String.valueOf(month);
		}
		
		if (dayL < 2) {
			dayS = "0" + String.valueOf(day);
		} else {
			dayS = String.valueOf(day);
		}
		
		Date date = null; 
		Date oldDate = null;
		try {
			oldDate = new SimpleDateFormat("MM/dd/yyyy").parse(monthS + "/" + dayS + "/" + year);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (dayFrom == 0 || monthFrom == 0 || yearFrom == 0) { // check from today
			try {
				date = new SimpleDateFormat("MM/dd/yyyy").parse(monthS + "/" + dayS + "/" + year);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Date is invalid. Try again.");
				res = 0;
			}
			if (!new Date().before(date)) {
				System.out.println("Date is invalid. Try again.");
				res = 0;
			}
		} else { // check from/to some date in the future
			monthL = String.valueOf(monthFrom).length();
			dayL = String.valueOf(dayFrom).length();
			
			if (monthL < 2) {
				monthS = "0" + String.valueOf(monthFrom);
			} else {
				monthS = String.valueOf(monthFrom);
			}
			
			if (dayL < 2) {
				dayS = "0" + String.valueOf(dayFrom);
			} else {
				dayS = String.valueOf(dayFrom);
			}
			
			try {
				date = new SimpleDateFormat("MM/dd/yyyy").parse(monthS + "/" + dayS + "/" + yearFrom);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Date is invalid. Try again.");
				res = 0;
			}
			if (range) { // DATE FROM
				if (oldDate.before(date)) {
					if (text) {
						System.out.println("Date is invalid. Try again.");
					}
					res = 0;
				}
			} else { // DATE TO
				if (!oldDate.before(date) || oldDate.before(new Date())) { // ensure date is before (some date) and after current date
					if (text) {
						System.out.println("Date is invalid. Try again.");
					}
					res = 0;
				}
			}
		}
		
		if (res == 0) {
			return 0;
		} else {
			return 1;
		}
	}
	
	static int checkFullDate(int day, int month, int year, int dayFrom, int monthFrom, int yearFrom, boolean range) { // check if full date is and valid
		int res = 1;
		
		int monthL = String.valueOf(month).length();
		int dayL = String.valueOf(day).length();
		
		String monthS = null;
		String dayS = null;
		
		if (monthL < 2) {
			monthS = "0" + String.valueOf(month);
		} else {
			monthS = String.valueOf(month);
		}
		
		if (dayL < 2) {
			dayS = "0" + String.valueOf(day);
		} else {
			dayS = String.valueOf(day);
		}
		
		try { // valid date in calendar?
			LocalDate localDate = LocalDate.parse(year + "-" + monthS + "-" + dayS); 
		} catch (Exception e) {
			System.out.println("Date is invalid. Try again.");
			res = 0;
		}
		
		res = checkFutureDate(day, month, year, dayFrom, monthFrom, yearFrom, range, true);
		
		if (res == 0) {
			return 0;
		} else {
			return 1;
		}
	}
	
	static int checkDate(int day, int month) { // check if date combos are valid
		// 28, 29, 30, 31
		
		if (day == 31) {
			switch (month) {
				case 1: // jan
				case 3: // mar
				case 5: // may
				case 7: // jul
				case 8: // aug
				case 10: // oct
				case 12: // dec
					return 1;
			default:
					return 0;
			}
		} else if (day == 30) {
			switch (month) {
				case 4: // apr
				case 6: // jun
				case 9: // sep
				case 11: // nov
				case 1: // jan
				case 3: // mar
				case 5: // may
				case 7: // jul
				case 8: // aug
				case 10: // oct
				case 12: // dec
					return 1;
				default:
					return 0;
			}
		}
		
		return 1;
	}
	
	static int[] enterDate(int dayFrom, int monthFrom, int yearFrom, boolean range) {
		Scanner sc = new Scanner(System.in);
		int day;
		int month;
		int year = 0;
		int status = 0;
		int valid = 0;
		
		int[] date = new int[3];
		
		do {
			do {
				if (status == 1) {
					System.out.println("Please enter a valid value."); // day
				}
				System.out.println("Enter the day (DD): [1 - 31]"); // day
				day = sc.nextInt();
				status = 1;
			} while (day < 1 || day > 31);
			status = 0;
			
			do {
				if (status == 1) {
					System.out.println("Please enter a valid combination."); // day
				}
				System.out.println("Enter the month (MM): [1 - 12]"); // month
				month = sc.nextInt();
				status = 1;
			} while (month < 1 || month > 12 || checkDate(day, month) == 0);
			status = 0;
			
			System.out.println("Enter the year (YYYY):"); // month
			year = sc.nextInt();
			
			if (checkFullDate(day, month, year, dayFrom, monthFrom, yearFrom, range) == 1) {
				valid = 1;
			}
			
		} while (valid == 0);
		
		date[0] = day;
		date[1] = month;
		date[2] = year;
		
		return date;
	}

	public static void main(String[] args) {
		
		do {
			
			// msc.
			Scanner sc = new Scanner(System.in);
			String LogInID;
			String LogInPassword;
			
			// declaring user (staff and students) information
			if (studentArr == null && staffArr == null) { // DECLARE ONCE
				studentArr = new Student[11];
				studentArr[0] = new Student("CHERN", "YCHERN","SCSE", null, 0);
				studentArr[1] = new Student("KOH", "KOH1", "ADM", null, 1);
				studentArr[2] = new Student("BRANDON", "BR015", "EEE", null, 2);
				studentArr[3] = new Student("CALVIN", "CT113", "SCSE", null, 3);
				studentArr[4] = new Student("CHAN", "YCN019", "NBS", null, 4);
				studentArr[5] = new Student("DENISE", "DL007", "SCSE", null, 5);
				studentArr[6] = new Student("DONG", "DON84", "ADM", null, 6);
				studentArr[7] = new Student("ERNEST", "ELI34", "EEE", null, 7);
				studentArr[8] = new Student("LEE", "LE51", "SCSE", null, 8);
				studentArr[9] = new Student("LIU", "SL22", "NBS", null, 9);
				studentArr[10] = new Student("RAWAL", "AKY013", "SSS", null, 10);
				
				staffArr = new Staff[5];
				staffArr[0] = new Staff("Madhukumar", "HUKUMAR", "SCSE", null, 0);
				staffArr[1] = new Staff("Alexei", "OURIN", "ADM", null, 1);
				staffArr[2] = new Staff("Chattopadhyay", "UPAM", "EEE", null, 2);
				staffArr[3] = new Staff("Datta", "ANWIT", "SCSE", null, 3);
				staffArr[4] = new Staff("Arvind", "ARVI", "NBS", null, 4);

			}
			
			// perform login requirements
			System.out.println("Welcome to the Camp Application and Management System (CAMs).");
			System.out.println("We are logging you in.");
			System.out.println("Enter 'QUIT' to quit, or please enter your User ID:");
					
			LogInID = sc.next(); // get USER ID
			
			int userStatus[] = checkForUser(LogInID); // check on USER ID
			
			if (userStatus[0] == 1) { // student
				
				System.out.println("Welcome " + studentArr[userStatus[1]].name + "! Enter your password:");
				
				LogInPassword = sc.next(); // get password
				if (LogInPassword.equals(studentArr[userStatus[1]].password)) { // if password matches
					
					// GO TO DASHBOARD
					Dashboard dashboard = new Dashboard(userStatus[1], studentArr[userStatus[1]].name, studentArr[userStatus[1]].userID, studentArr[userStatus[1]].faculty, "Student");
					
				} else {
					System.out.println("Wrong password!");
				}
				
			} else if (userStatus[0] == 2) { // staff
				
				System.out.println("Welcome " + staffArr[userStatus[1]].name + "! Enter your password:");
				LogInPassword = sc.next(); // get password
				if (LogInPassword.equals(staffArr[userStatus[1]].password)) {
					
					// GO TO DASHBOARD
					Dashboard dashboard = new Dashboard(userStatus[1], staffArr[userStatus[1]].name, staffArr[userStatus[1]].userID, staffArr[userStatus[1]].faculty, "Staff");
					
				} else {
					System.out.println("Wrong password!");
				}
				
			} else if (userStatus[0] == 0) { // does not exist
				
				System.out.println("This User ID does not exist.");
				
			} else if (userStatus[0] == -1) { // TERMINATION
				System.out.println("Program terminated.");
			}
			
		} while(qStatus == 0);
	} 
}
