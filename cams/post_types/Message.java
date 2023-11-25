package cams.post_types;

import java.io.Serializable;

public abstract class Message implements Serializable {
    private String postedBy;
    public abstract void setContent(String content);
    public abstract void displayContent();
    public String getPostedBy(){
        return this.postedBy;
    }
    public void setPostedBy(String userID) {
        this.postedBy = userID;
    }
}
