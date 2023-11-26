package cams.controllers.approver;

import cams.post_types.Post;
import cams.users.Staff;
import cams.users.User;

import java.util.List;

public interface ApproverController {
    boolean approve(User user, int postIndex, boolean isApproved);

    public List<Post> getSuggestions(User user);
}
