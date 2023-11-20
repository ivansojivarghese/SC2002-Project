package cams.database;
import cams.users.User;

import java.util.HashMap;

public class UnifiedUserRepository {
    private static UnifiedUserRepository instance;
    private HashMap<String, User> users;

    //prevent construction outside the class
    private UnifiedUserRepository(){
        this.users = null;
    }

    public void addUser(User user){
        this.users.put(user.getUserID(), user);
    }
    public User retrieveUser(String userID){
        return users.get(userID);
    }
    
    public boolean isEmpty() {
    	if (this.users != null) {
    		return this.users.isEmpty();
    	} else {
    		return true;
    	}
    }

    public static UnifiedUserRepository getInstance() {
        // If the instance is null, create a new one
        if (instance == null) {
            instance = new UnifiedUserRepository();
        }
        // Return the existing/new instance
        return instance;
    }
}
