package cams.PostTypes;

public class PostFactory {
    public static Post createPost(PostType type) {
        Post post = new Post();;
        Message message;
        switch (type){
            case SUGGESTION:
                message = new Suggestion();
                post.addContent(message);
                return post;
            case ENQUIRY:
                message = new Enquiry();
                post.addContent(message);
                return post;
        }
        throw new IllegalArgumentException("Unknown type");
    }
}
