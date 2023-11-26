package cams.replier.replier_controller;

import cams.post_types.Post;
import cams.users.User;

import java.util.List;

public interface ReplierController {
    /**
     * Provides a template for the business logic of generating a reply to a specified post
     * @param user The user replying
     * @param postIndex The index of the post in the user's list of possible posts to reply
     * @param reply The user's reply
     * @return true if reply added, else false.
     */
    boolean reply(User user, int postIndex, String reply);
    /**
     * Collects all enquiries that the user may reply to
     *
     * @return A list of {@link Post} objects representing enquiries.
     */
    List<Post> getEnquiries(User user);
}
