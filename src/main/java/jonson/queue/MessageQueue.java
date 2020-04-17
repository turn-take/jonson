package jonson.queue;

import jonson.message.Message;

import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * アプリケーション内共通で利用するキュー
 */
public class MessageQueue {
    private static final Map<String, Queue<Message>> topicNameMap = new ConcurrentHashMap<>();

    /**
     * トピック名を指定してキューに追加する。
     * @param message
     */
    public static void offer(String topicName, Message message) {
        if(!topicNameMap.containsKey(topicName)) {
            Queue<Message> queue = new ConcurrentLinkedQueue<>();
            topicNameMap.put(topicName, queue);
        }
        Queue<Message> queue = topicNameMap.get(topicName);
        queue.offer(message);
    }

    /**
     * トピック名を指定してキューから要素を取り出す。(FIFO)
     * @return Optional<Message>
     */
    public static Optional<Message> poll(String topicName) {
        Queue<Message> queue = topicNameMap.get(topicName);
        Optional<Message> message = Optional.of(queue.poll());
        return message;
    }
}
