package cams.database;

import cams.Camp;
import cams.util.Faculty;
import java.util.List;
/**
 * Interface defining the repository actions for managing camps.
 * Provides methods for adding, retrieving, deleting, and querying camps.
 */
public interface CampRepository {
    /**
     * Checks if a camp with the specified name exists in the repository.
     *
     * @param name The name of the camp to check.
     * @return true if the camp exists, false otherwise.
     */
    boolean Exists(String name);

    /**
     * Retrieves a list of camps filtered by the specified faculty.
     *
     * @param faculty The faculty to filter the camps by.
     * @return A list of {@link Camp} objects that belong to the specified faculty.
     */
    List<Camp> filterCampByFaculty(Faculty faculty);
    /**
     * Retrieves a list of all camps in the repository.
     *
     * @return A list of all {@link Camp} objects.
     */
    List<Camp> allCamps();

    /**
     * Checks if the repository is empty.
     *
     * @return true if the repository contains no camps, false otherwise.
     */
    boolean isEmpty();

    /**
     * Adds a new camp to the repository.
     *
     * @param camp The {@link Camp} object to add.
     */
    void addCamp(Camp camp);

    /**
     * Retrieves a camp by its name.
     *
     * @param campName The name of the camp to retrieve.
     * @return The {@link Camp} object if found, null otherwise.
     */
    Camp retrieveCamp(String campName);

    /**
     * Deletes a camp from the repository by its name.
     *
     * @param campName The name of the camp to delete.
     */
    void deleteCamp(String campName);

    /**
     * Retrieves the size of the repository.
     *
     * @return The number of camps in the repository.
     */
    int getSize();
}
