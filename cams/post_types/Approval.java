package cams.post_types;

import java.io.Serializable;

public class Approval extends Message implements Serializable {
    private Boolean content;

    public Approval(){}
    public Approval(Boolean isApproved){
        this.content = isApproved;
    }
    public Boolean getApproved() {
        return this.content;
    }

    public Boolean setContent(Boolean approved){
        this.content = approved;
        return true;
    }

    public void setContent(String approved) {}

    @Override
    public void displayContent() {
        System.out.printf("UserID " + this.getPostedBy() + ": ");
        if(this.getApproved())
            System.out.print("Approved\n");
        else
            System.out.print("Rejected\n");
        System.out.println();
    }
}
