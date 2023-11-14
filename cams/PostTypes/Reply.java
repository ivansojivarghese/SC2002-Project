package cams.PostTypes;

public class Reply extends Message {
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

    public Boolean setContent(String content){
        this.content = content;
        return true;
    }
}

