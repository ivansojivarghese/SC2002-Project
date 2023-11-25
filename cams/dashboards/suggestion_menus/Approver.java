package cams.dashboards.suggestion_menus;

import cams.Camp;
import cams.database.CampRepository;
import cams.database.UnifiedCampRepository;
import cams.database.UnifiedUserRepository;
import cams.database.UserRepository;
import cams.post_types.Approval;
import cams.post_types.Message;
import cams.post_types.Post;
import cams.users.Committable;
import cams.users.User;
import cams.util.SavableObject;

/**
 * Class responsible for handling the approval process of posts.
 * Extends the ApproverUI and provides implementation for the {@link #approve(User, int, boolean) approve}  method.
 */
public class Approver extends ApproverUI {
    /**
     * Approves or disapproves a specific post made by a user.
     * <p>
     * This method adds an approval or disapproval to the post and updates the associated camp.
     * If the suggester is a committee member and the suggestion is approved, a point is added.
     *
     * @param user       The user performing the approval action.
     * @param postIndex  The index of the post in the user's list of suggestions.
     * @param isApproved Boolean flag indicating whether the post is approved or not.
     * @return true if the approval process is successful, false otherwise.
     */
    @Override
    public boolean approve(User user, int postIndex, boolean isApproved) {
        if (user == null || user.getSuggestions().size() <= postIndex) {
            System.out.println("Invalid user or post index.");
            return false;
        }

        Post currentPost = user.getSuggestions().get(postIndex);
        Message approval = new Approval(isApproved);
        currentPost.addContent(approval);

        CampRepository campRepo = UnifiedCampRepository.getInstance();
        Camp camp = campRepo.retrieveCamp(currentPost.getCampName());
        if (camp == null) {
            System.out.println("Associated camp not found.");
            return false;
        }

        UserRepository userRepo = UnifiedUserRepository.getInstance();
        String suggesterID = currentPost.getFirstMessage().getPostedBy();
        User suggester = userRepo.retrieveUser(suggesterID);

        // Adds point if suggester is a committee member and the suggestion is approved
        if(suggester instanceof Committable && isApproved){
            System.out.println("1 point has been added to " + suggester.getName() + " for an approved suggestion.");
            camp.addPointToCommitteeMember(suggester.getUserID(), 1);
        }

        //Save changes
        camp.save();
        return true;
    }
}
