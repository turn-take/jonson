package jonson.server.send;

import jonson.json.MessageMapper;
import jonson.log.JonsonLog;
import jonson.message.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * JSONメッセージの送信を行うクラス
 */
public class Sender {

    // 購読者の一覧
    private final List<Subscriber> subscribers = new ArrayList<>();

    // 購読者の登録を行う。
    public synchronized void register(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    // 購読者を削除する。
    public synchronized void remove(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    /**
     * トピックの購読者に通知を送る。
     * @param message メッセージ
     */
    public void notifyToSubscriber(String topicName, Message message) {
        try {
            String data = MessageMapper.toJson(message);
            // 並列送信
            subscribers.parallelStream()
                    .filter(s -> !s.isCompleted())
                    .filter(s -> s.getTopicName().equals(topicName))
                    .forEach(s -> s.onNotify(data));
        } catch (IOException e) {
            JonsonLog.error(e.getMessage(), e);
        }
    }
}
