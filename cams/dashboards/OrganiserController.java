package cams.dashboards;

import cams.camp.CampDetails;
import cams.users.User;

/**
 * Interface defining the responsibilities of an organiser.
 * Includes methods for creating, editing, and deleting camps, as well as managing camp assignments.
 */
public interface OrganiserController {
    /**
     * Creates a new camp with the specified details.
     *
     * @param details The {@link CampDetails} object containing information about the new camp.
     */
    void createCamp(CampDetails details);

    /**
     * Edits an existing camp with new details.
     *
     * @param campName The name of the camp to be edited.
     * @param details  The new {@link CampDetails} to be applied to the camp.
     */
    void editCamp(String campName, CampDetails details);

    /**
     * Deletes a camp based on its name.
     *
     * @param campName The name of the camp to be deleted.
     */
    void deleteCamp(String campName);

    /**
     * Assigns a user to a camp.
     *
     * @param userID   The user ID of the user to be assigned to the camp.
     * @param campName The name of the camp to which the user is being assigned.
     */
    void assignCamp(String userID, String campName);

    /**
     * Checks if a camp name is unique within the system.
     *
     * @param campName The name of the camp to check.
     * @return true if the camp name is unique, false otherwise.
     */
    boolean isCampNameUnique(String campName);

    /**
     * Retrieves the details of a specific camp.
     *
     * @param campName The name of the camp whose details are being requested.
     * @return The {@link CampDetails} of the specified camp.
     */
    CampDetails getCampDetails(String campName);

    /**
     * Gets the number of attendees registered for a specific camp.
     *
     * @param campName The name of the camp.
     * @return The number of attendees registered for the camp.
     */
    int getNumAttendees(String campName);

    /**
     * Displays all camps created by the user
     * @param user The current user
     * @return Number of camps created.
     */
    int viewAllCamps(User user);
}
