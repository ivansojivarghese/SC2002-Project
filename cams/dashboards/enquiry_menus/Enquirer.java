package cams.dashboards.enquiry_menus;

import cams.Camp;
import cams.database.CampRepository;
import cams.post_types.*;
import cams.users.User;

/**
 * Represents an enquirer in the dashboard system, extending the functionality of {@link EnquirerUI}.
 * This class provides implementations for submitting, editing, and deleting enquiries related to camps.
 */
public class Enquirer extends EnquirerUI{
    private final CampRepository campRepository;
    /**
     * Constructs an Enquirer instance.
     */
    public Enquirer(CampRepository campRepository) {
        super();
        this.campRepository = campRepository;
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
        Camp camp = campRepository.retrieveCamp(currentPost.getCampName());
        camp.removePost(PostType.ENQUIRY, currentPost);
        //Save changes; posts are stored in camp, user does not store posts
        camp.save();
        return true;
    }
}
