package cams.users;

import cams.Camp;
import cams.post_types.Post;
import cams.util.Faculty;
import cams.database.UnifiedCampRepository;

import java.util.List;

public class Staff extends User {

    public List<Post> getEnquiries() { //COLLECTS all enquiries in Camps created by Staff
        List<Post> myEnquiries = null;
        UnifiedCampRepository repo = UnifiedCampRepository.getInstance();
        for (String campName : this.getMyCamps()) {
            Camp camp = repo.retrieveCamp(campName);
            myEnquiries.addAll(camp.getEnquiries());
        }
        return myEnquiries;
    }

    //TODO implement this method to return all camps
    @Override
    public List<Camp> viewAllCamps() {
        List<Camp> collection = null;
        return collection;
    }

    @Override
    public List<Post> getSuggestions() {
        List<Post> mySuggestions = null;
        UnifiedCampRepository repo = UnifiedCampRepository.getInstance();
        for (String campName : this.getMyCamps()) {
            Camp camp = repo.retrieveCamp(campName);
            mySuggestions.addAll(camp.getSuggestions());
        }
        return mySuggestions;
    }
    public void displayMyCamps(){
        UnifiedCampRepository repo = UnifiedCampRepository.getInstance();
        for(String c: this.getMyCamps()){
            Camp camp = repo.retrieveCamp(c);
            camp.display();
            System.out.println("______________");
        }
    }
    
    public Staff(String name, String userID, String faculty) {
    	this.setName(name);
    	this.setUserID(userID);   
    	if (faculty != "") {
	    	this.setFaculty(Faculty.valueOf(faculty));
    	}
    }

}
    // Student class constructor
//Dont need this. new password can be set using user.setPassword().
    /*
    Staff(String name, String userID, String faculty, String password, int identity)
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
}

class NewPasswordStaff extends Staff {
    NewPasswordStaff(String name, String userID, String faculty, String password, int identity) {
		super(name, userID, faculty, password, identity);
		// TODO Auto-generated constructor stub
	}

	public void setPassword(String password) {
    	this.password = password;
    }
}*/
