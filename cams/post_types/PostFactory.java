package cams.post_types;

import java.io.Serializable;

public class PostFactory implements Serializable {

    //Returns a post object with an empty message of specified type
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
        }
        throw new IllegalArgumentException("Unknown type");
    }
}
