package cams.enquiry.enquirer_controller;

import cams.users.User;

/**
 * The {@code EnquirerController} interface defines methods that allow
 * users with enquirer roles to submit, edit, and delete posts related to
 * a specific camp.
 */
public interface EnquirerController {

    /**
     * Submits a new post related to a specific camp.
     *
     * @param campName The name of the camp for which the post is submitted.
     * @param user     The user submitting the post.
     * @param text     The content of the submitted post.
     * @return {@code true} if the submission is successful, {@code false} otherwise.
     */
    boolean submit(String campName, User user, String text);

    /**
     * Edits the content of an existing post submitted by the user.
     *
     * @param user      The user editing the post.
     * @param postIndex The index of the post to be edited.
     * @param content   The new content for the post.
     * @return {@code true} if the edit is successful, {@code false} otherwise.
     */
    boolean edit(User user, int postIndex, String content);

    /**
     * Deletes an existing post submitted by the user.
     *
     * @param user      The user deleting the post.
     * @param postIndex The index of the post to be deleted.
     * @return {@code true} if the deletion is successful, {@code false} otherwise.
     */
    boolean delete(User user, int postIndex);

    /**
     * Interface to display the enquiries of the user and returns the count of those enquiries.
     * Each enquiry is displayed with its index and content.
     *
     * @param user The user whose enquiries are to be displayed.
     * @return The number of enquiries displayed. 0 = no enquiries by user. -1 = no camps.
     */
    int view(User user);
}
