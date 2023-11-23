package cams.post_types;

public class Enquiry extends Message{
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
