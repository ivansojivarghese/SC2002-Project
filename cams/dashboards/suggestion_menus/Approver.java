package cams.dashboards.suggestion_menus;

import cams.Camp;
import cams.database.CampRepository;
import cams.database.UnifiedCampRepository;
import cams.post_types.Approval;
import cams.post_types.Message;
import cams.post_types.Post;
import cams.users.User;
import cams.util.SerializeUtility;

import java.util.InputMismatchException;

public class Approver extends ApproverUI {
    @Override
    public boolean approve(User user, int postIndex, boolean isApproved) {
        Post currentPost = user.getSuggestions().get(postIndex);
        Message approval = new Approval(isApproved);
        currentPost.addContent(approval);

        CampRepository repo = UnifiedCampRepository.getInstance();
        Camp camp = repo.retrieveCamp(currentPost.getCampName());
        //Save changes
        SerializeUtility.saveObject(camp, camp.getFolderName(), camp.getFileName());
        return true;
    }
}
