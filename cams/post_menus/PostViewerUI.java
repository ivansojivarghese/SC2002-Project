package cams.post_menus;
import cams.dashboards.UI.DashboardState;
import cams.users.User;

/**
 * Interface defining the UI actions for viewing {@link cams.post_types.Post posts}.
 * This interface extends {@link DashboardState} to include functionalities for viewing posts.
 */
public interface PostViewerUI extends DashboardState {

    /**
     * Displays the posts for a given user.
     *
     * @param user The user whose posts are to be displayed.
     * @return The number of posts displayed.
     */
    int view(User user);
}
