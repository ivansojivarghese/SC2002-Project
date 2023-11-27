package cams.camp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import cams.posts.post_entities.Post;
import cams.posts.PostType;
import cams.util.Faculty;
import cams.util.Savable;
import cams.util.SavableObject;


 /**
 * The class Camp implements serializable. Allocate properties under certain data types.
 * Allows for saving of object variables to user device
 */ 
public class Camp implements Serializable {
    @Serial
    private static final long serialVersionUID = 5449975410110048101L; //crc32b Hash of "Camp" converted to ASCII
	private static final String folderName = "camps";
	private static final Savable savable = new SavableObject();
	private String campName;
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalDate closingDate;
	private String location;
	private int attendeeSlots = 0;
	private int committeeSlots = 10;
	private String description;
	private String inCharge;
	private Faculty facultyRestriction;
	private boolean visible;
	private List<Post> enquiries;
	private List<Post> suggestions;
	private List<String> attendees;
	private HashMap<String, Integer> committee;
	private HashSet<String> bannedUsers;


/** 
 *
 * Gets the file name
 *
 * @return The name of the serialized camp file.
 */
	public String getFileName() { 

		return "Camp_" + this.campName.replaceAll("\\s+", "_") + ".ser";
	}


/** 
 *
 * Gets the folder name
 *
 * @return The name of the folder where the object will be stored after being serialized.
 */
	public String getFolderName(){return folderName;}

	 /**
	  * Empty camp constructor
	  */
	public Camp() {
	}

