package cams.PostHandler;
import cams.PostTypes.*;
import cams.Camp;
import cams.User;
public interface PosterUI extends PostViewerUI {
    public int submit(String campName, String userID, String text);
    public int edit(User user, int postIndex, String content);
    public int delete(User user, int postIndex);
}
