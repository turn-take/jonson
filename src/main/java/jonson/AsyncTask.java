package jonson;

import jonson.queue.MessageQueue;
import jonson.server.send.Sender;

public class AsyncTask implements Runnable{

    private final Sender sender;

    public AsyncTask(Sender sender) {
        this.sender = sender;
    }

    /**
     * 非同期でキューを送信し続ける
     */
    @Override
    public void run() {
        while (true) {
            MessageQueue.getInstance().get().entrySet()
                    .stream()
                    .filter(entry -> !entry.getValue().isEmpty())
                    .forEach(entry -> {
                        sender.notifySubscriber(entry.getKey(), entry.getValue().poll().get());
            });
            sender.clear();
        }

    }
}
