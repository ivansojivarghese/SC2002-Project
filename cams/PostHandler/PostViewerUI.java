package cams.PostHandler;
import cams.PostTypes.*;
import cams.User;

public interface PostViewerUI {
    public void displayMenu(User user);
    public int view(User user);
}
