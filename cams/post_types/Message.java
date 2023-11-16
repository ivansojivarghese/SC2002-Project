package cams.post_types;

public abstract class Message {
    private String postedBy;
    public abstract Boolean setContent(String content);
    public abstract void displayContent();
    public String getPostedBy(){
        return this.postedBy;
    }
    public Boolean setPostedBy(String userID) {
        this.postedBy = userID;
        return true;
    }
}
