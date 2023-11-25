package cams.post_types;

import java.io.Serializable;

public class Suggestion extends Message implements Serializable {
    private String content;
    public void displayContent() {
        System.out.println("User " + this.getPostedBy() + " Suggested: ");
        System.out.println(content);
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content){
        this.content = content;
    }
}
