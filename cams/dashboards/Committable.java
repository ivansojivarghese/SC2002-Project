package cams.dashboards;

//Interface for users that can join committees
public interface Committable {
    public boolean isCommittee();
    public void setCommittee(String committee);
    public String getCommittee();
}
