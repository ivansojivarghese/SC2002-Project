package cams.dashboards.post_menus;
import cams.users.User;

public interface PostReplierUI extends PostViewerUI{
    int reply(User user, int post, String reply);
}
