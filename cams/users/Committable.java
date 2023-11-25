package cams.users;

/**
 * Interface for users that can join committees.
 * Defines basic methods to check if a user is part of a committee, get their committee assignment, and set their committee.
 */
public interface Committable {
    /**
     * Checks if the user is a member of a committee.
     *
     * @return true if the user is part of a committee, false otherwise.
     */
    boolean isCommittee();

    /**
     * Sets the committee that the user is a member of.
     *
     * @param committee The name of the committee to be assigned to the user.
     */
    void setCommittee(String committee);

    /**
     * Retrieves the name of the committee that the user is a member of.
     *
     * @return The name of the committee, or null if the user is not a member of any committee.
     */
    String getCommittee();
}
