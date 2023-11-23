package cams.dashboards.post_menus;
import cams.dashboards.DashboardState;
import cams.users.User;

public interface PostViewerUI extends DashboardState {
    int view(User user);
}
