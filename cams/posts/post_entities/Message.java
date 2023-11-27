package cams.posts.post_entities;

import java.io.Serializable;
/**
 * Represents an abstract base class serving as a template for types of messages.
 * Objects of this type are {@link Serializable}.
 * Provides basic structure for messages with functionality to set and display content.
 */
public abstract class Message implements Serializable {
    private String postedBy;

    /**
     * Sets the content of the message.
     * This method is abstract and must be implemented by subclasses.
     *
     * @param content The content of the message as a {@link String}.
     */
    public abstract void setContent(String content);

    /**
     * Displays the content of the message.
     * This method is abstract and must be implemented by subclasses to define how the message content is displayed.
     */
    public abstract void displayContent();

    /**
     * Retrieves the user ID of the person who posted the message.
     *
     * @return The user ID of the poster as a {@link String}.
     */
    public String getPostedBy(){
        return this.postedBy;
    }

    /**
     * Sets the user ID of the person who posted the message.
     *
     * @param userID The user ID of the poster as a {@link String}.
     */
    public void setPostedBy(String userID) {
        this.postedBy = userID;
    }
}
