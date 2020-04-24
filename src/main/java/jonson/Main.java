package jonson;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonLocation;
import jonson.log.JonsonLog;
import jonson.server.receive.ReceivingServer;
import jonson.server.send.Sender;
import jonson.server.send.SendingServer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    // 受信側サービス
    private final static ExecutorService receivingService = Executors.newSingleThreadExecutor();
    // 送信側サービス
    private final static ExecutorService sendingService = Executors.newSingleThreadExecutor();

    // アプリケーション終了待機用カウントダウン
    public final static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) {
        try {
            JonsonLog.info("Launch the application.");

            // 設定ファイル読み込み
            PropertiesUtil.getInstance().load();

            // 受信側サーバー起動
            launchReceivingServer();

            // メッセージ送信インスタンス生成
            Sender sender = new Sender();

            // 送信側サーバー起動
            launchSendingServer(sender);

            // 非同期タスクの実行
            AsyncTask task = new AsyncTask(sender);
            Thread thread = new Thread(task);
            thread.start();

            JonsonLog.info("Application has been launched.");

            // カウントダウンが0になったらアプリケーション終了
            countDownLatch.await();
        } catch (Exception e) {
            JonsonLog.error("Error has occurred in application.", e);
        } finally {
            JonsonLog.info("Exit the application.");
            receivingService.shutdown();
            sendingService.shutdown();
            JonsonLog.info("See you.");
        }
    }

    /**
     * 受信側サーバーの起動を行う。
     */
    private static void launchReceivingServer() {
        ReceivingServer receivingServer = new ReceivingServer();
        receivingService.execute(receivingServer);
    }

    /**
     * 送信側サーバーの起動を行う。
     */
    private static void launchSendingServer(Sender sender) {
        SendingServer sendingServer = new SendingServer(sender);
        sendingService.execute(sendingServer);
    }
}
