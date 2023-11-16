package cams.dashboards.post_menus;
import cams.users.User;

public interface PostApproverUI extends PostViewerUI{
    int approve(User user, int postIndex, String isApproved);
}
