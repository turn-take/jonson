package jonson.queue;

import jonson.message.Message;
import lombok.Getter;

import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * トピック名毎に生成されるキュー
 */
public class TopicQueue {

    // トピック名
    @Getter
    private final String topicName;

    // キュー本体
    @Getter
    private final Queue<Message> queue;

    // トピック名を指定してキューを作成する。
    TopicQueue(String topicName) {
        this.topicName = topicName;
        this.queue = new ConcurrentLinkedQueue<>();
    }

    /**
     * キューに要素を追加する。
     * @param message メッセージ
     */
    public void offer(Message message) {
        queue.offer(message);
    }

    /**
     * キューから要素をポップする。
     * @return optional<Message> nullかもしれないOptional
     */
    public Optional<Message> poll() {
        return Optional.ofNullable(queue.poll());
    }

    /**
     * キューが空かを返す
     * @return boolean
     */
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
