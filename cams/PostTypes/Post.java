package cams.PostTypes;

import cams.Camp;

public abstract class Post {
    //Attributes
    private Camp camp;
    private String postedBy;
    private String content;

    //Getters and Setters
    public Camp getCamp() {
        return camp;
    }

    public void setCamp(Camp camp) {
        this.camp = camp;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