	//Camp class has complex construction so using a builder provides better readability

/** 
 *
 * Constructor for the complex camp object through the use of the Builder object.
 *
 * @param builder The builder for the camp.
 */
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
		this.facultyRestriction = builder.facultyRestriction;
		this.visible = builder.visible;
		this.enquiries = builder.enquiries;
		this.suggestions = builder.suggestions;
		this.attendees = builder.attendees;
		this.committee = builder.committee;
		this.bannedUsers = builder.bannedUsers;
		savable.saveObject(this, folderName, getFileName());
	}

	/**
	 * Builder for the camp object to ensure readability
	 */
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
		private Faculty facultyRestriction;
		private boolean visible;
		private List<Post> enquiries;
		private List<Post> suggestions;
		private List<String> attendees;
		private HashMap<String, Integer> committee;
		private HashSet<String> bannedUsers;

		/**
		 * Builder
		 */
		public Builder() { 

			// Default values can be set here
			this.suggestions = new ArrayList<>();
			this.enquiries = new ArrayList<>();
			this.attendees = new ArrayList<>();
			this.committee = new HashMap<>();
			this.bannedUsers = new HashSet<>();
		}

		/**
		 * Camp name
		 *
		 * @param campName  the camp name.
		 * @return Builder
		 */
		public Builder campName(String campName) { 

			this.campName = campName;
			return this;
		}

		/**
		 *
		 * Set start date of camp.
		 *
		 * @param startDate  the start date.
		 * @return Builder
		 */
		public Builder startDate(LocalDate startDate) { 

			this.startDate = startDate;
			return this;
		}


		/**
		 *
		 * Set End date of camp
		 *
		 * @param endDate  the end date.
		 * @return Builder
		 */
		public Builder endDate(LocalDate endDate) { 

			this.endDate = endDate;
			return this;
		}


		/**
		 *
		 * Set Closing date of camp.
		 *
		 * @param closingDate  the closing date.
		 * @return Builder
		 */
		public Builder closingDate(LocalDate closingDate) { 

			this.closingDate = closingDate;
			return this;
		}


		/**
		 *
		 * Set Location
		 *
		 * @param location  the location.
		 * @return Builder
		 */
		public Builder location(String location) { 

			this.location = location;
			return this;
		}


		/**
		 * Set Attendee slots
		 *
		 * @param attendeeSlots  the total number attendee slots in this camp.
		 * @return Builder
		 */
		public Builder attendeeSlots(int attendeeSlots) { 

			this.attendeeSlots = attendeeSlots;
			return this;
		}


		/**
		 *
		 * Set number of Committee slots in the camp.
		 *
		 * @param slots  the total number of slots.
		 * @return Builder
		 */
		public Builder committeeSlots(int slots) { 

			this.committeeSlots = slots;
			return this;
		}


		/**
		 *
		 * Set Description of the camp
		 *
		 * @param description  the description.
		 * @return Builder
		 */
		public Builder description(String description) { 

			this.description = description;
			return this;
		}

		/**
		 *
		 * Set the user in charge of the camp.
		 *
		 * @param inCharge  the in charge.
		 * @return Builder
		 */
		public Builder inCharge(String inCharge) { 

			this.inCharge = inCharge;
			return this;
		}


		/**
		 *
		 * Set the Faculty restriction.
		 *
		 * @param faculty  the faculty.
		 * @return Builder
		 */
		public Builder facultyRestriction(Faculty faculty) { 

			this.facultyRestriction = faculty;
			return this;
		}


		/**
		 *
		 * List the Enquiries
		 *
		 * @param enquiries  A list of enquiries stored as Post objects.
		 * @return Builder
		 */
		public Builder enquiries(List<Post> enquiries) { 

			this.enquiries = enquiries;
			return this;
		}


		/**
		 * List the Suggestions
		 *
		 * @param suggestions Adds a list of suggestions to the builder
		 * @return Builder
		 */
		public Builder suggestions(List<Post> suggestions) {
			this.suggestions = suggestions;
			return this;
		}

		/**
		 * Sets a list of attendees to the camp builder
		 * @param attendees String list of attendees' UserIDs
		 */
		public Builder attendees(List<String> attendees) {
			this.attendees = attendees;
			return this;
		}


	/**
	 *
	 * Adds a committee into the builder
	 *
	 * @param committee Hashmap object containing the key-value pairs the UserID string of a committee member and their number of points
	 * @return Builder
	 */
		public Builder committee(HashMap<String, Integer> committee) { 

			this.committee = committee;
			return this;
		}


		/**
		 * Sets the visibility of the camp.
		 *
		 * @param visibility The boolean value to set the camp's visibility to;
		 * if true, it represents that the camp will be visible.
		 * @return Builder
		 */
		public Builder visible(boolean visibility) { 

			this.visible = visibility;
			return this;
		}

		/**
		 * Method to call the camp constructor after building it.
		 *
		 * @return Camp
		 */
		public Camp build() {
			return new Camp(this);
		}
	}

/** 
 *
 * Update details
 *
 * @param details CampDetails object storing the updated camp details to set the camp attributes to.
 */
	public void updateDetails(CampDetails details) { 

		// Each of these methods returns 'this' for method chaining
		this.setCampName(details.getCampName())
			.setAttendeeSlots(details.getAttendeeSlots())
			.setCommitteeSlots(details.getCommitteeSlots())
			.setVisible(details.isVisible())
			.setFacultyRestriction(details.getFacultyRestriction())
			.setEndDate(details.getEndDate())
			.setStartDate(details.getStartDate())
			.setClosingDate(details.getClosingDate())
			.setDescription(details.getDescription())
			.setLocation(details.getLocation());
		savable.saveObject(this, folderName, getFileName());
	}


/** 
 *
 * Add banned users. Users may be banned from a camp once they deregister it.
 *
 * @param UserID The user identifier.
 */
	public void addBannedUser(String UserID){ 

		this.bannedUsers.add(UserID);
		savable.saveObject(this, folderName, getFileName());
	}


/** 
 *
 * Checks if the user has been banned.
 *
 * @param UserID The user identifier.
 * @return boolean value representing whether the student is banned from the camp.
 */
	public boolean isBanned(String UserID){ 

		return this.bannedUsers.contains(UserID);
	}

	//Getters and Setters

/** 
 *
 * Gets the camp name
 *
 * @return The current camp name.
 */
	public String getCampName() { 

		return campName;
	}

