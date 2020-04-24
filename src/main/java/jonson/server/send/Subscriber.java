package jonson.server.send;

import jonson.log.SendingLog;
import jonson.net.SocketHandler;
import lombok.Getter;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * メッセージの購読者
 * ソケットと1:1の関係
 */
public class Subscriber {

    private final SocketHandler socketHandler;

    @Getter
    // 購読するトピック名
    private final String topicName;

    private final CountDownLatch countDownLatch;

    private boolean completed;

    public Subscriber(SocketHandler socketHandler, String topicName, CountDownLatch countDownLatch) {
        this.socketHandler = socketHandler;
        this.topicName = topicName;
        this.countDownLatch = countDownLatch;
        completed = false;
    }

    /**
     * 通知を受ける。
     * @param data 送信するデータ
     */
    public synchronized void onNotify(String data) {
        subscribe(data);
    }

    /**
     * データを購読する。
     * @param data　購読するデータ
     */
    private void subscribe(String data) {
        socketHandler.send(data);
        complete();
    }

    /**
     * 購読を完了する。
     */
    private void complete() {
        try {
            socketHandler.close();
        } catch (IOException e) {
            SendingLog.error(e.getMessage(), e);
        }finally {
            completed = true;
            // ここまで来たらスレッドの待ち合わせを終了する。
            countDownLatch.countDown();
        }
    }

    /**
     * 購読が完了しているかを返す。
     * @return boolean
     */
    public boolean isCompleted() {
        return completed;
    }
}
