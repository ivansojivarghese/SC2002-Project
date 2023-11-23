package cams.dashboards.post_menus;
import cams.users.User;

public interface PostReplierUI extends PostViewerUI{
    boolean reply(User user, int post, String reply);
}
