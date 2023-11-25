package cams.users;

//Interface for users that can join committees
public interface Committable {
    boolean isCommittee();
    void setCommittee(String committee);
    String getCommittee();
}
