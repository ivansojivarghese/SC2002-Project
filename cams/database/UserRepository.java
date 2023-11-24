package cams.database;

import cams.users.User;

public interface UserRepository {
        void addUser(User user);
        //UserID is always uppercase to remove case sensitivity
        User retrieveUser(String userID);
        boolean isEmpty();
}
