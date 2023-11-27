package cams.posts.post_entities;

import cams.posts.PostType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a post.
 * A post is associated with a camp and contains various messages.
 * Object is {@link Serializable}.
 */
public class Post implements Serializable {
    //Attributes
    private String campName;
    private PostType postType;
    private List<Message> content;

    /**
     * Constructor for Post.
     */
    public Post(){
        this.campName = "";
        this.content = new ArrayList<>();
        this.postType = null;
    }

    /**
     * Adds a message to the post's content.
     *
     * @param message The {@link Message} to be added to the post.
     */
    public void addContent(Message message){
        this.content.add(message);
    }

    /**
     * Retrieves all messages associated with the post.
     *
     * @return A list of {@link Message} objects in the post.
     */
    public List<Message> getContent(){
        return this.content;
    }

    /**
     * Retrieves the first message in the post.
     *
     * @return The first {@link Message} in the post, or null if the post has no messages.
     */
    public Message getFirstMessage(){
        return this.content.get(0);
    }

    /**
     * Displays the content of the post.
     * <p>
     * Prints out the name of the associated camp and the content of each message.
     */
    public void displayContent(){
        System.out.println("Camp: " + this.campName);
        for(Message message : this.content){
            message.displayContent();
        }
    }

    /**
     * Checks if the post has received any replies.
     *
     * @return true if the post has more than one message, indicating a reply, false otherwise.
     */
    public Boolean isReplied() {
        return content.size() > 1;
    }

    //Getters and Setters
    /**
     * Retrieves the name of the camp associated with the post.
     *
     * @return The name of the camp.
     */
    public String getCampName(){return campName;}

    /**
     * Sets the camp associated with the post.
     * <p>
     * Throws an exception if attempting to change the camp for a post that already has a camp.
     *
     * @param campName The name of the camp to associate with the post.
     * @throws UnsupportedOperationException if the post already has an associated camp.
     */
    public void setCamp(String campName) {
        if(!this.getCampName().isEmpty()) { //if post already has a camp, prevent changing camp
            throw new UnsupportedOperationException("Post already has a camp");
        }
        this.campName = campName; //set new camp
    }
}
