package jonson.server.receive;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 受信側サーバー
 */
public class ReceivingServer implements Runnable{
    public static final int PORT = 10000; //待ち受けポート番号

    private final ExecutorService service;

    public ReceivingServer() {
        System.out.println("Start the receiving server.");
        this.service = Executors.newFixedThreadPool(10);
    }

    //@Override
    public void run() {
        System.out.println("Receiving server has been started.");
        try (ServerSocket sc = new ServerSocket(PORT)){
            while (true) {
                // 接続を待ち受け続ける
                Socket socket = sc.accept();
                // 接続が合った場合はスレッド切り出し
                service.execute(new ReceivingSocketHandler(socket));
            }
        } catch (Exception e) {
            System.out.println("Problem has occurred in receiving server.");
            e.printStackTrace();
        } finally {
            System.out.println("Terminate the receiving server.");
            service.shutdown();
            System.out.println("Receiving server has been terminated.");
        }
    }
}
