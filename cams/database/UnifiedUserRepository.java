package cams.database;
import cams.users.Staff;
import cams.users.Student;
import cams.users.User;
import cams.util.Faculty;
import cams.util.UserType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class UnifiedUserRepository {
    private static UnifiedUserRepository instance;
    private HashMap<String, User> users;

    //prevent construction outside the class
    private UnifiedUserRepository(){
        this.users = new HashMap<String, User>();
    }

    public void addUser(User user){
        this.users.put(user.getUserID(), user);
    }
    public User retrieveUser(String userID){
        return users.get(userID);
    }
    
    public boolean isEmpty() {
    	if (this.users != null) {
    		return this.users.isEmpty();
    	} else {
    		return true;
    	}
    }

    public static UnifiedUserRepository getInstance() {
        // If the instance is null, create a new one
        if (instance == null) {
            instance = new UnifiedUserRepository();
        }
        // Return the existing/new instance
        return instance;
    }

    public boolean intialiseData(String filename, UserType userType) {
        ArrayList<User> data = new ArrayList<>();

        try {
            File file = new File(System.getProperty("user.dir") + "\\cams\\util\\" + filename);
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header row

                String name = "", email = "", userID = "";
                Faculty faculty = null;

                for (Cell cell : row) {
                    String cellValue = cell.getStringCellValue();
                    if (cellValue.isEmpty()) continue;

                    switch (cell.getColumnIndex()) {
                        case 0:
                            name = cellValue;
                            break;
                        case 1:
                            email = cellValue;
                            userID = email.substring(0, email.indexOf("@"));
                            break;
                        case 2:
                            faculty = Faculty.valueOf(cellValue);
                            break;
                    }
                }

                if (userID.isEmpty()) continue;
                switch (userType) {
                    case STUDENT:
                        this.addUser(new Student(name, userID, faculty)); // Student constructor
                        break;
                    case STAFF:
                        this.addUser(new Staff(name, userID, faculty)); // Staff constructor
                        break;
                }
            }

            wb.close();
        } catch (Exception e){
            System.out.println("Error in initialising user data files.");
            return false;
        }
        return true;
    }

}
