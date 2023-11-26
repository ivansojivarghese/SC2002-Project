package cams.controllers.approver;

import cams.users.User;

public interface ApproverController {
    boolean approve(User user, int postIndex, boolean isApproved);
}
