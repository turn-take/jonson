package jonson.queue;

import jonson.message.Message;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * アプリケーション内共通で利用するキュー
 */
public class MessageQueue {
    private static final Queue<Message> messagesQueue = new ConcurrentLinkedQueue<>();;

    /**
     * キューに追加する。
     * @param message
     */
    public static void offer(Message message) {
        messagesQueue.offer(message);
        System.out.println("The size of MessageQueue is " + messagesQueue.size());
    }

    /**
     * キューから要素を取り出す。
     * @return Message message
     */
    public static Message poll() {
        Message message = messagesQueue.poll();
        System.out.println("The size of MessageQueue is " + messagesQueue.size());
        return message;
    }

    /**
     * キューのサイズを取得する。
     * @return int size
     */
    public static int size() {
        return messagesQueue.size();
    }
}
