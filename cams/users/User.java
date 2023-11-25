package cams.users;
import cams.Camp;
import cams.dashboards.DashboardState;
import cams.database.UnifiedCampRepository;
import cams.post_types.Post;
import cams.util.Faculty;
import cams.util.Savable;
import cams.util.SavableObject;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class User implements Serializable {
    protected static final Savable savable = new SavableObject();
    @Serial
    private static final long serialVersionUID = 50100974955575555L; //crc32b Hash of "User" converted to ASCII
    private static final String folderName = "users";
    private String name;
    private String userID;
    private String password;
    private Faculty faculty;
    private List<String> myCamps;

    public abstract DashboardState getMenuState();
    public abstract String getFileName();

    public Boolean validateLogin(String password){
        return Objects.equals(password, this.password);
    }

    public abstract int viewAllCamps();
    public int displayMyCamps() {
        int index = 0;

        UnifiedCampRepository repo = UnifiedCampRepository.getInstance();
        if(this.getMyCamps() == null)
            throw new NullPointerException("No camps to display");
        for (String c : this.getMyCamps()) {
            System.out.println("_________________________________");
            System.out.println("Index: " + index++);
            Camp camp = repo.retrieveCamp(c);
            camp.display();
        }
        return index;
    }
    public abstract List<Post> getSuggestions();
    public abstract List<Post> getEnquiries();

    public User(){}
    public User(String name, String userID, Faculty faculty){
        //Must set UserID before all else to ensure proper saving of file
        this.setUserID(userID);
        this.setName(name);
        this.setFaculty(faculty);
        this.setPassword("password");
        this.myCamps = new ArrayList<>();
    }

    public List<String> getMyCamps() {
        return this.myCamps;
    }

    public void addCamp(Camp camp) {
        this.myCamps.add(camp.getCampName());
        savable.saveObject(this, folderName, this.getFileName());
    }
    public boolean removeCamp(String campName){
        this.myCamps.remove(campName);
        savable.saveObject(this, folderName, this.getFileName());
        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
    	this.name = name;
        savable.saveObject(this, folderName, this.getFileName());
    }

    public String getUserID() {
        return this.userID;
    }

    public void setUserID(String userID) {
        //UserID is always set to uppercase
        this.userID = userID.toUpperCase();
        savable.saveObject(this, folderName, this.getFileName());
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        //Set password without any leading or trailing space to prevent user error
        this.password = password.strip();
        savable.saveObject(this, folderName, this.getFileName());
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
        savable.saveObject(this, folderName, this.getFileName());
    }
}
