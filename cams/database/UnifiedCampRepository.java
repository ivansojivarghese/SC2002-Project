package cams.database;

import cams.camp.Camp;
import cams.camp.CampRepository;
import cams.util.Faculty;
import cams.util.Loader;
import cams.util.ObjectLoader;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Implements {@link CampRepository} as a Singleton to manage and store Camp objects.
 * Provides functionalities to add, retrieve, delete, and query camps.
 */
public class UnifiedCampRepository implements CampRepository {
    private static final Loader loader = new ObjectLoader();
    private static final String fileLocation = System.getProperty("user.dir") + File.separator + "resources" + File.separator + "camps";
    private static UnifiedCampRepository instance;
    private HashMap<String, Camp> Camps;

    /**
     * Private constructor to prevent instantiation outside the class.
     */
    private UnifiedCampRepository(){
        this.Camps = new HashMap<>();
    }

    /**
     * Provides a global point of access to the {@link UnifiedCampRepository} instance.
     *
     * @return The singleton instance of {@link UnifiedCampRepository}.
     */
    public static UnifiedCampRepository getInstance() {
        // If the instance is null, create a new one
        if (instance == null) {
            instance = new UnifiedCampRepository();
        }
        // Return the existing/new instance
        return instance;
    }

    /**
     * Filters camps based on the specified faculty.
     * <p>
     * This method returns a list of camps that are restricted to a given faculty, or open to all faculties if specified.
     *
     * @param faculty The faculty to filter the camps by.
     * @return A list of {@link Camp} objects filtered by the specified faculty.
     */
    @Override
    public List<Camp> filterCampByFaculty(Faculty faculty){
        List<Camp> filteredCamps = new ArrayList<>();
        for(Camp camp : this.Camps.values())
            if(camp.getFacultyRestriction() == faculty)
                filteredCamps.add(camp);

        return filteredCamps;
    }

    /**
     * Retrieves the ID of a camp based on its name.
     * <p>
     * This method generates a unique ID for a camp by converting its name to uppercase and removing spaces.
     *
     * @param campName The name of the camp.
     * @return The unique ID of the camp.
     */
    public String getCampID(String campName){
        return campName.toUpperCase().replaceAll("\\s", "");
    }

    /**
     * Getter method for instances of {@link Camp} stored in the repository.
     * @return List of all camp objects stored in the repository
     */
    public List<Camp> allCamps(){
        return new ArrayList<>(this.Camps.values());
    }

    /**
     * Checks if the hashmap storing camps in the camp repository is empty.
     * @return true if camp is empty, else false.
     */
    @Override
    public boolean isEmpty() {
        return this.Camps.isEmpty();
    }

    /**
     * Adds a camp to the hashmap in the repository storing camp objects.
     * The key is the ID of the camp, retrieved using {@link #getCampID(String)}
     * @param camp The {@link Camp} object to add.
     */
    @Override
    public void addCamp(Camp camp){
        this.Camps.put(getCampID(camp.getCampName()), camp);
    }

    /**
     * Retrieves the camp object with the specified camp name
     * @param campName The name of the camp to retrieve.
     * @return {@link Camp} object with the camp name
     */
    @Override
    public Camp retrieveCamp(String campName){
        //Retrieve camp using uppercase camp name without any spaces
        campName = getCampID(campName);
        return Camps.get(campName);
    }

    /**
     * Checks if the camp exists in the repository
     * @param campName The name of the camp to check.
     * @return true if the camp exists, else false
     */
    @Override
    public boolean Exists(String campName){
        return this.retrieveCamp(campName) != null;
    }

    /**
     * Removes the camp with the specified camp name from the repository hashmap
     * @param campName The name of the camp to delete.
     */
    @Override
    public void deleteCamp(String campName) { // delete the camp
    	this.Camps.remove(getCampID(campName));
    }

    /**
     * Gets the total number of camps currently stored in the repository's hashmap of {@link Camp} objects.
     * @return int value representing the size of the repository hashmap
     */
    @Override
    public int getSize(){
        return this.Camps.size();
    }

    /**
     * Initializes the repository data from stored files.
     * <p>
     * Loads Camp objects from serialized files in a specified directory.
     *
     * @return true if data initialization is successful, false otherwise.
     */
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
                Serializable object = loader.loadObject(file.getAbsolutePath());
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
