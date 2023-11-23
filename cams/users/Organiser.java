package cams.users;

import cams.Camp;

public interface Organiser {
    public void createCamp(CampDetails details);
    public void editCamp(String campName, CampDetails details);
    public void deleteCamp(String campName);
    public void assignCamp(String UserID, String campName);
    public boolean isCampNameUnique(String campName);
    public CampDetails getCampDetails(String campName);
    public int getNumAttendees(String campName);
}
