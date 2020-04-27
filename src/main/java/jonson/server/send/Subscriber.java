package jonson.server.send;

import jonson.net.SocketHandler;
import lombok.Getter;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

/**
 * 購読者クラス
 * 送信側サーバーで生成される。
 */
public class Subscriber {

    private final SocketHandler socketHandler;

    @Getter
    // 購読するトピック名
    private final String topicName;

    private final CountDownLatch countDownLatch;

    private boolean completed;

    private Exception exception = null;

    Subscriber(SocketHandler socketHandler, String topicName, CountDownLatch countDownLatch) {
        this.socketHandler = socketHandler;
        this.topicName = topicName;
        this.countDownLatch = countDownLatch;
        completed = false;
    }

    /**
     * 通知を受ける。
     * @param data 送信するデータ
     */
    public synchronized void onNotify(String data) throws IOException{
        subscribe(data);
    }

    /**
     * データを購読する。
     * @param data　購読するデータ
     */
    private void subscribe(String data) throws IOException {
        socketHandler.send(data);
        complete();
    }

    /**
     * 購読を完了する。
     */
    private void complete() {
        completed = true;
        // ここまで来たらスレッドの待ち合わせを終了する。
        countDownLatch.countDown();
    }

    /**
     * 購読が完了しているかを返す。
     * @return boolean
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * 例外発生時に呼び出す。
     * 例外をフィールドに設定し、スレッドの待ち合わせを終了する。
     * @param e 発生した例外
     */
    public void onException(Exception e) {
        this.exception = e;
        countDownLatch.countDown();
    }

    /**
     * 例外を取得する。
     * 例外を保持していない場合はnullをラップして返す
     * @return 例外をラップしたOptional
     */
    public Optional<Exception> getException() {
        return Optional.ofNullable(this.exception);
    }
}
