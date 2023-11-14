package cams.PostHandler;
import cams.User;

public interface PostReplierUI extends PostViewerUI{
    public int reply(User user, int post, String reply);
}
