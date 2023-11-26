package cams.dashboards.suggestion_menus;

import cams.post_types.Post;
import cams.users.Student;
import cams.users.User;

import java.util.List;

public interface Suggester {
    boolean submit(String camp, User user, String text);
    boolean edit(User user, int postIndex, String content);
    boolean delete(User user, int postIndex);
    List<Post> getSuggestions(User user);
}
