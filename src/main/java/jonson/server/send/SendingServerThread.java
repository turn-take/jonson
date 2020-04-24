package jonson.server.send;

import jonson.io.InputStreamParser;
import jonson.log.SendingLog;
import jonson.net.SocketHandler;

import java.io.*;
import java.util.concurrent.CountDownLatch;

/**
 * 送信側のサーバーで動くスレッドクラス
 * ソケットからトピックの購読者を作成し、購読完了までスレッドを待機させる。
 */
class SendingServerThread implements Runnable{
    private final SocketHandler socketHandler;
    private final Sender sender;

    private final CountDownLatch countDownLatch = new CountDownLatch(1);

    SendingServerThread(SocketHandler socketHandler, Sender sender) {
        this.socketHandler = socketHandler;
        this.sender = sender;
        SendingLog.info("connected : "
                + socketHandler.getRemoteSocketAddress());
    }

    public void run() {
        try {

            String topicName = InputStreamParser.parseToString(socketHandler.getInputStream());

            SendingLog.info("topic : " + topicName);

            // 購読者生成
            Subscriber subscriber = new Subscriber(socketHandler, topicName, countDownLatch);

            // 購読者登録
            sender.register(subscriber);

            // 購読完了まで待機する
            countDownLatch.await();

            // 購読者を削除
            sender.remove(subscriber);

        } catch (Exception e) {
            SendingLog.error(e.getMessage() , e);
        } finally {
            try {
                if (!socketHandler.isSocketClosed()) {
                    socketHandler.close();
                }
            } catch (IOException e) {
                SendingLog.error(e.getMessage(),e);
            }
            SendingLog.info("disconnected : "
                    + socketHandler.getRemoteSocketAddress());
        }
    }
}

