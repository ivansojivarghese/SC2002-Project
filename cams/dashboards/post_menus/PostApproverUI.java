package cams.dashboards.post_menus;
import cams.users.User;

public interface PostApproverUI extends PostViewerUI{
    boolean approve(User user, int postIndex, String isApproved);
}
