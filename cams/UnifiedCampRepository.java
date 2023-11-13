package cams;

import java.util.HashMap;

public class UnifiedCampRepository {
    //Use of Singleton design pattern to ensure only one instance exists
    //Allows for better access
    private static UnifiedCampRepository instance;
    private HashMap<String, Camp> Camps;

    //prevent construction outside the class
    private UnifiedCampRepository(){}

    public void addCamp(Camp camp){
        this.Camps.put(camp.campName, camp);
    }
    public Camp retrieveCamp(String campName){
        return Camps.get(campName);
    }

    public static UnifiedCampRepository getInstance() {
        // If the instance is null, create a new one
        if (instance == null) {
            instance = new UnifiedCampRepository();
        }
        // Return the existing/new instance
        return instance;
    }
}
