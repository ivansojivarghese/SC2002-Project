package cams;

import cams.PostTypes.Post;

import java.util.List;
import java.util.Objects;

public class Staff extends User {
    //shld all be private!

    public List<Post> getEnquiries() { //COLLECTS all enquiries in Camps created by Staff
        List<Post> myEnquiries = null;
        UnifiedCampRepository repo = UnifiedCampRepository.getInstance();
        for (String campName : this.getMyCamps()) {
            Camp camp = repo.retrieveCamp(campName);
            myEnquiries.addAll(camp.getEnquiries());
        }
        return myEnquiries;
    }

    @Override
    public void viewAllCamps() {

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
}
    // Student class constructor
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
