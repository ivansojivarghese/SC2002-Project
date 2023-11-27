package cams.suggestion.suggester_controllers;

import cams.post_types.Post;
import cams.users.User;

import java.util.List;

/**
 * The {@code SuggesterController} interface defines methods that allow
 * users with suggester roles to submit, edit, and delete suggestions related
 * to a specific camp, as well as retrieve a list of suggestions.
 */
public interface SuggesterController {

    /**
     * Submits a new suggestion related to a specific camp.
     *
     * @param camp The name of the camp for which the suggestion is submitted.
     * @param user The user submitting the suggestion.
     * @param text The content of the submitted suggestion.
     * @return {@code true} if the submission is successful, {@code false} otherwise.
     */
    boolean submit(String camp, User user, String text);

    /**
     * Edits the content of an existing suggestion submitted by the user.
     *
     * @param user      The user editing the suggestion.
     * @param postIndex The index of the suggestion to be edited.
     * @param content   The new content for the suggestion.
     * @return {@code true} if the edit is successful, {@code false} otherwise.
     */
    boolean edit(User user, int postIndex, String content);

    /**
     * Deletes an existing suggestion submitted by the user.
     *
     * @param user      The user deleting the suggestion.
     * @param postIndex The index of the suggestion to be deleted.
     * @return {@code true} if the deletion is successful, {@code false} otherwise.
     */
    boolean delete(User user, int postIndex);

    /**
     * Retrieves a list of suggestions submitted by the user.
     *
     * @param user The user requesting their list of suggestions.
     * @return A {@code List} of {@code Post} objects representing suggestions.
     */
    List<Post> getSuggestions(User user);
}
