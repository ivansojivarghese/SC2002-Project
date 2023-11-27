package cams.users;
import cams.camp.Camp;
import cams.dashboards.DashboardState;
import cams.replier.replier_controller.ReplierController;
import cams.posts.post_entities.Post;
import cams.util.Faculty;
import cams.util.Savable;
import cams.util.SavableObject;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Abstract class representing a user in the system.
 * Subclasses must implement specific functionality for different user types.
 * <p>
 * This class provides common properties and methods for all user types,
 * including basic information such as name, user ID, password, and faculty.
 * It also defines abstract methods for retrieving user-specific data and actions.
 * <p>
 * The class is Serializable and implements Savable to enable saving and loading
 * user objects to and from files using the SavableObject utility.
 *
 * @see Savable
 * @see SavableObject
 * @see Serializable
 */
public abstract class User implements Serializable {
    protected static final Savable savable = new SavableObject();
    @Serial
    private static final long serialVersionUID = 50100974955575555L; //crc32b Hash of "User" converted to ASCII
    protected static final String folderName = "users";
    private String name;
    private String userID;
    private String password;
    private Faculty faculty;
    private List<String> myCamps;

    /**
     * Abstract method to get the dashboard state for the user's menu.
     *
     * @return The DashboardState representing the user's menu.
     */
    public abstract DashboardState getMenuState();
    /**
     * Abstract method to get the filename associated with the user for saving and loading.
     *
     * @return The filename for the user.
     */
    public abstract String getFileName();
    /**
     * Validates the login with the provided password.
     *
     * @param password The password to validate.
     * @return true if the provided password matches the user's password, false otherwise.
     */
    public Boolean validateLogin(String password){
        return Objects.equals(password, this.password);
    }
    /**
     * Abstract method to get a list of post suggestions for the user.
     *
     * @return List of suggested posts.
     */
    public abstract List<Post> getSuggestions();
    /**
     * Abstract method to get a list of post enquiries for the user.
     *
     * @return List of post enquiries.
     */
    public abstract List<Post> getEnquiries();
    /**
     * Abstract method to get the ReplierController associated with the user.
     *
     * @return The ReplierController for the user.
     */
    public abstract ReplierController getReplierController();
    /**
     * Default constructor for the User class.
     */
    public User(){}
    /**
     * Parameterized constructor for the User class.
     *
     * @param name    The name of the user.
     * @param userID  The user ID.
     * @param faculty The faculty associated with the user.
     */
    public User(String name, String userID, Faculty faculty){
        //Must set UserID before all else to ensure proper saving of file
        this.setUserID(userID);
        this.setName(name);
        this.setFaculty(faculty);
        this.setPassword("password");
        this.myCamps = new ArrayList<>();
    }

    /**
     * Gets the list of camps associated with the user.
     *
     * @return The list of camp names.
     */
    public List<String> getMyCamps() {
        return this.myCamps;
    }

    /**
     * Adds a camp to the user's list of associated camps.
     *
     * @param camp The Camp object to add.
     */
    public void addCamp(Camp camp) {
        this.myCamps.add(camp.getCampName());
        savable.saveObject(this, folderName, this.getFileName());
    }

    /**
     * Removes a camp from the user's list of associated camps.
     *
     * @param campName The name of the camp to remove.
     * @return true if the camp was successfully removed, false otherwise.
     */
    public boolean removeCamp(String campName) {
        this.myCamps.remove(campName);
        savable.saveObject(this, folderName, this.getFileName());
        return true;
    }

    /**
     * Gets the name of the user.
     *
     * @return The name of the user.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     *
     * @param name The new name for the user.
     */
    public void setName(String name) {
    	this.name = name;
        savable.saveObject(this, folderName, this.getFileName());
    }

    /**
     * Gets the user ID.
     *
     * @return The user ID.
     */
    public String getUserID() {
        return this.userID;
    }

    /**
     * Sets the user ID. The user ID is always set to uppercase.
     *
     * @param userID The new user ID.
     */
    public void setUserID(String userID) {
        //UserID is always set to uppercase
        this.userID = userID.toUpperCase();
        savable.saveObject(this, folderName, this.getFileName());
    }

    /**
     * Gets the password of the user.
     *
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user. Leading and trailing spaces are removed to prevent user error.
     *
     * @param password The new password for the user.
     */
    public void setPassword(String password) {
        this.password = password.strip();
        savable.saveObject(this, folderName, this.getFileName());
    }

    /**
     * Gets the faculty associated with the user.
     *
     * @return The faculty of the user.
     */
    public Faculty getFaculty() {
        return faculty;
    }

    /**
     * Sets the faculty associated with the user.
     *
     * @param faculty The new faculty for the user.
     */
    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
        savable.saveObject(this, folderName, this.getFileName());
    }

}
