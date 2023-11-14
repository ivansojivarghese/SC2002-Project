package cams.PostTypes;

import cams.Camp;
import cams.UnifiedCampRepository;

public class Enquiry extends Message{
    private String content;
    public String getContent() {
        return content;
    }
    public Boolean setContent(String content){
        this.content = content;
        return true;
    }

    @Override
    public void displayContent() {
        System.out.println("User " + this.getPostedBy() + " Enquired: ");
        System.out.println(content);
    }
}
