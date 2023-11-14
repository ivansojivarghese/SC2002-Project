package cams;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cams.PostTypes.*;
import cams.Camp;
import cams.UnifiedCampRepository;

public class Student extends User { // student class
    //add these attributes must be private****
    public String name;
    public String userID;
    public String faculty;
    protected String password;
    private List<Post> myEnquiries;
    
    public boolean isCommittee = false;
    
    public ArrayList<Integer> joinedCamps = new ArrayList<>();
    
    public String getPassword() {
        return password;
    }

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

    public List<Post> getMyEnquiries(){
        List <Post> myEnquiries = null;
        UnifiedCampRepository repo = UnifiedCampRepository.getInstance();
        for(String campName : this.getMyCamps()){
            Camp camp = repo.retrieveCamp(campName);

            for(Post post: camp.getEnquiries()){
                if(Objects.equals(post.getPostedBy(), this.userID)) {
                    myEnquiries.add(post);
                }
            }
        }
        return myEnquiries;
    }

    public void addEnquiry(Post newEnquiry){
        this.myEnquiries.add(newEnquiry);
    }

    public void viewAllCamps(){

    }

    public void removePost(Post post){
        this.myEnquiries.remove(post);
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
