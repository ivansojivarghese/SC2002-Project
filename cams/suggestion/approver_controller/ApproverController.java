package cams.suggestion.approver_controller;

import cams.post_types.Post;
import cams.users.User;
import java.util.List;

/**
 * The {@code ApproverController} interface defines methods that allow
 * staff members with approver roles to approve or reject posts and retrieve
 * suggestions for approval.
 */
public interface ApproverController {

    /**
     * Approves or rejects a post submitted by a user.
     *
     * @param user          The staff member performing the approval.
     * @param postIndex     The index of the post to be approved or rejected.
     * @param isApproved    {@code true} if the post is approved, {@code false} if rejected.
     * @return {@code true} if the approval/rejection is successful, {@code false} otherwise.
     */
    boolean approve(User user, int postIndex, boolean isApproved);

    /**
     * Retrieves a list of post suggestions for approval by the specified user.
     *
     * @param user  The staff member requesting post suggestions.
     * @return A {@code List} of {@code Post} objects representing post suggestions.
     */
    List<Post> getSuggestions(User user);
}
