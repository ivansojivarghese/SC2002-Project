package cams.enquiry.enquirer_controller;

import cams.camp.Camp;
import cams.camp.CampRepository;
import cams.database.UnifiedCampRepository;
import cams.enquiry.enquiry_menus.EnquirerUI;
import cams.post_types.*;
import cams.users.Enquirer;
import cams.users.User;

import java.io.Serializable;

/**
 * Represents an enquirer in the dashboard system, extending the functionality of {@link EnquirerUI}.
 * This class provides implementations for submitting, editing, and deleting enquiries related to camps.
 */
public class StudentEnquirerController implements EnquirerController, Serializable {
    /**
     * Constructs an EnquirerController instance.
     */
    public StudentEnquirerController() {
    }

    /**
     * Submits an enquiry about a specific camp.
     *
     * @param campName The name of the camp to which the enquiry is addressed.
     * @param user   The user making the enquiry.
     * @param text     The content of the enquiry.
     * @return true if the submission is successful, false if the camp does not exist.
     */
    public boolean submit(String campName, User user, String text){
        CampRepository campRepository = UnifiedCampRepository.getInstance();
        Camp camp = campRepository.retrieveCamp(campName);
        if(camp == null){
            System.out.println("Selected camp does not exist.");
            return false;
        }

        Post newPost = PostFactory.createPost(PostType.ENQUIRY);
        Enquiry newEnquiry = (Enquiry) newPost.getFirstMessage();
        newEnquiry.setContent(text);
        newEnquiry.setPostedBy(user.getUserID());
        newPost.setCamp(campName);
        camp.addEnquiry(newPost);
        ((Enquirer)user).addEnquiry(newPost);
        return true;
    }

    /**
     * Edits an existing enquiry made by a user.
     *
     * @param user      The user who made the enquiry.
     * @param postIndex The index of the enquiry in the user's list of enquiries.
     * @param content   The new content of the enquiry.
     * @return true if the edit is successful, false if the user does not exist.
     */
    public boolean edit(User user, int postIndex, String content){
        if(user == null){
            System.out.println("User does not exist.");
            return false;
        }
        Post currentPost = user.getEnquiries().get(postIndex);
        currentPost.getFirstMessage().setContent(content);
        CampRepository campRepository = UnifiedCampRepository.getInstance();
        Camp camp = campRepository.retrieveCamp(currentPost.getCampName());
        //Save changes
        camp.save();
        return true;
    }

    /**
     * Deletes an enquiry made by a user.
     *
     * @param user      The user who made the enquiry.
     * @param postIndex The index of the enquiry in the user's list of enquiries.
     * @return true if the deletion is successful, false if the user does not exist or if the post has replies.
     */
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

        CampRepository campRepository = UnifiedCampRepository.getInstance();
        Camp camp = campRepository.retrieveCamp(currentPost.getCampName());
        camp.removePost(PostType.ENQUIRY, currentPost);

        //Save changes
        camp.save();
        return true;
    }
}
