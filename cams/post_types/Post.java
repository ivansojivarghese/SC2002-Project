package cams.post_types;

import cams.Camp;
import cams.database.CampRepository;
import cams.database.UnifiedCampRepository;

import java.util.ArrayList;
import java.util.List;

public class Post {
    //Attributes
    private String campName;
    private PostType postType;
    private List<Message> content;

    public Post(){
        this.campName = "";
        this.content = new ArrayList<>();
        this.postType = null;
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
        return content.size() > 1;
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
        CampRepository repo = UnifiedCampRepository.getInstance();
        Camp camp = repo.retrieveCamp(campName);
        if(camp == null) //if camp does not exist
            return false;

        if(getCamp() != null) { //if post already has a camp, prevent changing camp
            return false;
        }
        this.campName = campName; //set new camp
        camp.addEnquiry(this);

        return true;
    }
}
