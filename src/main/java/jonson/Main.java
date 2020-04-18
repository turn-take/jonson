package jonson;

import jonson.server.receive.ReceivingServer;
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
            System.out.println("Launch the application.");

            // 設定ファイル読み込み
            PropertiesUtil.getInstance().load();

            launchReceivingServer();

            launchSendingServer();

            System.out.println("Application has been launched.");

            // カウントダウンが0になったらアプリケーション終了
            countDownLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Exit the application.");
            receivingService.shutdown();
            sendingService.shutdown();
            System.out.println("See you.");
        }
    }

    /**
     * 受信側サーバーの起動を行う。
     */
    public static void launchReceivingServer() {
        ReceivingServer receivingServer = new ReceivingServer();
        receivingService.execute(receivingServer);
    }

    /**
     * 送信側サーバーの起動を行う。
     */
    public static void launchSendingServer() {
        SendingServer sendingServer = new SendingServer();
        sendingService.execute(sendingServer);
    }
}
