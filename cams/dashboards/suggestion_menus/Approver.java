package cams.dashboards.suggestion_menus;

import cams.users.User;

public interface Approver {
    boolean approve(User user, int postIndex, boolean isApproved);
}
