package cams.EnquiryHandler;

import cams.Camp;
import cams.Student;
import cams.PostTypes.*;
import cams.User;

public class Enquirer extends EnquirerUI{
    public int submit(Camp camp, String userID, String text){
        return 1;
    }
    public int edit(User user, int postIndex, String content){
        return 1;
    }
    public int delete(User user, int postIndex){
        Post currentPost;
        if(user instanceof Student) {
            Student student = (Student) user;
            currentPost = student.getMyEnquiries().get(postIndex);

            if(currentPost instanceof Enquiry){
                Enquiry enquiry = (Enquiry) currentPost;
                if(enquiry.getReply() == null){
                    student.removePost(currentPost);
                    return 1;
                }

                else
                    return 2;
            }
        }
        return 3;
    }

}
