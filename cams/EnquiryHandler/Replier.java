package cams.EnquiryHandler;

import cams.PostTypes.Message;
import cams.PostTypes.Post;
import cams.PostTypes.Reply;
import cams.User;

public class Replier extends ReplierUI{
    @Override
    public int reply(User user, int postIndex, String content) {
        Post post = user.getEnquiries().get(postIndex);
        if(post.isReplied())
            return -1;
        Message reply = new Reply(user.getUserID(), content);
        post.addContent(reply);
        return 1;
    }
}
