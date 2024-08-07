package cams.users;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

import cams.dashboards.menu.CommitteeUI;
import cams.dashboards.DashboardState;
import cams.dashboards.menu.StudentUI;
import cams.posts.post_entities.Post;
import cams.replier.replier_controller.CommitteeReplierController;
import cams.replier.replier_controller.ReplierController;
import cams.suggestion.suggester_controllers.SuggesterController;
import cams.suggestion.suggester_controllers.CommitteeSuggesterController;
import cams.util.Faculty;

/**
 * Represents a student, extending {@link User}, implementing {@link Committable} and {@link Serializable}.
 * Students can join committees, register for camps, and interact with camp-related posts.
 */
public class Student extends User implements Committable, Enquirer, Serializable { // student class
    @Serial
    private static final long serialVersionUID = 555657101575497102L; //crc32b Hash of "User" converted to ASCII
    private static final SuggesterController suggester = new CommitteeSuggesterController();
    private static final ReplierController replier = new CommitteeReplierController();
    private String myCommittee;
    private List<Post> myEnquiries;

    /**
     * Generates a file name based on the userID.
     * Replaces all blank spaces with _
     * @return String value of the file name
     */
    public String getFileName() {
        return "Student_" + this.getUserID().replaceAll("\\s+", "_") + ".ser";
    }

    /**
     * Retrieves the appropriate dashboard menu state for the student
     * depending on whether the student is a committee member.
     *
     * @return The {@link DashboardState} specific to the student's role.
     */
    @Override
    public DashboardState getMenuState() {
        if(this.isCommittee())
            return new CommitteeUI();
        else
            return new StudentUI();
    }

    /**
     * Empty constructor for student.
     */
    Student(){
        this.setCommittee("NA");
        this.myEnquiries = new ArrayList<>();
        savable.saveObject(this, folderName, this.getFileName());
    }
    /**
     * Constructs a Student with the specified details.
     *
     * @param name     The name of the student.
     * @param userID   The user ID of the student.
     * @param faculty  The faculty to which the student belongs.
     */
    public Student(String name, String userID, Faculty faculty) {
        super(name, userID, faculty);
        this.setCommittee("NA");
        this.myEnquiries = new ArrayList<>();
        savable.saveObject(this, folderName, this.getFileName());
    }
    /**
     * Returns the replier controller dependency used by Staff
     * @return Returns the committee controller
     */
    public ReplierController getReplierController(){
        return replier;
    }
    /**
     * Checks whether the student is already a part of a committee.
     * @return false if student's myCommittee variable is "NA"
     */
    @Override
    public boolean isCommittee() {
        return !myCommittee.equalsIgnoreCase("NA");
    }

    /**
     * Sets the committee attribute representing the name of the committee that the student is a member of.
     * A student only be a member of one committee.
     * @param committee The name of the committee to be assigned to the user.
     */
    @Override
    public void setCommittee(String committee) {
        myCommittee = committee;
        savable.saveObject(this, folderName, this.getFileName());
    }

    /**
     * Retrieves the committee to which the student belongs.
     *
     * @return The name of the student's committee.
     */
    @Override
    public String getCommittee(){
        return this.myCommittee;
    }

    /**
     * Collects all enquiries posted by the student to camps.
     *
     * @return A list of {@link Post} objects representing enquiries.
     */
    @Override
    public List<Post> getEnquiries() {
        return this.myEnquiries;
    }

    /**
     * Removes specified post from the user
     * @param post Post to be removed.
     * @return true
     */
    public boolean removeEnquiry(Post post){
        this.myEnquiries.remove(post);
        savable.saveObject(this, folderName, getFileName());
        return true;
    }

    /**
     * Adds an enquiry to the student's myEnquiries attribute.
     */
    public void addEnquiry(Post post) {
        this.myEnquiries.add(post);
        savable.saveObject(this, folderName, getFileName());
    }

    /**
     * Removes the specified camp from the student's list of registered camps
     * Committee members cannot withdraw from their camps.
     * @param campName Name of the camp to be removed
     * @return true if the removal was successful, otherwise false.
     */
    public boolean removeCamp(String campName){
        //Prevent user from withdrawing if they are in the Committee
        if(this.myCommittee.equalsIgnoreCase(campName))
            return false;
        super.removeCamp(campName);
        savable.saveObject(this, folderName, this.getFileName());
        return true;
    }

    /**
     * Collects all suggestions posted by the student user to
     * the camp that they are a committee member of.
     *
     * @return A list of {@link Post} objects representing suggestions.
     */
    @Override
    public List<Post> getSuggestions() {
        return suggester.getSuggestions(this);
    }
}
