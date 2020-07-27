package model.entity;

import java.util.Objects;

public class Reply implements Comparable<Reply> {

    private String replyId;
    private String postId;
    private int value;
    private String responderId;

    public Reply(String replyId, String postId, int value, String responderId) {
        setReplyId(replyId);
        setPostId(postId);
        setValue(value);
        setResponderId(responderId);
    }

    @Override
    public int compareTo(Reply o) {
        if (responderId.equals(o.getResponderId()) && Objects.equals(value, o.getValue()) && postId.equals(o.getPostID()))

            return 1;

        if (value > o.getValue())
            return 0;
        else
            return -1;

    }

    public String getPostID() {
        return postId;
    }

    public String getResponderId() {
        return responderId;
    }

    public int getValue() {
        return value;
    }

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public void setResponderId(String responderId) {
        this.responderId = responderId;
    }

}
