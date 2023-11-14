package cams.PostTypes;

import cams.Camp;
import cams.UnifiedCampRepository;

public abstract class Post {
    //Attributes
    private Camp camp;
    private String postedBy;
    private String content;

    public Post(){
        this.camp = null;
        this.postedBy = null;
        this.content = null;
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
            removeFromCamp(camp);
        }
        this.camp = camp; //set new camp

        return true;
    }

    public abstract void removeFromCamp(Camp camp);

    public String getPostedBy() {
        return postedBy;
    }

    public Boolean setPostedBy(String userID) {
        this.postedBy = userID;
        return true;
    }

    public String getContent() {
        return content;
    }

    public Boolean setContent(String content) {
        this.content = content;
        return true;
    }
}
