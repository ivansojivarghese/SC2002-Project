package cams.dashboards.suggestion_menus;

import cams.post_types.Approval;
import cams.post_types.Message;
import cams.post_types.Post;
import cams.users.User;

import java.util.InputMismatchException;

public class Approver extends ApproverUI {
    @Override
    public boolean approve(User user, int postIndex, String isApproved) {
        Post currentPost = user.getSuggestions().get(postIndex);
        Boolean boolApproved = validateInput(isApproved);
        if(boolApproved == null)
            throw new InputMismatchException("Invalid input.");
        Message approval = new Approval(boolApproved);
        currentPost.addContent(approval);
        return true;
    }
    public Boolean validateInput(String approved){
        if ("1".equalsIgnoreCase(approved) || "yes".equalsIgnoreCase(approved) ||
                "true".equalsIgnoreCase(approved)) {
            return true;
        }
        else if ("0".equalsIgnoreCase(approved) || "no".equalsIgnoreCase(approved) ||
                "false".equalsIgnoreCase(approved)) {
            return false;
        }
        else
            return null;
    }
}
