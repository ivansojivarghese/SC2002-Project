package cams.users;

import cams.util.Faculty;

import java.time.LocalDate;

/**
 * Store the details of a camp, including dates, location, and other attributes.
 */
public class CampDetails {
    private String campName;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate closingDate;
    private String location;

    /**
     * Retrieves the name of the camp.
     *
     * @return The name of the camp.
     */
    public String getCampName() {
        return campName;
    }

    /**
     * Sets the name of the camp.
     *
     * @param campName The name to be set for the camp.
     */
    public void setCampName(String campName) {
        this.campName = campName;
    }

    /**
     * Retrieves the start date of the camp.
     *
     * @return The start date of the camp.
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date of the camp.
     *
     * @param startDate The start date to be set for the camp.
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Sets the date when the camp ends
     *
     * @param endDate The end date to be set for the camp
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets the closing date of registration
     *
     * @return LocalDate object representing the last day to register for the camp
     */
    public LocalDate getClosingDate() {
        return closingDate;
    }

    /**
     * Sets the closing date of registration
     *
     * @param closingDate The updated closing date of registration
     */
    public void setClosingDate(LocalDate closingDate) {
        this.closingDate = closingDate;
    }

    /**
     * Gets the location of the camp
     *
     * @return String value representing the camp location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of the camp
     *
     * @param location The location of the camp
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the number of slots available to attendees
     *
     * @return The total number of participant slots
     */
    public int getAttendeeSlots() {
        return attendeeSlots;
    }

    /**
     * Sets the number of slots available to attendees
     *
     * @param attendeeSlots The total number of participant slots
     */
    public void setAttendeeSlots(int attendeeSlots) {
        this.attendeeSlots = attendeeSlots;
    }

    /**
     * Retrieves the number of slots in the camp committee
     *
     * @return The number of slots in the camp committee
     */
    public int getCommitteeSlots() {
        return committeeSlots;
    }

    /**
     * Sets the number of committee slots
     *
     * @param committeeSlots The number of slots in the camp committee
     */
    public void setCommitteeSlots(int committeeSlots) {
        this.committeeSlots = committeeSlots;
    }

    /**
     * Retrieves the userID of the user in charge of the camp
     *
     * @return String value of the userID
     */
    public String getInCharge() {
        return inCharge;
    }

    /**
     * Set the user in charge of the camp
     *
     * @param inCharge The user in charge of the camp
     */
    public void setInCharge(String inCharge) {
        this.inCharge = inCharge;
    }

    /**
     * gets the camp's description
     *
     * @return String value of the description attribute of the camp
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the camp's description
     *
     * @param description The String value to set the camp description to.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retrieves the faculty that the camp is available to
     *
     * @return Enumeration of type Faculty; if ALL, the camp is unrestricted.
     */
    public Faculty getFacultyRestriction() {
        return facultyRestriction;
    }

    /**
     * Sets the faculty that the camp is available to;
     * all participants from other faculties are unable to join or view the camp
     *
     * @param facultyRestriction The faculty that the camp is available to (can be ALL)
     */
    public void setFacultyRestriction(Faculty facultyRestriction) {
        this.facultyRestriction = facultyRestriction;
    }

    /**
     * Checks if the camp is currently visible.
     * <p>
     * Visibility determines whether the camp is viewable by potential participants.
     *
     * @return true if the camp is visible, false otherwise.
     */
    public boolean isVisible() {
        return visibility;
    }

    /**
     * Sets the visibility of the camp.
     *
     * @param visibility The visibility status to set for the camp.
     */
    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    private int attendeeSlots;
    private int committeeSlots;
    private String inCharge;
    private String description;
    private Faculty facultyRestriction = Faculty.ALL;
    private boolean visibility = false;
}
