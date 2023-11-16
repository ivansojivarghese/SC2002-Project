package cams.post_types;

public class Suggestion extends Message{
    private String content;
    public void displayContent() {
        System.out.println("User " + this.getPostedBy() + " Suggested: ");
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
