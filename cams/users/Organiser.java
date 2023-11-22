package cams.users;

import cams.Camp;

public interface Organiser {
    public void createCamp(String campName);
    public void editCamp(String campName);
    public void deleteCamp(String campName);
    public void assignCamp(String UserID, String campName);
}
