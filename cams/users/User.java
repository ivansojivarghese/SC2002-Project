package cams.users;
import cams.Camp;
import cams.post_types.Post;
import cams.util.Faculty;

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

    public abstract List<Camp> viewAllCamps();
    public abstract void displayMyCamps();
    public abstract List<Post> getSuggestions();
    public abstract List<Post> getEnquiries();

    public List<String> getMyCamps() {
        return this.myCamps;
    }

    public void addCamp(Camp camp) {
        this.myCamps.add(camp.getCampName());
    }
    public void removeCamp(String campName){
        this.myCamps.remove(campName);
    }
    
    public void setName(String name) {
    	this.name = name;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }
}
