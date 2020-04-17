package jonson.queue;

import jonson.message.Message;
import jonson.message.SimpleMessage;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class MessageQueueTest {

    @Test
    public void 要素の追加と取得が出来る() {
        String topicName = "test01";
        Message message = new SimpleMessage(topicName, "test01");
        MessageQueue.offer(message.getTopicName(), message);

        String topicName2 = "test02";
        Message message2 = new SimpleMessage(topicName2, "test02");
        MessageQueue.offer(message2.getTopicName(), message2);

        Optional<Message> optional = MessageQueue.poll(topicName);
        assertDoesNotThrow(() -> optional.get());
        assertEquals(message, optional.get());
        assertEquals(topicName, optional.get().getTopicName());
    }

}
