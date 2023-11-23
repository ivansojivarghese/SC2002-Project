package cams.dashboards.enquiry_menus;

import cams.post_types.Message;
import cams.post_types.Post;
import cams.post_types.Reply;
import cams.users.User;

public class Replier extends ReplierUI{
    @Override
    public int reply(User user, int postIndex, String content) {
        Post post = user.getEnquiries().get(postIndex);
        if(post.isReplied())
            return 0;
        Message reply = new Reply(user.getUserID(), content);
        post.addContent(reply);
        return 1;
    }
}
