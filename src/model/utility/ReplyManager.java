package model.utility;

import model.entity.Reply;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ReplyManager {

    private List<Reply> replies = new ArrayList<>();

    public int getRepliesCount() {
        return replies.size();
    }

    public Collection<Reply> getAllReplies() {
        return replies;
    }

    public boolean replyExists(Reply newReply) {
        if (replies.size() == 0)
            return false;
        return replies.stream().anyMatch(offer -> offer.compareTo(newReply) == 0);
    }

    public boolean add(Reply reply) {
        if (replyExists(reply))
            return false;
        replies.add(reply);
        return true;
    }

    public Reply getLowestReply() {
        if (replies.size() == 0)
            return null;
        return Collections.min(replies);
    }

    public double getLowestReplyValue() {
        Reply lowestReply = getLowestReply();
        if (lowestReply == null)
            return 0;
        return lowestReply.getValue();
    }

    public Reply getHighestReply() {
        if (replies.size() == 0)
            return null;
        return Collections.max(replies);
    }

    public double getHighestReplyValue() {
        Reply highestReply = getHighestReply();
        return highestReply == null ? 0 : highestReply.getValue();
    }

    public String getReplyDetails(boolean isAscending) {
        if (replies.size() == 0)
            return "Offer history: Empty";

        Collections.sort(replies, (offer1, offer2) -> {
            return offer1.compareTo(offer2);
        });

        if (!isAscending)
            Collections.reverse(replies);

        StringBuilder sb = new StringBuilder();
        sb.append("-- Offer History --\n");
        for (Reply reply : replies) {
            sb.append(String.format("%s: $%.2f\n", reply.getResponderId(), reply.getValue()));

        }
        return sb.toString();
    }
}

