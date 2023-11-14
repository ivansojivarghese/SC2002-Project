package cams.PostTypes;

import cams.Camp;
import cams.UnifiedCampRepository;

public class Enquiry extends Post{
    private String reply;

    public void removeFromCamp(Camp camp) {
        if(this.getCamp() != null) { //if already has a camp, remove the current post from the camp
            camp.getEnquiries().remove(this);
        }
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }
}
