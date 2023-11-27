package cams.post_menus;
import cams.posts.post_entities.Post;
import cams.users.User;

/**
 * Interface defining the UI actions for submitting, editing, and deleting posts.
 * This interface extends {@link PostViewerUI} to include functionalities for managing posts.
 */
public interface PosterUI extends PostViewerUI {
    /**
     * Submits a new post to a specified camp.
     *
     * @param campName The name of the camp to which the post is being submitted.
     * @param user     The user submitting the post.
     * @param text     The content of the post.
     * @return true if the submission is successful, false otherwise.
     */
    boolean submit(String campName, User user, String text);

    /**
     * Edits an existing {@link Post post}.
     *
     * @param user      The user who owns the post.
     * @param postIndex The index of the post in the user's list of posts.
     * @param content   The new content for the post.
     * @return true if the edit is successful, false otherwise.
     */
    boolean edit(User user, int postIndex, String content);

    /**
     * Deletes a post.
     *
     * @param user      The user who owns the post.
     * @param postIndex The index of the post in the user's list of posts.
     * @return true if the deletion is successful, false otherwise.
     */
    boolean delete(User user, int postIndex);
}
