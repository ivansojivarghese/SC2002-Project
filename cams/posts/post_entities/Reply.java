package cams.posts.post_entities;

import java.io.Serializable;
/**
 * Represents a reply type of message, extending {@link Message} .
 * This object is {@link Serializable}.
 * Responsible for storing the content of a reply within a post.
 */
public class Reply extends Message implements Serializable {
    private String content;

    /**
     * Constructs a Reply instance with the specified user ID and content.
     *
     * @param userID The user ID of the person posting the reply.
     * @param content The content of the reply as a {@link String}.
     */
    public Reply(String userID, String content) {
        this.content = content;
        this.setPostedBy(userID);
    }

    /**
     * Displays the content of this reply message.
     * <p>
     * Prints out the userID of the poster and the content of the reply.
     */
    @Override
    public void displayContent() {
        System.out.println("User " + this.getPostedBy() + " Replied: ");
        System.out.println(content);
    }

    /**
     * Retrieves the content of this reply.
     *
     * @return The content of the reply as a {@link String}.
     */
    public String getContent() {
        return this.content;
    }

    /**
     * Sets the content of this reply.
     *
     * @param content The content of the reply as a {@link String}.
     */
    public void setContent(String content){
        this.content = content;
    }
}

