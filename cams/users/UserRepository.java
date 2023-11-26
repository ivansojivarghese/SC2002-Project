package cams.users;

import cams.users.User;

/**
 * Defines an interface with a shared body of methods for managing a user repository.
 * <p>Such as adding and removing users from the repository
 * and checking if the repository is empty</p>
 *
 */
public interface UserRepository {
        /**
         * Adds a new user to the repository.
         *
         * @param user The {@link User} object to add.
         */
        void addUser(User user);
        /**
         * Adds a new user to the repository.
         *
         * @param userID The String value of the userID attribute of the {@link User} object to add.
         */
        User retrieveUser(String userID);

        /**
         * Checks if the repository is empty.
         *
         * @return true if the repository contains no users, false otherwise.
         */
        boolean isEmpty();
}
