package cams.post_types;

public abstract class Message {
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
