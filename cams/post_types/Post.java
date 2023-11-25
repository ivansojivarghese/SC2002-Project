package cams.post_types;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Post implements Serializable {
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

    //Getters and Setters
    public String getCampName(){return campName;}

    public void setCamp(String campName) {
        if(!this.getCampName().isEmpty()) { //if post already has a camp, prevent changing camp
            throw new UnsupportedOperationException("Post already has a camp");
        }
        this.campName = campName; //set new camp
    }
}
