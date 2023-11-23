package cams.database;

import cams.Camp;
import cams.util.Faculty;

import java.util.ArrayList;
import java.util.List;

public interface CampRepository {
    public boolean Exists(String name);
    public List<Camp> filterCampByFaculty(Faculty faculty);
    public List<Camp> allCamps();
    public boolean isEmpty();
    public void addCamp(Camp camp);
    public Camp retrieveCamp(String campName);
    public void deleteCamp(String campName);
    public int getSize();
}
