package cams.PostTypes;

public class Approval extends Message{
    private String content;

    public Approval(){}
    public Approval(String isApproved){
        if(this.setContent(isApproved)){
            return;
        }
        else
            this.setContent(null);
    }
    public Boolean getApproved() {
        return Boolean.valueOf(content);
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

    public Boolean setContent(String approved) {
        if (!(this.validateInput(approved).equalsIgnoreCase("error"))) {
            content = this.validateInput(approved);
            return true;
        }
        else
            return false;
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
