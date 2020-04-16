package jonson.server.send;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 送信側サーバー
 */
public class SendingServer implements Runnable{

    public static final int PORT = 10001; //待ち受けポート番号

    public final ExecutorService service;

    public SendingServer() {
        System.out.println("Start the sending server.");
        this.service = Executors.newFixedThreadPool(10);
    }

    @Override
    public void run() {
        System.out.println("Sending server has been started");
        try (ServerSocket sc = new ServerSocket(PORT)){
            while (true) {
                // 接続を待ち受け続ける
                Socket socket = sc.accept();
                // 接続が合った場合はスレッド切り出し
                service.execute(new SendingSocketHandler(socket));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Terminate the sending server.");
            service.shutdown();
            System.out.println("Sending server has been terminated.");
        }
    }
}
