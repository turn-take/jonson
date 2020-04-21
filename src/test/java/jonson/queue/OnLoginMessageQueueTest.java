package jonson.queue;

import jonson.message.OnLoginMessage;
import jonson.message.SimpleOnLoginMessage;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class OnLoginMessageQueueTest {

//    @Test
//    public void 要素の追加と取得が出来る() {
//        String topicName = "test01";
//        OnLoginMessage onLoginMessage = new SimpleOnLoginMessage(topicName, "test01");
//        MessageQueue.offer(onLoginMessage.getTopicName(), onLoginMessage);
//
//        String topicName2 = "test02";
//        OnLoginMessage onLoginMessage2 = new SimpleOnLoginMessage(topicName2, "test02");
//        MessageQueue.offer(onLoginMessage2.getTopicName(), onLoginMessage2);
//
//        Optional<OnLoginMessage> optional = MessageQueue.poll(topicName);
//        assertDoesNotThrow(() -> optional.get());
//        assertEquals(onLoginMessage, optional.get());
//        assertEquals(topicName, optional.get().getTopicName());
//    }

}
