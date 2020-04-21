package jonson.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public abstract class OnLoginMessage implements Message{

    @Getter
    @JsonIgnore
    // トピック名
    private final String topicName;

    @Getter
    @JsonIgnore
    private final long sessionId;

    public OnLoginMessage(String topicName, long sessionId) {
        this.topicName = topicName;
        this.sessionId = sessionId;
    }
}
