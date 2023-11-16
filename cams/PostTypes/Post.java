package cams.PostTypes;

import cams.Camp;
import cams.UnifiedCampRepository;

import java.util.List;

public class Post {
    //Attributes
    private String campName;
    private PostType postType;
    private List<Message> content;

    public Post(){
        this.campName = null;
        this.content = null;
    }

    public void addContent(Message message){
        this.content.add(message);
    }

    public List<Message> getContent(){
        return this.content;
    }

    public Message getFirstMessage(){
        return this.content.get(0);
    }

    public void displayContent(){
        System.out.println("Camp: " + this.campName);
        for(Message message : this.content){
            message.displayContent();
        }
    }

    public Boolean isReplied() {
        if(content.size() > 1)
            return true;
        return false;
    }

    public void removeFromCamp() {
        //if post is already in a camp, remove the current post from the camp
        Camp camp = this.getCamp();
        if(camp != null) {
            camp.removePost(this.postType, this);
        }
    }

    //Getters and Setters
    public Camp getCamp() {
        UnifiedCampRepository repo = UnifiedCampRepository.getInstance();
        return repo.retrieveCamp(this.campName);
    }

    public String getCampName(){return campName;}

    public Boolean setCamp(String campName) {
        UnifiedCampRepository repo = UnifiedCampRepository.getInstance();
        Camp camp = repo.retrieveCamp(campName);
        if(camp == null) //if camp does not exist
            return false;

        if(getCamp() != null) { //if post already has a camp, remove the current post from the camp
            removeFromCamp();
        }
        this.campName = campName; //set new camp
        camp.addEnquiry(this);

        return true;
    }
}
