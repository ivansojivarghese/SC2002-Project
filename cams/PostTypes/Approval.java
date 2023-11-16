package cams.PostTypes;

public class Approval extends Message{
    private Boolean content;

    public Approval(){}
    public Approval(Boolean isApproved){
        this.content = isApproved;
    }
    public Boolean getApproved() {
        return this.content;
    }

    public String validateInput(String approved){
        if ("1".equalsIgnoreCase(approved) || "yes".equalsIgnoreCase(approved) ||
                "true".equalsIgnoreCase(approved)) {
            return "true";
        }
        else if ("0".equalsIgnoreCase(approved) || "no".equalsIgnoreCase(approved) ||
                "false".equalsIgnoreCase(approved)) {
            return "false";
        }
        else
            return "error";
    }

    public Boolean setContent(Boolean approved){
        this.content = approved;
        return true;
    }

    public Boolean setContent(String approved) {return true;}

    @Override
    public void displayContent() {
        System.out.printf("UserID " + this.getPostedBy() + ": ");
        if(this.getApproved())
            System.out.printf("Approved\n");
        else
            System.out.printf("Rejected\n");
        System.out.println();
    }
}
