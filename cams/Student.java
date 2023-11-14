package cams;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cams.PostTypes.*;

public class Student extends User { // student class
    private boolean isCommittee = false;

    /*
    public void setPassword(int identity, String password) {
    	studentArr[identity].password = password;
    }
 
    // Student class constructor
    Student(String name, String userID, String faculty, String password, int identity)
    {
        this.name = name;
        this.userID = userID;
        this.faculty = faculty;
        if (password == null) {        	
        	this.password = "password";
        } else {        	
        	setPassword(identity, password);
        }
    }

    public List<Post> getMyEnquiries(){
        return this.myEnquiries;
    }*/

    public List<Post> getEnquiries(){
        List <Post> myEnquiries = null;
        UnifiedCampRepository repo = UnifiedCampRepository.getInstance();
        for(String campName : this.getMyCamps()){
            Camp camp = repo.retrieveCamp(campName);
            for(Post post: camp.getEnquiries()){
                if(Objects.equals(post.getFirstMessage().getPostedBy(), this.getUserID())) {
                    myEnquiries.add(post);
                }
            }
        }
        return myEnquiries;
    }

    public void viewAllCamps(){

    }
}
/*
class NewPasswordStudent extends Student {
    NewPasswordStudent(String name, String userID, String faculty, String password, int identity) {
		super(name, userID, faculty, password, identity);
		// TODO Auto-generated constructor stub
	}

	public void setPassword(String password) {
    	this.password = password;
    }
}*/
