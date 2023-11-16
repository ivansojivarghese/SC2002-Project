package cams.dashboards.enquiry_menus;

import cams.Camp;
import cams.post_types.*;
import cams.users.User;

public class Enquirer extends EnquirerUI{
    public Enquirer() {
    }

    public int submit(String campName, String userID, String text){
        Post newPost = PostFactory.createPost(PostType.ENQUIRY);
        Enquiry newEnquiry = (Enquiry) newPost.getFirstMessage();
        if(newEnquiry.setContent(text) && newEnquiry.setPostedBy(userID) && newPost.setCamp(campName))
            return 1; //successful
        return -1; //unsuccessful
    }

    public int edit(User user, int postIndex, String content){
        if(user == null)
            return -1; //user does not exist
        Post currentPost = user.getEnquiries().get(postIndex);
        currentPost.getFirstMessage().setContent(content);
        return 1;
    }
    public int delete(User user, int postIndex){
        if(user == null)
            return -1; //user does not exist

        Post currentPost = user.getEnquiries().get(postIndex);
        if(currentPost.isReplied())
            return -2; //reply exists

        Camp camp = currentPost.getCamp();
        camp.removePost(PostType.ENQUIRY, currentPost);
        //User does not store their posts
        return 1;
    }
}
