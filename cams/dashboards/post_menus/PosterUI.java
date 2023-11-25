package cams.dashboards.post_menus;
import cams.users.User;
public interface PosterUI extends PostViewerUI {
    boolean submit(String campName, User user, String text);
    boolean edit(User user, int postIndex, String content);
    boolean delete(User user, int postIndex);
}
