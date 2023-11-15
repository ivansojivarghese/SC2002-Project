package cams;

import java.time.LocalDate;
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
	private List<Post> enquiries;
	private List<Post> suggestions;
	private List<User> attendees;
	private List<User> committee;

	private Camp(Builder builder) {
		this.campName = builder.campName;
		this.startDate = builder.startDate;
		this.endDate = builder.endDate;
		this.closingDate = builder.closingDate;
		this.location = builder.location;
		this.totalSlots = builder.totalSlots;
		this.remainingSlots = builder.remainingSlots;
		this.description = builder.description;
		this.inCharge = builder.inCharge;
		this.visibility = builder.visibility;
		this.enquiries = builder.enquiries;
		this.suggestions = builder.suggestions;
		this.attendees = builder.attendees;
		this.committee = builder.committee;
	}

	// Getters and setters
	public static class Builder {
		private String campName;
		private LocalDate startDate;
		private LocalDate endDate;
		private LocalDate closingDate;
		private String location;
		private int totalSlots;
		private int remainingSlots;
		private String description;
		private String inCharge;
		private Faculty visibility;
		private List<Post> enquiries;
		private List<Post> suggestions;
		private List<User> attendees;
		private List<User> committee;

		public Builder() {
			// Default values can be set here
		}

		public Builder campName(String campName) {
			this.campName = campName;
			return this;
		}

		public Builder startDate(LocalDate startDate) {
			this.startDate = startDate;
			return this;
		}

		public Builder endDate(LocalDate endDate) {
			this.endDate = endDate;
			return this;
		}

		public Builder closingDate(LocalDate closingDate) {
			this.closingDate = closingDate;
			return this;
		}

		public Builder location(String location) {
			this.location = location;
			return this;
		}

		public Builder totalSlots(int totalSlots) {
			this.totalSlots = totalSlots;
			return this;
		}

		public Builder remainingSlots(int remainingSlots) {
			this.remainingSlots = remainingSlots;
			return this;
		}

		public Builder description(String description) {
			this.description = description;
			return this;
		}

		public Builder inCharge(String inCharge) {
			this.inCharge = inCharge;
			return this;
		}

		public Builder visibility(Faculty visibility) {
			this.visibility = visibility;
			return this;
		}

		public Builder enquiries(List<Post> enquiries) {
			this.enquiries = enquiries;
			return this;
		}

		public Builder suggestions(List<Post> suggestions) {
			this.suggestions = suggestions;
			return this;
		}

		public Builder attendees(List<User> attendees) {
			this.attendees = attendees;
			return this;
		}

		public Builder committee(List<User> committee) {
			this.committee = committee;
			return this;
		}

		public Camp build() {
			return new Camp(this);
		}
	}

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
		this.enquiries = enquiries;
	}

	public void setSuggestions(List<Post> suggestions) {
		this.suggestions = suggestions;
	}

	public List<User> getAttendees() {
		return attendees;
	}

	public void setAttendees(List<User> attendees) {
		this.attendees = attendees;
	}

	public void addAttendee(User attendee) {
		this.attendees.add(attendee);
	}
	public void removeAttendee(User user){
		this.attendees.remove(user);
	}

	public List<User> getCommittee() {
		return committee;
	}

	public void setCommittee(List<User> committee) {
		this.committee = committee;
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
		this.attendees = Attendees;
		this.committee = Committee;
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
		this.enquiries.add(post);
	}

	public List<Post> getEnquiries(){
		return this.enquiries;
	}

	public List<Post> getSuggestions(){
		return this.suggestions;
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
