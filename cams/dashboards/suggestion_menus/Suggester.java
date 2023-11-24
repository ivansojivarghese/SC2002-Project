package cams.dashboards.suggestion_menus;

import cams.Camp;
import cams.database.CampRepository;
import cams.database.UnifiedCampRepository;
import cams.post_types.*;
import cams.users.User;

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
        repo.retrieveCamp(currentPost.getCampName()).removePost(PostType.SUGGESTION, currentPost);
        //User does not store their posts
        return true;
    }
}
