package jonson.message;

import lombok.Getter;
import lombok.Setter;

/**
 * 単純なメッセージ
 */
public class SimpleOnLoginMessage extends OnLoginMessage {

    public SimpleOnLoginMessage(String topicName, long sessionId, String message) {
        super(topicName,sessionId);
        this.message = message;
    }

    @Getter
    @Setter
    private String message;
}
