package cams.users;

import cams.post_types.Post;

import java.util.List;

public interface Enquirer {
    /**
     * Collects all enquiries posted by the student to camps.
     *
     * @return A list of {@link Post} objects representing enquiries.
     */
    public List<Post> getEnquiries();

    /**
     * Adds an enquiry to the student's myEnquiries attribute.
     */
    public void addEnquiry(Post post);

    /**
     * Removes specified post from the user
     * @param post Post to be removed.
     * @return true
     */
    public boolean removeEnquiry(Post post);

}
