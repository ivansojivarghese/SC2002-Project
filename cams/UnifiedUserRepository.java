package cams;
import java.util.HashMap;

public class UnifiedUserRepository {
    private static UnifiedUserRepository instance;
    private HashMap<String, User> Users;

        //prevent construction outside the class
        private UnifiedUserRepository(){}

        public void addUser(User user){
            this.Users.put(user.getUserID(), user);
        }
        public User retrieveUser(String userID){
            return Users.get(userID);
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
