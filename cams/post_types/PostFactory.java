package cams.post_types;

/**
 * A factory class for creating {@link Post} objects.
 * Provides a method to create a new post with an initial empty message of a specified type.
 */
public class PostFactory {

    /**
     * Creates a new post with an empty message of the specified type.
     * <p>
     * Depending on the {@link PostType}, it initializes the post with an appropriate message type.
     *
     * @param type The type of the post as specified in {@link PostType}.
     * @return A {@link Post} object with an initial message of the specified type.
     * @throws IllegalArgumentException if an unknown post type is passed.
     */
    public static Post createPost(PostType type) {
        Post post = new Post();
        Message message;
        switch (type) {
            case SUGGESTION -> {
                message = new Suggestion();
                post.addContent(message);
                return post;
            }
            case ENQUIRY -> {
                message = new Enquiry();
                post.addContent(message);
                return post;
            }
            default -> throw new IllegalArgumentException("Unknown type");
        }
    }
}
