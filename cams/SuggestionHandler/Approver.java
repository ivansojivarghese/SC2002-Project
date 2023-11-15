package cams.SuggestionHandler;

import cams.PostTypes.Approval;
import cams.PostTypes.Message;
import cams.PostTypes.Post;
import cams.User;

public class Approver extends ApproverUI{

    @Override
    public int approve(User user, int postIndex, String isApproved) {
        Post currentPost = user.getSuggestions().get(postIndex);
        if(validateInput(isApproved).equalsIgnoreCase("error"))
            return -1; //incorrect input
        Message approval = new Approval(isApproved);
        currentPost.addContent(approval);
        return 1; //successful
    }
    public String validateInput(String approved){
        if ("1".equalsIgnoreCase(approved) || "yes".equalsIgnoreCase(approved) ||
                "true".equalsIgnoreCase(approved)) {
            return "true";
        }
        else if ("0".equalsIgnoreCase(approved) || "no".equalsIgnoreCase(approved) ||
                "false".equalsIgnoreCase(approved)) {
            return "false";
        }
        else
            return "error";
    }
}
