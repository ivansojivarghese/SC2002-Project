package cams.database;
import cams.users.Staff;
import cams.users.Student;
import cams.users.User;
import cams.users.UserRepository;
import cams.util.Faculty;
import cams.util.Loader;
import cams.util.ObjectLoader;
import cams.util.UserType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.Serializable;
import java.util.HashMap;
/**
 * Implements {@link UserRepository} as a Singleton to manage and store User objects.
 * Provides functionalities to add, retrieve, and query users.
 */
public class UnifiedUserRepository implements UserRepository {
    private static UnifiedUserRepository instance;
    private static final Loader loader = new ObjectLoader();
    private HashMap<String, User> users;
    private static final String fileLocation = System.getProperty("user.dir") + File.separator + "resources" + File.separator + "users";

    /**
     * Private constructor to prevent instantiation outside the class.
     */
    private UnifiedUserRepository(){
        this.users = new HashMap<>();
    }

    /**
     * Provides a global point of access to the {@link UnifiedUserRepository} instance.
     *
     * @return The singleton instance of {@link UnifiedUserRepository}.
     */
    public static UnifiedUserRepository getInstance() {
        // If the instance is null, create a new one
        if (instance == null) {
            instance = new UnifiedUserRepository();
        }
        // Return the existing/new instance
        return instance;
    }

    /**
     * Adds a new user to the repository.
     *
     * @param user The {@link User} object to add.
     */
    @Override
    public void addUser(User user){
        this.users.put(user.getUserID(), user);
    }

    /**
     * Retrieves a user by their userID.
     * <p>
     * UserIDs are treated as case-insensitive for retrieval purposes.
     *
     * @param userID The userID of the user to retrieve.
     * @return The {@link User} object if found, null otherwise.
     */
    @Override
    //UserID is always uppercase to remove case sensitivity
    public User retrieveUser(String userID){
        return users.get(userID.toUpperCase());
    }

    /**
     * Checks if the repository is empty.
     *
     * @return true if the repository contains no users, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return this.users.isEmpty();
    }

    /**
     * Initializes the repository data from stored files.
     * <p>
     * Loads User objects from serialized files located in a specified directory.
     *
     * @return true if data initialization is successful, false otherwise.
     */
    public boolean initialiseData(){
        File folder = new File(fileLocation);
        File[] listOfFiles = folder.listFiles();
        //If folder cannot be accessed return false
        if (listOfFiles == null) {
            System.out.println("Error: Unable to access user database.");
            return false;
        }
        //If there are no serializable objects to load return false
        if (listOfFiles.length == 0) {
            return false;
        }
        //If there are serializable objects to load
        //Iterate through each file in the folder and load it into the repository
        for (File file : listOfFiles) {
            if (file.isFile()) {
                Serializable object;
                try {
                    object = loader.loadObject(file.getAbsolutePath());
                } catch (Exception e) {
                    System.out.println("Error loading object from file: " + file.getName());
                    continue;
                }

                if (!(object instanceof User)) {
                    System.out.println("Object loaded from file is not of type User, skipping object.");
                    continue;
                }

                User user = (User) object;
                this.users.put(user.getUserID(), user);
            }
        }
        return true;
    }


    /**
     * Initializes user objects from Excel files and automatically serializes the objects.
     * <p>
     * Reads user data from an Excel file and adds the users to the repository based on the specified user type.
     *
     * @param filename The filename of the Excel file containing user data.
     * @param userType The type of users to be added (STUDENT or STAFF).
     * @return true if data initialization is successful, false otherwise.
     */
    public boolean initialiseData(String filename, UserType userType) {
        try {
            File file = new File(System.getProperty("user.dir") + File.separator + "cams" + File.separator + "util" + File.separator + filename);
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
                        case 0 -> name = cellValue;
                        case 1 -> {
                            email = cellValue;
                            userID = email.substring(0, email.indexOf("@"));
                        }
                        case 2 -> faculty = Faculty.valueOf(cellValue);
                    }
                }

                if (userID.isEmpty()) continue;
                switch (userType) {
                    case STUDENT -> this.addUser(new Student(name, userID, faculty)); // Student constructor
                    case STAFF -> this.addUser(new Staff(name, userID, faculty)); // Staff constructor
                }
            }

            wb.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("User data files not found, unable to initialise user database.");
            return false;
        }
        catch (Exception e){
            System.out.println("Unknown error in initialising user data files.");
            return false;
        }
        return true;
    }
}
