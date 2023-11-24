package cams.users;

public interface Organiser {
    void createCamp(CampDetails details);
    void editCamp(String campName, CampDetails details);
    void deleteCamp(String campName);
    void assignCamp(String UserID, String campName);
    boolean isCampNameUnique(String campName);
    CampDetails getCampDetails(String campName);
    int getNumAttendees(String campName);
}