/** 
 *
 * Sets the camp name
 *
 * @param campName The updated name of the camp.
 * @return Camp
 */
	public Camp setCampName(String campName) { 

		String oldFileName = getFileName();
		this.campName = campName;
		savable.deleteFile(folderName, oldFileName);
		savable.saveObject(this, folderName, getFileName());
		return this;
	}


/** 
 *
 * Gets the start date
 *
 * @return the start date
 */
	public LocalDate getStartDate() { 

		return startDate;
	}

/** 
 *
 * Sets the start date
 *
 * @param startDate  the start date. 
 * @return Camp
 */
	public Camp setStartDate(LocalDate startDate) { 

		this.startDate = startDate;
		savable.saveObject(this, folderName, getFileName());
		return this;
	}


/** 
 *
 * Gets the end date
 *
 * @return the end date
 */
	public LocalDate getEndDate() { 

		return endDate;
	}

/** 
 *
 * Sets the end date
 *
 * @param endDate  the end date. 
 * @return Camp
 */
	public Camp setEndDate(LocalDate endDate) { 

		this.endDate = endDate;
		savable.saveObject(this, folderName, getFileName());
		return this;
	}


/** 
 *
 * Gets the closing date
 *
 * @return the closing date
 */
	public LocalDate getClosingDate() { 

		return closingDate;
	}

/** 
 *
 * Sets the closing date
 *
 * @param closingDate  the closing date. 
 * @return Camp
 */
	public Camp setClosingDate(LocalDate closingDate) { 

		this.closingDate = closingDate;
		savable.saveObject(this, folderName, getFileName());
		return this;
	}


/** 
 *
 * Gets the location
 *
 * @return the location
 */
	public String getLocation() { 

		return location;
	}

/** 
 *
 * Sets the location
 *
 * @param location  the location. 
 * @return Camp
 */
	public Camp setLocation(String location) { 

		this.location = location;
		savable.saveObject(this, folderName, getFileName());
		return this;
	}


/** 
 *
 * Gets the attendee slots
 *
 * @return the attendee slots
 */
	public int getAttendeeSlots() { 

		return this.attendeeSlots;
	}

/** 
 *
 * Sets the attendee slots
 *
 * @param attendeeSlots  the attendee slots. 
 * @return Camp
 */
	public Camp setAttendeeSlots(int attendeeSlots) { 

		this.attendeeSlots = attendeeSlots;
		savable.saveObject(this, folderName, getFileName());
		return this;
	}


/** 
 *
 * Gets the attendees
 *
 * @return the attendees
 */
	public List<String> getAttendees() { 

		return attendees;
	}

/** 
 *
 * Sets the attendees
 *
 * @param attendees  the attendees. 
 */
	public void setAttendees(List<String> attendees) { 

		this.attendees = attendees;
		savable.saveObject(this, folderName, getFileName());
	}

/** 
 *
 * Add attendee
 *
 * @param userID  the user identifier. 
 */
	public void addAttendee(String userID) { 

		this.attendees.add(userID);
		savable.saveObject(this, folderName, getFileName());
	}

/** 
 *
 * Remove attendee
 *
 * @param userID  the user identifier. 
 */
	public void removeAttendee(String userID){ 

		this.attendees.remove(userID);
		savable.saveObject(this, folderName, getFileName());
	}


/** 
 *
 * Gets the committee
 *
 * @return the committee
 */
	public HashMap<String, Integer> getCommittee() { 

		return new HashMap<>(committee);
	}



/** 
 *
 * Gets the committee slots
 *
 * @return the committee slots
 */
	public int getCommitteeSlots() { 

		return committeeSlots;
	}


/** 
 *
 * Sets the committee slots
 *
 * @param committeeSlots  the committee slots. 
 * @return Camp
 */
	public Camp setCommitteeSlots(int committeeSlots) { 

		this.committeeSlots = committeeSlots;
		savable.saveObject(this, folderName, getFileName());
		return this;
	}


