package cams.PostTypes;

public class Suggestion extends Post{
    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    private Boolean approved;

}
