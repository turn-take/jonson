package jonson.server.send;

import jonson.message.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * JSONメッセージの送信を行うクラス
 */
public class Sender {

    // 購読者の一覧
    private final List<Subscriber> subscribers = new ArrayList<>();

    public void register(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    /**
     * 購読者に通知を送る。
     * @param message メッセージ
     */
    public void notifySubscriber(String topicName, Message message) {
        subscribers.stream()
                .filter(Subscriber::isAlive)
                .filter(s -> s.getTopicName().equals(topicName))
                .forEach(s -> s.onNotify(message));
    }

    /**
     * 死んだ購読者には消えてもらう
     */
    public void clear() {
        subscribers.removeIf(Subscriber::isDead);
    }


}
