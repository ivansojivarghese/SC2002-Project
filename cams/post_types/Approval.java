package cams.post_types;

import java.io.Serializable;

/**
 * Represents an approval message, extending {@link Message} and implementing {@link Serializable}.
 * Holds information about whether a particular action or request is approved or rejected.
 */
public class Approval extends Message implements Serializable {
    private Boolean content;

    /**
     * Default constructor for Approval.
     */
    public Approval(){}

    /**
     * Constructs an Approval instance with specified approval status.
     *
     * @param isApproved The approval status (true for approved, false for rejected).
     */
    public Approval(String userID, Boolean isApproved) {
        this.content = isApproved;
        this.setPostedBy(userID);
    }

    /**
     * Retrieves the approval status of this message.
     *
     * @return true if approved, false if rejected.
     */
    public Boolean getApproved() {
        return this.content;
    }

    /**
     * Sets the approval status of this message.
     *
     * @param approved The approval status to set.
     * @return true
     */
    public Boolean setContent(Boolean approved){
        this.content = approved;
        return true;
    }

    /**
     * This method is not implemented and should not be called.
     *
     * @param approved The approval status as a string.
     * @throws UnsupportedOperationException if this method is called.
     */
    @Override
    public void setContent(String approved) {
        throw new UnsupportedOperationException("setContent(String approved) is not implemented and should not be called.");
    }

    /**
     * Displays the content of this approval or rejection message.
     * <p>
     * Prints out the userID of the person in charge of approving or rejecting the post
     * and whether the content is approved or rejected.
     */
    @Override
    public void displayContent() {
        System.out.printf("UserID " + this.getPostedBy() + ": ");
        System.out.println(this.getApproved() ? "Approved" : "Rejected");
        System.out.println();
    }
}
