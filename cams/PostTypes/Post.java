package cams.PostTypes;

import cams.Camp;
import cams.UnifiedCampRepository;

import java.util.List;

public class Post {
    //Attributes
    private Camp camp;
    private PostType postType;
    private List<Message> content;

    public Post(){
        this.camp = null;
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
        if(this.getCamp() != null) {
            this.camp.removePost(this.postType, this);
        }
    }

    //Getters and Setters
    public Camp getCamp() {
        return camp;
    }

    public Boolean setCamp(String campName) {
        UnifiedCampRepository repo = UnifiedCampRepository.getInstance();
        Camp camp = repo.retrieveCamp(campName);
        if(camp == null) //if camp does not exist
            return false;

        if(this.camp != null) { //if post already has a camp, remove the current post from the camp
            removeFromCamp();
        }
        this.camp = camp; //set new camp
        camp.addEnquiry(this);

        return true;
    }
}
