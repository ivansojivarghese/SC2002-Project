package cams.controllers.enquirer;

import cams.users.User;

public interface EnquirerController {
    boolean submit(String campName, User user, String text);
    boolean edit(User user, int postIndex, String content);
    boolean delete(User user, int postIndex);
}
