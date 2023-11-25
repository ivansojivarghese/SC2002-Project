package cams.util;

import cams.Camp;
import cams.users.User;
import cams.database.UnifiedCampRepository;

import java.time.LocalDate;

//Utility Functions related to Date
public class Date {
    public static boolean doDatesClash(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) {
        return !start1.isAfter(end2) && !end1.isBefore(start2); //returns true if there is an overlap (clash) and false otherwise.
    }
    public static boolean checkClashes(User user, Camp camp){
        UnifiedCampRepository repo = UnifiedCampRepository.getInstance();
        boolean clash = false;
        for (String c: user.getMyCamps()){
            Camp registeredCamp = repo.retrieveCamp(c);
            if(Date.doDatesClash(camp.getStartDate(),camp.getEndDate(), registeredCamp.getStartDate(), registeredCamp.getEndDate())){
                System.out.println("Selected camp clashes with: " + registeredCamp.getCampName());
                clash = true;
            }
        }
        return clash;
    }

/* ALL THESE ARE UNNECESSARY: the LocalDate object already has its own validation function
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

        java.util.Date date = null;
        java.util.Date oldDate = null;
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
            if (!new java.util.Date().before(date)) {
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
                if (!oldDate.before(date) || oldDate.before(new java.util.Date())) { // ensure date is before (some date) and after current date
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
        Scanner sc = InputScanner.getInstance();(System.in);
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
    }*/
}
