package jonson.queue;

import jonson.message.Message;
import jonson.message.OnLoginMessage;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * メッセージをキャッシュするキュー
 * 外部的にはキューとして利用できるが内部的にはMap
 * アプリケーション共通で利用するキューのため。
 * アプリケーションのどこからでもアクセスできるようにするためにシングルトンにしている。
 */
public class MessageQueue {
    private static final MessageQueue instance = new MessageQueue();

    // 唯一のインスタンス
    public static MessageQueue getInstance() {
        return instance;
    }

    // トピック毎にキューを保管するMap
    private final Map<String, TopicQueue> map;

    // シングルトン
    private MessageQueue() {
        this.map  = new ConcurrentHashMap<>();
    }

    /**
     * キューに追加する。
     */
    public void offer(OnLoginMessage message) {

        String topicName = message.getTopicName();

        // キューが存在しない場合は生成してmapに追加
        if(!map.containsKey(topicName)) {
            TopicQueue topicQueue = new TopicQueue(topicName);
            map.put(topicName, topicQueue);
        }

        map.get(topicName).offer(message);

    }

    /**
     * トピック名を指定してキューから要素を取り出す。(FIFO)
     * @return Optional<Message>
     */
    public Optional<Message> poll(String topicName) {
        TopicQueue queue = map.get(topicName);
        Optional<Message> message = queue.poll();
        return message;
    }

    /**
     * 保管している全てのキューを取得する。
     * @return Map<String , TopicQueue> キューのマップ
     */
    public Map<String , TopicQueue> get() {
        return map;
    }

}
