package jonson;

import jonson.server.receive.ReceivingServer;
import jonson.server.send.SendingServer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    // 受信側サービス
    private final static ExecutorService receivingService = Executors.newSingleThreadExecutor();
    // 送信側サービス
    private final static ExecutorService sendingService = Executors.newSingleThreadExecutor();

    public static void main(String[] args) {
            System.out.println("Launch the application.");

            launchReceivingServer();

            launchSendingServer();

            System.out.println("Application has been launched.");
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
