package cams.post_types;

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
            System.out.print("Approved\n");
        else
            System.out.print("Rejected\n");
        System.out.println();
    }
}
