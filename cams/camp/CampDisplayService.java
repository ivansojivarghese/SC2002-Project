package cams.camp;

import cams.database.UnifiedCampRepository;
import cams.users.User;

/**
 * This class is responsible for displaying all the associated camps of a specific user
 */
public class CampDisplayService implements CampService {
    public CampDisplayService() {
    }

    /**
     * Displays the camps of the specified user
     * @param user User whose camps are to be displayed
     * @return Number of camps associated with the user
     */
    public int displayMyCamps(User user) {
        int index = 0;

        UnifiedCampRepository repo = UnifiedCampRepository.getInstance();
        if(user.getMyCamps() == null)
            throw new NullPointerException("No camps to display");
        for (String c : user.getMyCamps()) {
            System.out.println("_________________________________");
            System.out.println("Index: " + index++);
            Camp camp = repo.retrieveCamp(c);
            camp.display();
        }
        return index;
    }
}
