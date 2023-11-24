package cams.dashboards.suggestion_menus;

import cams.post_types.Approval;
import cams.post_types.Message;
import cams.post_types.Post;
import cams.users.User;

import java.util.InputMismatchException;

public class Approver extends ApproverUI {
    @Override
    public boolean approve(User user, int postIndex, boolean isApproved) {
        Post currentPost = user.getSuggestions().get(postIndex);
        Message approval = new Approval(isApproved);
        currentPost.addContent(approval);
        return true;
    }
}
