package cams.replier.replier_controller;

import cams.camp.Camp;
import cams.camp.CampRepository;
import cams.database.UnifiedCampRepository;
import cams.replier.ReplierUI;
import cams.post_types.Message;
import cams.post_types.Post;
import cams.post_types.Reply;
import cams.users.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class provides specific implementation of reply functionality to enquiries.
 * It extends {@link ReplierUI} and provides the implementation for the reply method.
 */
public class StaffReplierController implements ReplierController, Serializable {
    /**
     * Creates an instance of the class.
     */

    public StaffReplierController(){
    }

    /**
     * Replies to a specific {@link cams.post_types.Enquiry enquiry} made by a user.
     * This method checks if a reply already exists for the specified {@link cams.post_types.Post post} and, if not,
     * adds a {@link cams.post_types.Message message} of the type {@link cams.post_types.Reply reply} to the post. It then saves the changes to the associated camp.
     *
     * @param user      The user who is replying.
     * @param postIndex The index of the post in the user's list of enquiries to reply to.
     * @param content   The content of the reply.
     * @return true if the reply was successfully added, false otherwise.
     */
    @Override
    public boolean reply(User user, int postIndex, String content) {
        // Validate input parameters
        if (user == null) {
            System.out.println("User is null.");
            return false;
        }
        if (user.getReplierController().getEnquiries(user).isEmpty() ||
            postIndex < 0 || postIndex >= user.getReplierController().getEnquiries(user).size()) {
            System.out.println("Invalid post index.");
            return false;
        }

        Post post = this.getEnquiries(user).get(postIndex);

        // Check if the post already has a reply
        if(post.isReplied()) {
            System.out.println("Reply already exist, unable to add reply!");
            return false;
        }

        // Create and add the reply to the post
        Message reply = new Reply(user.getUserID(), content);
        post.addContent(reply);

        // Retrieve the associated camp and save changes
        CampRepository campRepo = UnifiedCampRepository.getInstance();
        Camp camp = campRepo.retrieveCamp(post.getCampName());
        camp.save();
        return true;
    }

    /**
     * Collects all enquiries related to the camps created by the staff member.
     *
     * @return A list of {@link Post} objects representing enquiries.
     */
    public List<Post> getEnquiries(User user) { //COLLECTS all enquiries in Camps created by Staff
        List<Post> myEnquiries = new ArrayList<>();
        CampRepository campRepo = UnifiedCampRepository.getInstance();

        try{
            for (String campName : user.getMyCamps()) {
                Camp camp = campRepo.retrieveCamp(campName);
                if(camp == null)
                    continue;
                myEnquiries.addAll(camp.getEnquiries());
            }
        }
        catch (NullPointerException e){
            System.out.println("No camp to retrieve suggestions from.");
        }
        return myEnquiries;
    }
}
