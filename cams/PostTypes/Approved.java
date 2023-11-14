package cams.PostTypes;

public class Approved extends Message{
    private String content;
    public Boolean getApproved() {
        return Boolean.valueOf(content);
    }

    public Boolean setContent(String approved) {
        content = approved;
        return true;
    }

    @Override
    public void displayContent() {
        System.out.printf("UserID " + this.getPostedBy() + ": ");
        if(this.getApproved())
            System.out.printf("Approved\n");
        else
            System.out.printf("Rejected\n");
    }
}
