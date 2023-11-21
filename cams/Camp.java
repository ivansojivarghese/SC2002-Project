package cams;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import cams.post_types.*;
import cams.util.Faculty;

public class Camp {
	private String campName;
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalDate closingDate;
	private String location;
	private int attendeeSlots = 0;
	private int committeeSlots = 10;
	private String description;
	private String inCharge;
	private Faculty visibility;
	private List<Post> enquiries;
	private List<Post> suggestions;
	private List<String> attendees;
	private HashMap<String, Integer> committee;

	//Best practice to always have an empty constructor
	public Camp(){}

	//Camp class has complex construction so using a builder provides better readability
	private Camp(Builder builder) {
		this.campName = builder.campName;
		this.startDate = builder.startDate;
		this.endDate = builder.endDate;
		this.closingDate = builder.closingDate;
		this.location = builder.location;
		this.attendeeSlots = builder.attendeeSlots;
		this.committeeSlots = builder.committeeSlots;
		this.description = builder.description;
		this.inCharge = builder.inCharge;
		this.visibility = builder.visibility;
		this.enquiries = builder.enquiries;
		this.suggestions = builder.suggestions;
		this.attendees = builder.attendees;
		this.committee = builder.committee;
	}

	public static class Builder {
		private String campName;
		private LocalDate startDate;
		private LocalDate endDate;
		private LocalDate closingDate;
		private String location;
		private int attendeeSlots;
		private int committeeSlots;
		private String description;
		private String inCharge;
		private Faculty visibility;
		private List<Post> enquiries;
		private List<Post> suggestions;
		private List<String> attendees;
		private HashMap<String, Integer> committee;

		public Builder() {
			// Default values can be set here
			this.suggestions = new ArrayList<>();
			this.enquiries = new ArrayList<>();
			this.attendees = new ArrayList<>();
			this.committee = new HashMap<>();
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

		public Builder attendeeSlots(int attendeeSlots) {
			this.attendeeSlots = attendeeSlots;
			return this;
		}

		public Builder committeeSlots(int slots) {
			this.committeeSlots = slots;
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

		public Builder attendees(List<String> attendees) {
			this.attendees = attendees;
			return this;
		}

		public Builder committee(HashMap<String, Integer> committee) {
			this.committee = committee;
			return this;
		}

		public Camp build() {
			return new Camp(this);
		}
	}

	//Getters and Setters
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

	public int getAttendeeSlots() {
		return this.attendeeSlots;
	}
	public void setAttendeeSlots(int attendeeSlots) {
		this.attendeeSlots = attendeeSlots;
	}

	/* don't need this just use the get remaining functions
	public void decreaseSlots() {
		int cur = this.getAttendeeSlots();
		cur--;
		this.remainingSlots = cur;
	}

	public void increaseSlots() {
		this.attendeeSlots++;
	}*/

	public List<String> getAttendees() {
		return attendees;
	}
	public void setAttendees(List<String> attendees) {
		this.attendees = attendees;
	}
	public void addAttendee(String userID) {
		this.attendees.add(userID);
	}
	public void removeAttendee(String userID){this.attendees.remove(userID);}

	public List<String> getCommittee() {
		return new ArrayList<>(committee.keySet());
	}

	public int getCommitteeSlots() {
		return committeeSlots;
	}

	public void setCommitteeSlots(int committeeSlots) {
		this.committeeSlots = committeeSlots;
	}

	public void addCommittee(String userID) {
		if(this.getRemainingCommitteeSlots() > 0){
			System.out.println("User has been added to the camp committee!");
			this.committee.put(userID, 0); //Put UserID with points intialised to 0 into the committee
		}
		else
			System.out.println("Camp Committee is full.");
	}
	public void removeCommittee(String userID){
		if(checkForMember(userID)){
			System.out.println("User has been removed from the camp committee!");
			this.committee.remove(userID);
		}
		else
			System.out.println("User is in the camp committee.");
	}

	//if specified user has already joined the camp as an attendee or committee, returns true
	public boolean checkForMember(String userID) {
		return this.committee.containsKey(userID) || this.attendees.contains(userID);
	}

	public int getNumAttendees() {
		return this.attendees.size();
	}

	public int getRemainingCommitteeSlots() {
		return this.committeeSlots - this.committee.size();
	}

	public int getNumCommitteeMembers() {
		return this.committee.size();
	}

	public int getRemainingAttendeeSlots(){
		return this.attendeeSlots - this.getNumAttendees();
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
	public void addEnquiry(Post post){ this.enquiries.add(post); }
	public List<Post> getEnquiries(){ return this.enquiries; }

	public void setSuggestions(List<Post> suggestions) { this.suggestions = suggestions; }
	public List<Post> getSuggestions(){ return this.suggestions; }

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

	//Prints formatted camp information to user
	public void display() {
		System.out.println("Name: " + this.campName);
		System.out.println("Dates: " + this.startDate + " to " + this.endDate);
		System.out.println("Registration closes on: " + this.closingDate);
		System.out.println("Open to: " + this.visibility);
		System.out.println("Location: " + this.location);
		System.out.println("Available Attendee Slots: " + this.getRemainingAttendeeSlots() + " / " + this.attendeeSlots);
		System.out.println("Committee Size: " + this.getCommittee().size() + " / " + this.committeeSlots);
		System.out.println("Description: " + this.description);
		System.out.println("Staff-in-Charge: " + this.inCharge);
	}
	@Override
	public String toString() {
	    return "Name: " + this.campName + ", Starting Date: " + this.startDate + ", Ending Date: "
	+ this.endDate + ", Closing Date: " + this.closingDate + ", Open to: "
	    		+ this.visibility + ", Location: " + this.location + ", Attendee Slots: " + this.attendeeSlots + ", Committee Slots: " + this.committeeSlots + ", Committee Members: "
	    		+ this.getCommittee().toString() + ", Student Members: " + this.getAttendees() + ", Description: " + this.description + ", Staff-in-Charge: " + this.inCharge;
	}
}
