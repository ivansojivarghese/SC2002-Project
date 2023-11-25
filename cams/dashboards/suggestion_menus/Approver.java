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

public class Approver extends ApproverUI {
    @Override
    public boolean approve(User user, int postIndex, boolean isApproved) {
        Post currentPost = user.getSuggestions().get(postIndex);
        Message approval = new Approval(isApproved);
        currentPost.addContent(approval);

        CampRepository campRepo = UnifiedCampRepository.getInstance();
        Camp camp = campRepo.retrieveCamp(currentPost.getCampName());
        UserRepository userRepo = UnifiedUserRepository.getInstance();
        String suggesterID = currentPost.getFirstMessage().getPostedBy();
        User suggester = userRepo.retrieveUser(suggesterID);

        if(suggester instanceof Committable && isApproved){
            System.out.println("1 point has been added to " + suggester.getName() + " for an approved suggestion.");
            camp.addPointToCommitteeMember(suggester.getUserID(), 1);
        }
        //Save changes
        camp.save();
        return true;
    }
}
