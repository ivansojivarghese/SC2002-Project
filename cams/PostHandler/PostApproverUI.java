package cams.PostHandler;
import cams.PostTypes.*;

public interface PostApproverUI extends PostViewerUI{
    public int approve(Post post, Boolean isApproved);
}
