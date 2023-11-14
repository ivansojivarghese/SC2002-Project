package cams;

import java.util.ArrayList;
import java.util.List;
import cams.PostTypes.*;

public class Camp extends App {
	public String campName;
	public int[] startDate;
	public int[] endDate;
	public int[] closingDate;
	public String userGroup;
	public String location;
	public int slots = 0;
	public int remainSlots = 0;
	public String description;
	public String inCharge;
	public boolean visibility;
	private List<Post> Enquiries;
	private List<Post> Suggestions;
	public Student[] studentsList;
	public Student[] committee;

	//always have an empty constructor! otherwise
	public Camp(){}

	//USE A BUILDER INSTEAD
	public Camp(String campName, int[] startDate, int[] endDate, int[] closingDate, String userGroup,
			String location, int slots, Student[] studentsList, Student[] committee, String description, String inCharge, boolean visibility) {
		// TODO Auto-generated constructor stub
		this.campName = campName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.closingDate = closingDate;
		this.userGroup = userGroup;
		this.location = location;
		this.slots = slots;
		this.remainSlots = slots;
		this.studentsList = studentsList;
		this.committee = committee;
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
	
	public boolean findClashes(ArrayList<Integer> otherCamps) {
		boolean res = false;
		for (int i = 0; i < otherCamps.size(); i++) {
			int res1;
			int res2;
			int st[];
			int en[];
			st = ((Camp) campsArr.get(otherCamps.get(i))).startDate; // get start/end dates of registered camps by user, compare with other camps
			en = ((Camp) campsArr.get(otherCamps.get(i))).endDate;
			
			res1 = checkFutureDate(this.startDate[0], this.startDate[1], this.startDate[2], en[0], en[1], en[2], true, false);
			res2 = checkFutureDate(this.endDate[0], this.endDate[1], this.endDate[2], st[1], st[1], st[2], false, false);
			
			if (res1 == 0 || res2 == 0) {
				res = true;
				break;
			}
		}
		return res;
	}
	
	public String getName() {
		return this.campName;
	}
	
	public String getUserGroup() {
		return this.userGroup;
	}
	
	public int getSlots() {
		return this.remainSlots;
	}
	
	public void decrSlots() {
		int cur = this.getSlots();
		cur--;
		this.remainSlots = cur;
	}
	
	public void incrSlots() {
		int cur = this.getSlots();
		cur++;
		this.remainSlots = cur;
	}
	
	public boolean getVisibility() {
		return this.visibility;
	}
	
	public void updateVisibility(boolean visibility) {
		this.visibility = visibility;
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
	
	@Override
	public String toString() {
	    return "Name: " + this.campName + ", Starting Date: " + this.startDate[0] + "/" + this.startDate[1] + "/" + this.startDate[2] + ", Ending Date: " 
	+ this.endDate[0] + "/" + this.endDate[1] + "/" + this.endDate[2] + ", Closing Date: " + 
	    		this.closingDate[0] + "/" + this.closingDate[1] + "/" + this.closingDate[2] + ", Open to: "
	    		+ this.userGroup + ", Location: " + this.location + ", Total Slots: " + this.slots + ", Remaining Slots: " + this.remainSlots + ", Committee Members: "
	    		+ this.getMembers(committee) + ", Student Members: " + this.getMembers(studentsList) + ", Description: " + this.description + ", Staff-in-Charge: " + this.inCharge + ", Visibility: " + this.visibility;
	    
	}
	
}
