package cams.dashboards.enquiry_menus;

import cams.Camp;
import cams.post_types.*;
import cams.users.User;

public class Enquirer extends EnquirerUI{
    public Enquirer() {
    }

    public boolean submit(String campName, String userID, String text){
        Post newPost = PostFactory.createPost(PostType.ENQUIRY);
        Enquiry newEnquiry = (Enquiry) newPost.getFirstMessage();
        newEnquiry.setContent(text);
        newEnquiry.setPostedBy(userID);
        newPost.setCamp(campName);
        return true;
    }

    public boolean edit(User user, int postIndex, String content){
        if(user == null){
            System.out.println("User does not exist.");
            return false;
        }
        Post currentPost = user.getEnquiries().get(postIndex);
        currentPost.getFirstMessage().setContent(content);
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

        Camp camp = currentPost.getCamp();
        camp.removePost(PostType.ENQUIRY, currentPost);
        //User does not store their posts
        return true;
    }
}
