package cams.users;

import cams.Camp;

public interface Organiser {
    public void createCamp(Camp camp);
    public void editCamp(Camp camp);
    public void deleteCamp(Camp camp);
    public void assignCamp(String UserID, Camp camp);
}