/** 
 *
 * Add committee
 *
 * @param userID  the user identifier. 
 * @return int: 1 or 0, determinant to see if the user has been added.
 */
	public int addCommittee(String userID) { 

		if(this.getRemainingCommitteeSlots() > 0){
			System.out.println("User has been added to the camp committee!");
			this.committee.put(userID, 0); //Put UserID with points initialised to 0 into the committee
			savable.saveObject(this, folderName, getFileName());
			return 1;
		}
		else {
			System.out.println("Camp Committee is full.");
			return 0;
		}
		// 
	}

/** 
 *
 * Remove committee
 *
 * @param userID  the user identifier. 
 */
	public void removeCommittee(String userID){ 

		if(checkForMember(userID)){
			System.out.println("User has been removed from the camp committee!");
			this.committee.remove(userID);
			savable.saveObject(this, folderName, getFileName());
		}
		else
			System.out.println("User is in the camp committee.");
	}


/** 
 *
 * Gets the points of committee member
 *
 * @param userID  the user identifier. 
 * @return the points of committee member
 */
	public int getPointsOfCommitteeMember(String userID){ 

		return this.committee.get(userID);
	}


/** 
 *
 * Add point to committee member
 *
 * @param userID  the user identifier.
 * @param pointsAdded  the points added. 
 */
	public void addPointToCommitteeMember(String userID, int pointsAdded){
		int currentPoints = this.committee.get(userID);
		this.committee.put(userID, currentPoints + pointsAdded);
	}


/** 
 *
 * Check for member
 *
 * @param userID The userID to be checked.
 * @return boolean value representing whether the user has registered for the camp.
 *	if specified user has already joined the camp as an attendee or committee, returns true
 */
	public boolean checkForMember(String userID) {
		return this.committee.containsKey(userID) || this.attendees.contains(userID);
	}


/** 
 *
 * Gets the number of attendees
 *
 * @return the number of attendees
 */
	public int getNumAttendees() { 

		return this.attendees.size();
	}


/** 
 *
 * Gets the number of remaining committee slots.
 *
 * @return the number of remaining committee slots.
 */
	public int getRemainingCommitteeSlots() { 

		return this.committeeSlots - this.committee.size();
	}


/** 
 *
 * Gets the number of committee members
 *
 * @return the number of committee members
 */
	public int getNumCommitteeMembers() { 

		return this.committee.size();
	}


/** 
 *
 * Sets the committee members
 *
 * @param committee  the committee. 
 */
	public void setCommitteeMembers(HashMap<String, Integer> committee) { 

		this.committee = committee;
	}



/** 
 *
 * Gets the remaining attendee slots
 *
 * @return the remaining attendee slots
 */
	public int getRemainingAttendeeSlots(){ 

		return this.attendeeSlots - this.getNumAttendees();
	}

/** 
 *
 * Gets the description
 *
 * @return the description
 */
	public String getDescription() {
		return description;
	}

/** 
 *
 * Sets the description
 *
 * @param description The camp's description.
 * @return Camp
 */
	public Camp setDescription(String description) {
		this.description = description;
		savable.saveObject(this, folderName, getFileName());
		return this;
	}

/** 
 *
 * Gets the in charge
 *
 * @return the in charge
 */
	public String getInCharge() { 

		return inCharge;
	}

/** 
 *
 * Sets the in charge
 *
 * @param inCharge  the in charge. 
 * @return Camp
 */
	public Camp setInCharge(String inCharge) {
		this.inCharge = inCharge;
		savable.saveObject(this, folderName, getFileName());
		return this;
	}

/** 
 *
 * Gets the visibility value of the camp as a boolean.
 *
 * @return the visibility value of the camp
 */
	public boolean getVisible() {
		return visible;
	}

/** 
 *
 * Sets the visibility of the camp.
 *
 * @param visible boolean value to set the camp visibility to. If true, camp is visible.
 * @return Camp for method chaining.
 */
	public Camp setVisible(boolean visible) { 

		this.visible = visible;
		savable.saveObject(this, folderName, getFileName());
		return this;
    }


