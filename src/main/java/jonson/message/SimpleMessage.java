package jonson.message;

import jonson.queue.MessageQueue;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 単純なメッセージ
 */
public class SimpleMessage implements Message, Serializable {

    public SimpleMessage(String message) {
        this.message = message;
    }

    @Getter
    @Setter
    private String message;
}
