package cams;
import cams.Faculty;
import cams.PostTypes.Post;

import java.util.List;
import java.util.Objects;

public abstract class User {
    private String userID;
    private String password;
    private Faculty faculty;
    private List<String> myCamps;

    public Boolean validateLogin(String password){
        return Objects.equals(password, this.password);
    }

    public abstract void viewAllCamps();
    public abstract List<Post> getEnquiries();

    public List<String> getMyCamps() {
        return this.myCamps;
    }

    public void addCamp(Camp camp) {
        this.myCamps.add(camp.campName);
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
