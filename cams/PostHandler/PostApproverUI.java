package cams.PostHandler;
import cams.PostTypes.*;
import cams.User;

public interface PostApproverUI extends PostViewerUI{
    public int approve(User user, int postIndex, String isApproved);
}
