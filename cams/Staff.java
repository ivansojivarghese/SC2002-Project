package cams;

public class Staff extends App {
	public String name;
    public String userID;
    public String faculty;
    protected String password;
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(int identity, String password) {
    	staffArr[identity].password = password;
    }
 
    // Student class constructor
    Staff(String name, String userID, String faculty, String password, int identity)
    {
        this.name = name;
        this.userID = userID;
        this.faculty = faculty;
        if (password == null) {        	
        	this.password = "password";
        } else {        	
        	setPassword(identity, password);
        }
    }
}

class NewPasswordStaff extends Staff {
    NewPasswordStaff(String name, String userID, String faculty, String password, int identity) {
		super(name, userID, faculty, password, identity);
		// TODO Auto-generated constructor stub
	}

	public void setPassword(String password) {
    	this.password = password;
    }
}
