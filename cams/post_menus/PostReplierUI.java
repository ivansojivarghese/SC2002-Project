package cams.post_menus;
import cams.posts.post_entities.Post;
import cams.users.User;

/**
 * Interface defining the UI actions for replying to {@link Post posts}.
 * This interface extends {@link PostViewerUI} to include functionalities for replying to posts.
 */
public interface PostReplierUI extends PostViewerUI{
    /**
     * Replies to a specific post.
     *
     * @param user     The user who is replying to the post.
     * @param post     The index of the post to which the reply is being made.
     * @param reply    The content of the reply.
     * @return true if the reply was successfully added, false otherwise.
     */
    boolean reply(User user, int post, String reply);
}
