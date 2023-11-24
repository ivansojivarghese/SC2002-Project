package cams.database;

import cams.Camp;
import cams.util.Faculty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UnifiedCampRepository implements CampRepository {
    //Use of Singleton design pattern to ensure only one instance exists
    //Allows for better access
    private static UnifiedCampRepository instance;
    private HashMap<String, Camp> Camps;

    //prevent construction outside the class
    private UnifiedCampRepository(){
        this.Camps = new HashMap<>();
    }
    public static UnifiedCampRepository getInstance() {
        // If the instance is null, create a new one
        if (instance == null) {
            instance = new UnifiedCampRepository();
        }
        // Return the existing/new instance
        return instance;
    }

    public List<Camp> filterCampByFaculty(Faculty faculty){
        List<Camp> filteredCamps = new ArrayList<>();
        for(Camp camp : this.Camps.values())
            if(camp.getFacultyRestriction() == faculty)
                filteredCamps.add(camp);

        return filteredCamps;
    }

    public List<Camp> allCamps(){
        return new ArrayList<>(this.Camps.values());
    }
    public boolean isEmpty() {
        return this.Camps.isEmpty();
    }

    //Key is the Camp name and is always set to UpperCase, without any spacing
    //Removing spacing prevents duplicate camp names where the only different is spaces
    public void addCamp(Camp camp){
        this.Camps.put(camp.getCampName().toUpperCase().replaceAll("\\s", ""), camp);
    }
    public Camp retrieveCamp(String campName){
        //Retrieve camp using uppercase camp name without any spaces
        campName = campName.toUpperCase().replaceAll("\\s", "");
        return Camps.get(campName);
    }

    public boolean Exists(String campName){
        return this.retrieveCamp(campName) != null;
    }

    public void deleteCamp(String campName) { // delete the camp
    	this.Camps.remove(campName.toUpperCase().replaceAll("\\s", ""));
    }

    public int getSize(){
        return this.Camps.size();
    }
}
