package cams.camp;

import cams.users.User;

/**
 * This class defines an interface for handling a user's camp-related services,
 * such as the display of all of a user's camps
 */
public interface CampService {
    /**
     * Displays the camps of the specified user
     * @param user User whose camps are to be displayed
     * @return Number of camps associated with the user
     */
    int displayMyCamps(User user);
}
