package cams.dashboards.enquiry_menus;

import cams.Camp;
import cams.database.CampRepository;
import cams.database.UnifiedCampRepository;
import cams.post_types.Message;
import cams.post_types.Post;
import cams.post_types.Reply;
import cams.users.User;
import cams.util.SavableObject;
/**
 * Class provides specific implementation of reply functionality to enquiries.
 * It extends {@link ReplierUI} and provides the implementation for the reply method.
 */
public class Replier extends ReplierUI{
    /**
     * Creates an instance of the class.
     */
    public Replier(){}

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
        if (user.getEnquiries().isEmpty() || postIndex < 0 || postIndex >= user.getEnquiries().size()) {
            System.out.println("Invalid post index.");
            return false;
        }

        Post post = user.getEnquiries().get(postIndex);

        // Check if the post already has a reply
        if(post.isReplied()) {
            System.out.println("Reply already exist, unable to add reply!");
            return false;
        }

        // Create and add the reply to the post
        Message reply = new Reply(user.getUserID(), content);
        post.addContent(reply);

        // Retrieve the associated camp and save changes
        CampRepository repo = UnifiedCampRepository.getInstance();
        Camp camp = repo.retrieveCamp(post.getCampName());
        camp.save();
        return true;
    }
}
