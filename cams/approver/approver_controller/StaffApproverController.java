package cams.approver.approver_controller;

import cams.camp.Camp;
import cams.camp.CampRepository;
import cams.database.UnifiedCampRepository;
import cams.database.UnifiedUserRepository;
import cams.posts.post_entities.Approval;
import cams.posts.post_entities.Message;
import cams.posts.post_entities.Post;
import cams.users.UserRepository;
import cams.users.Committable;
import cams.users.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible for handling the approval process of posts.
 * Extends the ApproverUI and provides implementation for the {@link #approve(User, int, boolean) approve}  method.
 */
public class StaffApproverController implements ApproverController, Serializable {
    public StaffApproverController(){
    }
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

        // Check if the post already has a response
        if(currentPost.isReplied()) {
            System.out.println("Response already exist, unable to respond again!");
            return false;
        }

        // Create and add the reply to the post
        Message approval = new Approval(user.getUserID(), isApproved);
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

        if (suggester == null) {
            System.out.println("Associated suggester not found.");
            return false;
        }

        // Adds point if suggester is a committee member and the suggestion is approved
        if(suggester instanceof Committable && isApproved){
            System.out.println("1 point has been added to " + suggester.getName() + " for an approved suggestion.");
            camp.addPointToCommitteeMember(suggester.getUserID(), 1);
        }

        //Save changes
        camp.save();
        return true;
    }

    /**
     * Collects all suggestions related to the camps created by the staff member.
     *
     * @return A list of {@link Post} objects representing suggestions.
     */
    public List<Post> getSuggestions(User user) {
        List<Post> mySuggestions = new ArrayList<>();
        if (user.getMyCamps() == null) {
            System.out.println("No camps associated with the user.");
            return mySuggestions;
        }

        for (String campName : user.getMyCamps()) {
            if (campName == null) continue; // Skip if campName is null

            CampRepository campRepo = UnifiedCampRepository.getInstance();
            Camp camp = campRepo.retrieveCamp(campName);
            if (camp == null) continue; // Skip if camp is null

            if(camp.getSuggestions() == null) continue;

            mySuggestions.addAll(camp.getSuggestions());
        }

        return mySuggestions;
    }
}
