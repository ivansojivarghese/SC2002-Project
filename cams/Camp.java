package cams;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import cams.PostTypes.*;

public class Camp extends App {

	private String campName;
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalDate closingDate;
	private String location;
	private int totalSlots = 0;
	private int remainingSlots = 0;
	private String description;
	private String inCharge;
	private Faculty visibility;
	private List<Post> Enquiries;
	private List<Post> Suggestions;
	private List<User> Attendees;
	private List<User> Committee;
	public String getCampName() {
		return campName;
	}

	public void setCampName(String campName) {
		this.campName = campName;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public LocalDate getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(LocalDate closingDate) {
		this.closingDate = closingDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getTotalSlots() {
		return totalSlots;
	}

	public void setTotalSlots(int totalSlots) {
		this.totalSlots = totalSlots;
	}

	public int getRemainingSlots() {
		return remainingSlots;
	}

	public void setRemainingSlots(int remainingSlots) {
		this.remainingSlots = remainingSlots;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getInCharge() {
		return inCharge;
	}

	public void setInCharge(String inCharge) {
		this.inCharge = inCharge;
	}

	public Faculty getVisibility() {
		return visibility;
	}

	public void setVisibility(Faculty visibility) {
		this.visibility = visibility;
	}

	public void setEnquiries(List<Post> enquiries) {
		Enquiries = enquiries;
	}

	public void setSuggestions(List<Post> suggestions) {
		Suggestions = suggestions;
	}

	public List<User> getAttendees() {
		return Attendees;
	}

	public void setAttendees(List<User> attendees) {
		Attendees = attendees;
	}

	public void addAttendee(User attendee) {
		this.Attendees.add(attendee);
	}
	public void removeAttendee(User user){
		this.Attendees.remove(user);
	}

	public List<User> getCommittee() {
		return Committee;
	}

	public void setCommittee(List<User> committee) {
		Committee = committee;
	}


	//always have an empty constructor! otherwise
	public Camp(){}

	//USE A BUILDER INSTEAD
	public Camp(String campName, LocalDate startDate, LocalDate endDate, LocalDate closingDate,
			String location, int slots, List<User> Attendees, List<User> Committee, String description, String inCharge, Faculty visibility) {
		// TODO Auto-generated constructor stub
		this.campName = campName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.closingDate = closingDate;
		this.location = location;
		this.totalSlots = slots;
		this.remainingSlots = slots;
		this.Attendees = Attendees;
		this.Committee = Committee;
		this.description = description; 
		this.inCharge = inCharge;
		this.visibility = visibility;
	}

	public void removePost(PostType postType, Post post){
		switch (postType) {
			case ENQUIRY:
				this.getEnquiries().remove(post);
				break;
			case SUGGESTION:
				this.getSuggestions().remove(post);
				break;
		}
	}

	public void addEnquiry(Post post){
		this.Enquiries.add(post);
	}

	public List<Post> getEnquiries(){
		return this.Enquiries;
	}

	public List<Post> getSuggestions(){
		return this.Suggestions;
	}
	

	
	public void decrSlots() {
		int cur = this.getTotalSlots();
		cur--;
		this.remainingSlots = cur;
	}
	
	public void incrSlots() {
		int cur = this.getTotalSlots();
		cur++;
		this.remainingSlots = cur;
	}

	public boolean checkForMember(String userID) {
		boolean status = false;
		int f = this.getNumMembers();
		for (int i = 0; i < f; i++) {
			if (studentsList[i].userID == userID) {
				status = true;
				break;
			}
		}
		return status;
	}
	
	public int getNumMembers() {
		int count = 0;
		if (this.studentsList != null) {
			int i = 0;
			while (this.studentsList[i] != null) {
				count++;
				i++;
			}
		}
		return count;
	}
	
	public void addMember(Student s) {
		if (this.studentsList == null) {
			this.studentsList = new Student[11]; // make 11 spaces automatically
		}
		if (this.committee == null) {
			this.committee = new Student[10]; // MAX 10
		}
		
		int memNum = this.getNumMembers();
		
		this.studentsList[memNum] = s; // add student in
		System.out.println("You have successfully registered!");
		if (memNum < 10 && !s.isCommittee) {
			this.committee[memNum] = s; // add to committee also
			s.isCommittee = true;
			System.out.println("Due to vacancy, you have been added to the Committee as well.");
		} else {
			System.out.println("You would just be an attendee.");
		}
		
		decrSlots(); // decrement slots
	}
	
	public String checkRole(String userID) {
		String res = "Attendee";
		for (int i = 0; i < this.committee.length; i++) {
			if (userID == committee[i].userID) {
				res = "Committee Member";
				break;
			}
		}
		return res;
	}
	
	public String getMembers(Student[] ar) {
		String res = "";
		if (ar != null) {
			for (int i = 0; i < ar.length; i++) {
				if (ar[i] != null) {
					res += ar[i].userID + ", ";
				}
			}
		} else {
			res = null;
		}
		return res;
	}
	public void display() {
		System.out.println("Name: " + this.campName);
		System.out.println("Dates: " + this.startDate.toString() + " - " + this.endDate.toString());
		System.out.println("Registration closes on: " + this.closingDate.toString());
		System.out.println("Open to: " + this.visibility);
		System.out.println("Location: " + this.location);
		System.out.println("Available Slots: " + this.remainingSlots + " / " + this.totalSlots);
		System.out.println("Committee Size: " + this.getCommittee().size() + " / "); //FIX THIS: need to get size of commitee and remainig slots
		System.out.println("Description: " + this.description);
		System.out.println("Staff-in-Charge: " + this.inCharge);
	}
	@Override
	public String toString() {
	    return "Name: " + this.campName + ", Starting Date: " + this.startDate + ", Ending Date: "
	+ this.endDate + ", Closing Date: " + this.closingDate + ", Open to: "
	    		+ this.visibility + ", Location: " + this.location + ", Total Slots: " + this.totalSlots + ", Remaining Slots: " + this.remainingSlots + ", Committee Members: "
	    		+ this.getCommittee() + ", Student Members: " + this.getAttendees() + ", Description: " + this.description + ", Staff-in-Charge: " + this.inCharge;
	}
}
