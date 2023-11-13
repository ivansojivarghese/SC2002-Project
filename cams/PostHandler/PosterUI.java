package cams.PostHandler;
import cams.PostTypes.*;
import cams.Camp;
import cams.User;
public interface PosterUI extends PostViewerUI {
    public int submit(Camp camp, User user, String text);
    public int edit(Post post, String content);
    public int delete(Post post);
}
