package jonson.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public abstract class Message implements Serializable {

    @Getter
    @Setter
    @JsonIgnore
    // トピック名
    private String topicName;

    public Message(String topicName) {
        this.topicName = topicName;
    }
}
