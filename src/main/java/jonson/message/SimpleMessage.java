package jonson.message;

import lombok.Getter;
import lombok.Setter;

/**
 * 単純なメッセージ
 */
public class SimpleMessage extends Message{

    public SimpleMessage(String topicName, String message) {
        super(topicName);
        this.message = message;
    }

    @Getter
    @Setter
    private String message;
}
