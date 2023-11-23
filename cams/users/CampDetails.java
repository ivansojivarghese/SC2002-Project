package cams.users;

import cams.util.Faculty;

import java.time.LocalDate;

public class CampDetails {
    private String campName;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate closingDate;
    private String location;

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
        return attendeeSlots;
    }

    public void setAttendeeSlots(int attendeeSlots) {
        this.attendeeSlots = attendeeSlots;
    }

    public int getCommitteeSlots() {
        return committeeSlots;
    }

    public void setCommitteeSlots(int committeeSlots) {
        this.committeeSlots = committeeSlots;
    }

    public String getInCharge() {
        return inCharge;
    }

    public void setInCharge(String inCharge) {
        this.inCharge = inCharge;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Faculty getFacultyRestriction() {
        return facultyRestriction;
    }

    public void setFacultyRestriction(Faculty facultyRestriction) {
        this.facultyRestriction = facultyRestriction;
    }

    public boolean isVisibility() {
        return visibility;
    }

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
