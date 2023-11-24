package cams.dashboards.enquiry_menus;

import cams.Camp;
import cams.database.CampRepository;
import cams.database.UnifiedCampRepository;
import cams.post_types.*;
import cams.users.User;
import cams.util.SerializeUtility;

public class Enquirer extends EnquirerUI{
    public Enquirer() {
    }

    public boolean submit(String campName, String userID, String text){
        Post newPost = PostFactory.createPost(PostType.ENQUIRY);
        Enquiry newEnquiry = (Enquiry) newPost.getFirstMessage();
        newEnquiry.setContent(text);
        newEnquiry.setPostedBy(userID);
        newPost.setCamp(campName);

        CampRepository repo = UnifiedCampRepository.getInstance();
        repo.retrieveCamp(campName).addEnquiry(newPost);
        return true;
    }

    public boolean edit(User user, int postIndex, String content){
        if(user == null){
            System.out.println("User does not exist.");
            return false;
        }
        Post currentPost = user.getEnquiries().get(postIndex);
        currentPost.getFirstMessage().setContent(content);

        CampRepository repo = UnifiedCampRepository.getInstance();
        Camp camp = repo.retrieveCamp(currentPost.getCampName());
        //Save changes
        SerializeUtility.saveObject(camp, camp.getFolderName(), camp.getFileName());
        return true;
    }
    public boolean delete(User user, int postIndex){
        if(user == null) {
            System.out.println("User does not exist.");
            return false;
        }

        Post currentPost = user.getEnquiries().get(postIndex);
        if(currentPost.isReplied()){
            System.out.println("Unable to delete posts with replies.");
            return false;
        }
        CampRepository repo = UnifiedCampRepository.getInstance();
        Camp camp = repo.retrieveCamp(currentPost.getCampName());
        camp.removePost(PostType.ENQUIRY, currentPost);
        //Save changes; posts are stored in camp, user does not store posts
        SerializeUtility.saveObject(camp, camp.getFolderName(), camp.getFileName());
        return true;
    }
}