/** 
 *
 * Gets the faculty restriction
 *
 * @return the faculty restriction
 */
	public Faculty getFacultyRestriction() { 

		return facultyRestriction;
	}

/** 
 *
 * Sets the faculty restriction
 *
 * @param faculty  the faculty. 
 * @return Camp
 */
	public Camp setFacultyRestriction(Faculty faculty) { 

		this.facultyRestriction = faculty;
		savable.saveObject(this, folderName, getFileName());
		return this;
	}


/** 
 *
 * Sets the enquiries to a specified list of enquiry objects upcasted to post objects.
 *
 * @param enquiries  A list of enquiries stored as post objects.
 */
	public void setEnquiries(List<Post> enquiries) { 

		this.enquiries = enquiries;
	}

/** 
 *
 * Adds enquiry to the camp.
 *
 * @param post  The post to be added.
 */
	public void addEnquiry(Post post){ 

		this.enquiries.add(post);
		savable.saveObject(this, folderName, getFileName());
	}


/** 
 *
 * Gets all the camp's enquiries.
 *
 * @return A list of posts representing the enquiries of a camp.
 */
	public List<Post> getEnquiries(){ return this.enquiries; } 

	public void addSuggestion(Post post){

		this.suggestions.add(post);
		savable.saveObject(this, folderName, getFileName());
	}


	/**
	 *
	 * Sets the suggestions of a camp
	 *
	 * @param suggestions  A list of post objects representing the suggestions to set the camp suggestions to.
	 */
	public void setSuggestions(List<Post> suggestions) {
		this.suggestions = suggestions;
		savable.saveObject(this, folderName, getFileName());
	}


	/**
	 *
	 * Gets a list of the camp's suggestions
	 *
	 * @return A list of suggestions stored as post objects.
	 */
	public List<Post> getSuggestions(){ return this.suggestions; } 

	public void removePost(PostType postType, Post post){

        switch (postType) {
            case ENQUIRY -> this.getEnquiries().remove(post);
            case SUGGESTION -> this.getSuggestions().remove(post);
        }
		savable.saveObject(this, folderName, getFileName());
	}


/**
 * Saves the current camp object by serializing it into a location.
 */
	public void save(){ 

		savable.saveObject(this, this.getFolderName(), this.getFileName());
	}

	//Prints formatted camp information to user

	/**
	 * Displays the camp's basic information.
	 * Does not display the visibility of the camp. Visibility attribute is hidden from the UI.
	 */
	public void display() { 

		System.out.println("Name: " + this.campName);
		System.out.println("Dates: " + this.startDate + " to " + this.endDate);
		System.out.println("Registration closes on: " + this.closingDate);
		System.out.println("Open to: " + this.facultyRestriction);
		System.out.println("Location: " + this.location);
		System.out.println("Available Attendee Slots: " + this.getRemainingAttendeeSlots() + " / " + this.attendeeSlots);
		System.out.println("Committee Size: " + this.getCommittee().size() + " / " + this.committeeSlots);
		System.out.println("Description: " + this.description);
		System.out.println("Staff-in-Charge: " + this.inCharge);
	}

	/**
	 *
	 * Converts the camp's attributes to a string.
	 *
	 * @return String value containing the camp information.
	 */
	@Override
	public String toString() { 

	    return "Name: " + this.campName + ", Starting Date: " + this.startDate + ", Ending Date: "
	+ this.endDate + ", Closing Date: " + this.closingDate + ", Open to: "
	    		+ this.facultyRestriction + ", Location: " + this.location + ", Attendee Slots: " + this.attendeeSlots + ", Committee Slots: " + this.committeeSlots + ", Committee Members: "
	    		+ this.getCommittee().toString() + ", Student Members: " + this.getAttendees() + ", Description: " + this.description + ", Staff-in-Charge: " + this.inCharge;
	}
}
