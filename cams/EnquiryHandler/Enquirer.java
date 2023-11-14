package cams.EnquiryHandler;

import cams.Camp;
import cams.Student;
import cams.PostTypes.*;
import cams.UnifiedCampRepository;
import cams.UnifiedUserRepository;
import cams.User;
import cams.Camp;

public class Enquirer extends EnquirerUI{
    public int submit(String campName, String userID, String text){
        Post newEnquiry = new Enquiry();
        if(newEnquiry.setContent(text) && newEnquiry.setPostedBy(userID) && newEnquiry.setCamp(campName))
            return 1; //successful
        return -1; //unsuccessful
    }
    public int edit(User user, int postIndex, String content){
        if(user == null)
            return -1; //user does not exist
        if(!(user instanceof Student))
            return -2; //user is not of the correct role
        Student student = (Student) user;
        Post currentPost = student.getMyEnquiries().get(postIndex);
        currentPost.setContent(content);
        return 1;
    }
    public int delete(User user, int postIndex){
        if(user == null)
            return -1; //user does not exist
        if(!(user instanceof Student))
            return -2; //user is not of the correct role

        Student student = (Student) user;
        Post currentPost = student.getMyEnquiries().get(postIndex);

        if(!(currentPost instanceof Enquiry))
            return -3; //Post is not an enquiry

        Enquiry enquiry = (Enquiry) currentPost;

        if(enquiry.getReply() != null)
            return -4; //reply exists

        student.removePost(currentPost);
            return 1;
    }
}
