package cams.post_types;

import java.io.Serializable;

public class Enquiry extends Message implements Serializable {
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content){
        this.content = content;
    }

    @Override
    public void displayContent() {
        System.out.println("User " + this.getPostedBy() + " Enquired: ");
        System.out.println(content);
    }
}
