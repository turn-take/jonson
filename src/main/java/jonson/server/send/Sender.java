package jonson.server.send;

import jonson.json.MessageMapper;
import jonson.message.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
     * @param topicName 送信対象のトピック名
     * @param message メッセージ
     */
    public void notifyToSubscriber(String topicName, Message message) throws Exception{
        try {
            String data = MessageMapper.toJson(message);

            // 購読者を絞り出して送信
            List<Subscriber> filteredSubscribers =
                    subscribers.stream()
                            .filter(s -> !s.isCompleted())
                            .filter(s -> s.getTopicName().equals(topicName))
                            .collect(Collectors.toList());

            for (Subscriber filteredSubscriber : filteredSubscribers) {
                try {
                    filteredSubscriber.onNotify(data);
                } catch (Exception e) {
                    // 購読者内でエラーが発生した場合
                    filteredSubscriber.onException(e);
                }
            }
        } catch (IOException e) {
            // JSON形式への変換に失敗した場合
            throw new Exception(e.getMessage());
        }
    }
}
