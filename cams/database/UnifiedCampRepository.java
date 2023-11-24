package cams.database;

import cams.Camp;
import cams.users.User;
import cams.util.Faculty;
import cams.util.SerializeUtility;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UnifiedCampRepository implements CampRepository {
    private static final String fileLocation = System.getProperty("user.dir") + File.separator + "resources" + File.separator + "camps";

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
    public String getCampID(String campName){
        return campName.toUpperCase().replaceAll("\\s", "");
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
        this.Camps.put(getCampID(camp.getCampName()), camp);
    }
    public Camp retrieveCamp(String campName){
        //Retrieve camp using uppercase camp name without any spaces
        campName = getCampID(campName);
        return Camps.get(campName);
    }

    public boolean Exists(String campName){
        return this.retrieveCamp(campName) != null;
    }

    public void deleteCamp(String campName) { // delete the camp
    	this.Camps.remove(getCampID(campName));
    }

    public int getSize(){
        return this.Camps.size();
    }

    public boolean initialiseData(){
        File folder = new File(fileLocation);
        File[] listOfFiles = folder.listFiles();

        //If folder cannot be accessed return false
        if (listOfFiles == null) {
            System.out.println("Error: Unable to access user database.");
            return false;
        }
        //If there are no serializable objects to load return false
        if (listOfFiles.length == 0) {
            return false;
        }
        //If there are serializable objects to load
        //Iterate through each file in the folder and load it into the repository
        for (File file : listOfFiles) {
            if (file.isFile()) {
                Serializable object = SerializeUtility.loadObject(file.getAbsolutePath());
                if(!(object instanceof Camp)){
                    System.out.println("Serializable found is not of type Camp, skipping object.");
                    continue;
                }
                Camp camp = (Camp) object;
                this.Camps.put(this.getCampID(camp.getCampName()), camp);
            }
        }
        return true;
    }
}
