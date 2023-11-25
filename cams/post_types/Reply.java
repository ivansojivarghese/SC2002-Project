package cams.post_types;

import java.io.Serializable;

public class Reply extends Message implements Serializable {
    public Reply(String userID, String content) {
        this.setContent(content);
        this.setPostedBy(userID);
    }

    private String content;
    public void displayContent() {
        System.out.println("User " + this.getPostedBy() + " Replied: ");
        System.out.println(content);
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content){
        this.content = content;
    }
}

