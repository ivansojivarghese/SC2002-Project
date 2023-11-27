package cams.post_types;

import java.io.Serializable;

/**
 * Represents a suggestion message, extending {@link Message} and implementing {@link Serializable}.
 * Holds the content of a suggestion within a post.
 */
public class Suggestion extends Message implements Serializable {
    private String content;

    /**
     * Displays the content of this suggestion message.
     * <p>
     * Prints out the userID of the poster and the content of the suggestion.
     */
    public void displayContent() {
        System.out.println("User " + this.getPostedBy() + " Suggested: ");
        System.out.println(content);
    }

    /**
     * Retrieves the content of this suggestion.
     *
     * @return The content of the suggestion as a {@link String}.
     */
    public String getContent() {
        return this.content;
    }

    /**
     * Sets the content of this suggestion.
     *
     * @param content The content of the suggestion as a {@link String}.
     */
    public void setContent(String content){
        this.content = content;
    }
}
