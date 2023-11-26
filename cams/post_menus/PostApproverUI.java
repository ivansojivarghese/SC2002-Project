package cams.post_menus;
import cams.users.User;

/**
 * Interface defining the UI actions for approving or rejecting {@link cams.post_types.Post posts}.
 * This interface extends {@link PostViewerUI} and adds functionalities specific to post approval.
 */
public interface PostApproverUI extends PostViewerUI{
    /**
     * Approves or rejects a specific post made by a user.
     *
     * @param user       The user who is performing the approval action.
     * @param postIndex  The index of the post in the list of posts to be approved.
     * @param isApproved Boolean flag indicating whether the post is approved (true) or disapproved (false).
     * @return true if the approval action was successfully executed, false otherwise.
     */
    boolean approve(User user, int postIndex, boolean isApproved);
}
