package cams.SuggestionHandler;

import cams.Camp;
import cams.PostTypes.*;
import cams.User;

public class Suggester extends SuggesterUI{
    @Override
    public int submit(String campName, String userID, String text) {
        Post newPost = PostFactory.createPost(PostType.SUGGESTION);
        Suggestion newSuggestion = (Suggestion) newPost.getFirstMessage();
        if(newSuggestion.setContent(text) && newSuggestion.setPostedBy(userID) && newPost.setCamp(campName))
            return 1; //successful
        return -1; //unsuccessful
    }

    @Override
    public int edit(User user, int postIndex, String content) {
        if(user == null)
            return -1; //user does not exist
        Post currentPost = user.getSuggestions().get(postIndex);
        currentPost.getFirstMessage().setContent(content);
        return 1;
    }

    @Override
    public int delete(User user, int postIndex) {
        if(user == null)
            return -1; //user does not exist

        Post currentPost = user.getSuggestions().get(postIndex);

        if(currentPost.isReplied())
            return -2; //reply exists

        Camp camp = currentPost.getCamp();
        camp.removePost(PostType.SUGGESTION, currentPost);
        //User does not store their posts
        return 1;
    }
}
