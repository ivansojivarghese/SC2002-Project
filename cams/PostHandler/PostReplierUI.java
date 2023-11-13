package cams.PostHandler;
import cams.PostTypes.*;

public interface PostReplierUI extends PostViewerUI{
    public int reply(Post post, String reply);
}
