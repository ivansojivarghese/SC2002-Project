package cams.database;

import cams.Camp;
import cams.util.Faculty;
import java.util.List;

public interface CampRepository {
    boolean Exists(String name);
    List<Camp> filterCampByFaculty(Faculty faculty);
    List<Camp> allCamps();
    boolean isEmpty();
    void addCamp(Camp camp);
    Camp retrieveCamp(String campName);
    void deleteCamp(String campName);
    int getSize();
}
