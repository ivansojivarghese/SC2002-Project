package cams.PostTypes;

public class Reply extends Message {
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

