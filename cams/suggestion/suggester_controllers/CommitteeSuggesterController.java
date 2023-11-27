package cams.suggestion.suggester_controllers;

import cams.camp.Camp;
import cams.camp.CampRepository;
import cams.database.UnifiedCampRepository;
import cams.posts.post_entities.Post;
import cams.posts.post_entities.Suggestion;
import cams.posts.*;
import cams.suggestion.SuggesterUI;
import cams.users.Committable;
import cams.users.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class responsible for handling the submission, editing, and deletion of suggestions.
 * Extends the {@link SuggesterUI} and provides implementations for submit, edit, and delete methods.
 */
public class CommitteeSuggesterController implements SuggesterController, Serializable {
    private final CampRepository campRepo;

    public CommitteeSuggesterController(){
        this.campRepo = UnifiedCampRepository.getInstance();
    }

    /**
     * Submits a new suggestion to a specified camp.
     * <p>
     * This method creates a new post of type {@link PostType#SUGGESTION} and associates it with the specified camp.
     * If the user is a committee member, a point is added for the submission.
     *
     * @param campName The name of the camp to which the suggestion is being submitted.
     * @param user     The user submitting the suggestion.
     * @param text     The content of the suggestion.
     * @return true if the submission is successful, false if the camp does not exist.
     */
    @Override
    public boolean submit(String campName, User user, String text) {
        Post newPost = PostFactory.createPost(PostType.SUGGESTION);

        CampRepository repo = UnifiedCampRepository.getInstance();
        Camp camp = repo.retrieveCamp(campName);
        if(camp == null) {//if camp does not exist
            System.out.println("Camp does not exist.");
            return false;
        }

        Suggestion newSuggestion = (Suggestion) newPost.getFirstMessage();
        newSuggestion.setContent(text);
        newSuggestion.setPostedBy(user.getUserID());
        newPost.setCamp(campName);
        camp.addSuggestion(newPost);

        if(user instanceof Committable){
            System.out.println("1 point has been added.");
            camp.addPointToCommitteeMember(user.getUserID(), 1);
        }

        camp.save();
        return true;
    }

    /**
     * Edits an existing suggestion.
     * <p>
     * This method updates the content of an existing suggestion post.
     *
     * @param user      The user who owns the suggestion.
     * @param postIndex The index of the suggestion in the user's list of suggestions.
     * @param content   The new content for the suggestion.
     * @return true if the edit is successful, false if the user does not exist.
     */
    @Override
    public boolean edit(User user, int postIndex, String content) {
        if (user == null || user.getSuggestions().size() <= postIndex) {
            System.out.println("Invalid user or post index.");
            return false;
        }

        Post currentPost = user.getSuggestions().get(postIndex);

        if(currentPost.isReplied()) {
            System.out.println("Unable to delete suggestions that have been responded to.");
            return false; //reply exists
        }

        currentPost.getFirstMessage().setContent(content);

        CampRepository repo = UnifiedCampRepository.getInstance();
        Camp camp = repo.retrieveCamp(currentPost.getCampName());
        //Save changes
        camp.save();
        return true;
    }

    /**
     * Deletes a suggestion.
     * <p>
     * This method removes a suggestion post. Suggestions that have been responded to cannot be deleted.
     *
     * @param user      The user who owns the suggestion.
     * @param postIndex The index of the suggestion in the user's list of suggestions.
     * @return true if the deletion is successful, false if the user does not exist or the suggestion has responses.
     */
    @Override
    public boolean delete(User user, int postIndex) {
        if (user == null || user.getSuggestions().size() <= postIndex) {
            System.out.println("Invalid user or post index.");
            return false;
        }

        Post currentPost = user.getSuggestions().get(postIndex);

        if(currentPost.isReplied()) {
            System.out.println("Unable to delete suggestions that have been responded to.");
            return false; //reply exists
        }

        CampRepository repo = UnifiedCampRepository.getInstance();
        Camp camp = repo.retrieveCamp(currentPost.getCampName());
        camp.removePost(PostType.SUGGESTION, currentPost);
        //Save changes; posts are stored in camp, user does not store posts
        camp.save();
        return true;
    }

    /**
     * Collects all suggestions posted by the student user to
     * the camp that they are a committee member of.
     *
     * @return A list of {@link Post} objects representing suggestions.
     */
    public List<Post> getSuggestions(User user) {
        List<Post> mySuggestions = new ArrayList<>();

        if(!(user instanceof Committable))
            return mySuggestions;

        if(((Committable) user).isCommittee()){
            Camp camp = campRepo.retrieveCamp(((Committable) user).getCommittee());
            if(camp == null) {
                System.out.println("Camp not found.");
                return mySuggestions;
            }
            for (Post post : camp.getSuggestions()) {
                if (Objects.equals(post.getFirstMessage().getPostedBy(), user.getUserID())) {
                    mySuggestions.add(post);
                }
            }
        }
        return mySuggestions;
    }
}
