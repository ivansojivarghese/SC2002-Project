package cams.users;
import cams.Camp;
import cams.database.UnifiedCampRepository;
import cams.post_types.Post;
import cams.util.Faculty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class User {
    private String name;
    private String userID;
    private String password;
    private Faculty faculty;
    private List<String> myCamps;

    public Boolean validateLogin(String password){
        return Objects.equals(password, this.password);
    }

    public abstract void viewAllCamps();
    public int displayMyCamps() {
        int index = -1;
        try {
            UnifiedCampRepository repo = UnifiedCampRepository.getInstance();
            for (String c : this.getMyCamps()) {
                System.out.println("_________________________________");
                System.out.println("Index: " + index++);
                Camp camp = repo.retrieveCamp(c);
                camp.display();
            }
        }
        catch (NullPointerException e){
            index = 0;
        }
        return index;
    }
    public abstract List<Post> getSuggestions();
    public abstract List<Post> getEnquiries();

    public User(){}
    public User(String name, String userID, Faculty faculty){
        this.setName(name);
        this.setUserID(userID);
        this.setFaculty(faculty);
        this.setPassword("password");
        this.myCamps = new ArrayList<String>();
    }

    public List<String> getMyCamps() {
        return this.myCamps;
    }

    public void addCamp(Camp camp) {
        this.myCamps.add(camp.getCampName());
    }
    public void removeCamp(String campName){
        this.myCamps.remove(campName);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
    	this.name = name;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        //UserID is always set to uppercase
        this.userID = userID.toUpperCase();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        //Set password without any leading or trailing space to prevent user error
        this.password = password.strip();
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }
}
