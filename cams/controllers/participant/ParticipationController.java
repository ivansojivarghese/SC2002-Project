package cams.controllers.participant;

import cams.users.User;

/**
 * Interface defining the core behaviours of a participationController.
 * Includes methods for registering and de-registering from camps.
 */
public interface ParticipationController {
    /**
     * Registers a user to a specified camp.
     * <p>
     * This method handles the logic for adding a user to the attendees list of a camp.
     *
     * @param user The {@link User} who is registering.
     * @param campName The name of the camp to which the user is registering.
     */
	void register(User user, String campName);

    /**
     * De-registers a user from a specified camp.
     * <p>
     * This method handles the logic for removing a user from the attendees list of a camp.
     *
     * @param user The {@link User} who is de-registering.
     * @param campName The name of the camp from which the user is deregistering.
     */
    void deregister(User user, String campName);
    int viewAllCamps(User user);
}
