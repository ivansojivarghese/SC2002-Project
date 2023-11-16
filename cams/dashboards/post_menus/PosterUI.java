package cams.dashboards.post_menus;
import cams.users.User;
public interface PosterUI extends PostViewerUI {
    int submit(String campName, String userID, String text);
    int edit(User user, int postIndex, String content);
    int delete(User user, int postIndex);
}
