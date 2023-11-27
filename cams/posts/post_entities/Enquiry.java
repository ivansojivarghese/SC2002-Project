package cams.posts.post_entities;

import java.io.Serializable;

/**
 * Represents an enquiry message, extending {@link Message} and implementing {@link Serializable}.
 * Holds the content of an enquiry.
 */
public class Enquiry extends Message implements Serializable {
    private String content;

    /**
     * Retrieves the content of this enquiry.
     *
     * @return The content of the enquiry as a {@link String}.
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of this enquiry.
     *
     * @param content The content of the enquiry as a {@link String}.
     */
    public void setContent(String content){
        this.content = content;
    }

    /**
     * Displays the content of this enquiry message.
     * <p>
     * Prints out the userID of the poster and the content of the enquiry.
     */
    @Override
    public void displayContent() {
        System.out.println("User " + this.getPostedBy() + " Enquired: ");
        System.out.println(content);
    }
}
