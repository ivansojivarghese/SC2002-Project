package cams.users;

import cams.Camp;
import cams.post_types.Post;
import cams.util.Faculty;
import cams.database.UnifiedCampRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Staff extends User {

    public Staff() {}

    public Staff(String name, String userID, Faculty faculty) {
        super(name, userID, faculty);
        this.setPassword("password");
    }

    public List<Post> getEnquiries() { //COLLECTS all enquiries in Camps created by Staff
        List<Post> myEnquiries = new ArrayList<>();
        try{
            UnifiedCampRepository repo = UnifiedCampRepository.getInstance();
            for (String campName : this.getMyCamps()) {
                Camp camp = repo.retrieveCamp(campName);
                myEnquiries.addAll(camp.getEnquiries());
            }
        }
        catch (NullPointerException e){
                System.out.println("No camp to retrieve suggestions from.");
        }
        return myEnquiries;
    }

    //TODO implement this method to return all camps
    @Override
    public void viewAllCamps() {
        UnifiedCampRepository repo = UnifiedCampRepository.getInstance();
        for(Camp c : repo.allCamps()){
            System.out.println("_________________________________");
            c.display();
        }
    }

    @Override
    public List<Post> getSuggestions() {
        List<Post> mySuggestions = new ArrayList<>();
        try{
            UnifiedCampRepository repo = UnifiedCampRepository.getInstance();
            for (String campName : this.getMyCamps()) {
                Camp camp = repo.retrieveCamp(campName);
                mySuggestions.addAll(camp.getSuggestions());
            }
        }
        catch (NullPointerException e){
            System.out.println("No camp to retrieve suggestions from.");
        }
        return mySuggestions;
    }
    public void displayMyCamps(){
        try{
            UnifiedCampRepository repo = UnifiedCampRepository.getInstance();
            System.out.println("My Created Camps: ");
            for(String c: this.getMyCamps()){
                System.out.println("_________________________________");
                Camp camp = repo.retrieveCamp(c);
                camp.display();
            }
        } catch (NullPointerException e){
            System.out.println("No camps created.");
        }
    }
    
    public void editMyCamps() {
    	Scanner sc = new Scanner(System.in);
    	int listCount = 1;
    	int index, option, track = 0;
    	boolean error = false;
    	String targetName = null;
    	if (this.getMyCamps() != null) {
	    	try{
	            UnifiedCampRepository repo = UnifiedCampRepository.getInstance();
	            System.out.println("Choose a Camp to edit. Make the selection through its index number:");
	            for(String c: this.getMyCamps()){
	                System.out.println("_________________________________");
	                System.out.println("Index: " + listCount);
	                Camp camp = repo.retrieveCamp(c);
	                camp.display();
	                listCount++;
	            }
	            index = sc.nextInt();
	            for(String c: this.getMyCamps()){ // get name of targeted camp
	                if (track < index) {
	                	Camp camp = repo.retrieveCamp(c);
	                	targetName = camp.getCampName();
	                	track++;
	                } else {
	                	break;
	                }
	            }
	            System.out.println("Camp name: " + targetName);
	            System.out.println("Do you want to:");
	            System.out.println("[1] Toggle its visibility?");
	            System.out.println("[2] Delete it?");
	            
	            do {
		            option = sc.nextInt();
		            
		            switch (option) {
			            case 1: // toggle
			            	Faculty inverse = null;
			            	if (repo.retrieveCamp(targetName).getVisibility() != Faculty.NULL) {
			            		inverse = Faculty.NULL;
			            	} else {
			            		inverse = repo.retrieveCamp(targetName).getOriginalVisibility();
			            	}
			            	System.out.println("This Camp is currently open to " + repo.retrieveCamp(targetName).getVisibility() + ".");
			            	
			            	repo.retrieveCamp(targetName).setVisibility(inverse);
			            	
			            	System.out.println("Proceeding to change visibility to " + inverse + ".");
	
			            break;
			            case 2: // deletion
			            	repo.deleteCamp(targetName);
			            	System.out.println("This Camp has been deleted.");
			            break;
			            default:
			            	error = true;
			            break;
		            }
	            } while (error);
	            
	        } catch (NullPointerException e){
	            System.out.println("No camps created.");
	        }
    	} else {
    		System.out.println("No camps created.");
    	}
    }
}
