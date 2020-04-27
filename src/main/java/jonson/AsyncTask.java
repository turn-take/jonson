package jonson;

import jonson.log.JonsonLog;
import jonson.message.Message;
import jonson.queue.MessageQueue;
import jonson.queue.TopicQueue;
import jonson.server.send.Sender;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 非同期のタスク
 */
public class AsyncTask implements Runnable{

    private final Sender sender;

    public AsyncTask(Sender sender) {
        this.sender = sender;
    }

    /**
     * 非同期でキューにあるメッセージを送信し続ける
     */
    @Override
    public void run() {
        while (true) {
            Set<Map.Entry<String,TopicQueue>> topicQueueSet =
                MessageQueue.getInstance().get().entrySet()
                        .stream()
                        .filter(entry -> !entry.getValue().isEmpty())
                        .collect(Collectors.toSet());
                    //.forEach(entry -> {
                      //  sender.notifyToSubscriber(entry.getKey(), entry.getValue().poll().get());
            //});
            for (Map.Entry<String, TopicQueue> entry : topicQueueSet) {
                try {

                    String topicName = entry.getKey();
                    TopicQueue topicQueue = entry.getValue();

                    // 例外が発生する可能性があるのでget()する。
                    if(topicQueue.poll().isPresent()) {
                        sender.notifyToSubscriber(topicName, topicQueue.poll().get());
                    }
                } catch (Exception e) {
                    // このスレッド内のエラーはログの出力だけにする。
                    JonsonLog.error(e.getMessage(), e);
                }

            }
        }

    }
}
