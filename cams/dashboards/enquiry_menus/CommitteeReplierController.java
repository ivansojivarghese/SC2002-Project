package cams.dashboards.enquiry_menus;

import cams.camp.Camp;
import cams.database.CampRepository;
import cams.database.UnifiedCampRepository;
import cams.post_types.Message;
import cams.post_types.Post;
import cams.post_types.Reply;
import cams.users.Committable;
import cams.users.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class provides specific implementation of reply functionality to enquiries.
 * It extends {@link ReplierUI} and provides the implementation for the reply method.
 */
public class CommitteeReplierController implements ReplierController, Serializable {
    /**
     * Creates an instance of the class.
     */
    public CommitteeReplierController(){
    }

    /**
     * Replies to a specific {@link cams.post_types.Enquiry enquiry} made by a user.
     * This method checks if a reply already exists for the specified {@link cams.post_types.Post post} and, if not,
     * adds a {@link cams.post_types.Message message} of the type {@link cams.post_types.Reply reply} to the post. It then saves the changes to the associated camp.
     * It adds points if the user is a committee member.
     * The user may only reply to posts in the camp they are a committee member of.
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

        Post post = user.getEnquiries().get(postIndex);

        if(!((Committable)user).getCommittee().equalsIgnoreCase(post.getCampName()))
        {
            System.out.println("Unable to reply to enquiries outside of the camp you are a member in.");
            return false;
        }

        // Check if the post already has a reply
        if(post.isReplied()) {
            System.out.println("Reply already exist, unable to add reply!");
            return false;
        }

        // Create and add the reply to the post
        Message reply = new Reply(user.getUserID(), content);
        post.addContent(reply);

        // Retrieve the associated camp
        CampRepository campRepo = UnifiedCampRepository.getInstance();
        Camp camp = campRepo.retrieveCamp(post.getCampName());

        //Add points
        camp.addPointToCommitteeMember(user.getUserID(), 1);

        //Save changes
        camp.save();

        return true;
    }

    /**
     * Collects all enquiries in the camp that the member is a committee of
     *
     * @return A list of {@link Post} objects representing enquiries.
     */
    public List<Post> getEnquiries(User user) {
        List<Post> myEnquiries = new ArrayList<>();
        CampRepository campRepo = UnifiedCampRepository.getInstance();
        Camp camp = campRepo.retrieveCamp(((Committable)user).getCommittee());
        if(camp == null)
            return myEnquiries;
        try {
            myEnquiries.addAll(camp.getEnquiries());
        }
        catch (NullPointerException e){
            System.out.println("No camp to retrieve suggestions from.");
        }
        return myEnquiries;
    }
}

