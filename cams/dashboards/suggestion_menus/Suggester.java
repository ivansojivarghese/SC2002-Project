package cams.dashboards.suggestion_menus;

import cams.Camp;
import cams.database.CampRepository;
import cams.database.UnifiedCampRepository;
import cams.post_types.*;
import cams.users.User;
import cams.util.SerializeUtility;

public class Suggester extends SuggesterUI{
    @Override
    public boolean submit(String campName, String userID, String text) {
        Post newPost = PostFactory.createPost(PostType.SUGGESTION);

        CampRepository repo = UnifiedCampRepository.getInstance();
        Camp camp = repo.retrieveCamp(campName);
        if(camp == null) {//if camp does not exist
            System.out.println("Camp does not exist.");
            return false;
        }
        Suggestion newSuggestion = (Suggestion) newPost.getFirstMessage();
        newSuggestion.setContent(text);
        newSuggestion.setPostedBy(userID);
        newPost.setCamp(campName);
        camp.addEnquiry(newPost);
        return true;
    }

    @Override
    public boolean edit(User user, int postIndex, String content) {
        if(user == null) {
            System.out.println("User does not exist.");
            return false; //user does not exist
        }
        Post currentPost = user.getSuggestions().get(postIndex);
        currentPost.getFirstMessage().setContent(content);

        CampRepository repo = UnifiedCampRepository.getInstance();
        Camp camp = repo.retrieveCamp(currentPost.getCampName());
        //Save changes
        SerializeUtility.saveObject(camp, camp.getFolderName(), camp.getFileName());
        return true;
    }

    @Override
    public boolean delete(User user, int postIndex) {
        if(user == null)
            return false; //user does not exist

        Post currentPost = user.getSuggestions().get(postIndex);

        if(currentPost.isReplied()) {
            System.out.println("Unable to delete suggestions that have been responded to.");
            return false; //reply exists
        }

        CampRepository repo = UnifiedCampRepository.getInstance();
        Camp camp = repo.retrieveCamp(currentPost.getCampName());
        camp.removePost(PostType.SUGGESTION, currentPost);
        //Save changes; posts are stored in camp, user does not store posts
        SerializeUtility.saveObject(camp, camp.getFolderName(), camp.getFileName());
        return true;
    }
}
