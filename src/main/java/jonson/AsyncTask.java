package jonson;

import jonson.queue.MessageQueue;
import jonson.server.send.Sender;

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
            MessageQueue.getInstance().get().entrySet()
                    .parallelStream()
                    .filter(entry -> !entry.getValue().isEmpty())
                    .forEach(entry -> {
                        sender.notifyToSubscriber(entry.getKey(), entry.getValue().poll().get());
            });
        }

    }
}
