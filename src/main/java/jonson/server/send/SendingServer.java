package jonson.server.send;

import jonson.Main;
import jonson.PropertiesUtil;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 送信側サーバー
 */
public class SendingServer implements Runnable{

    private final int PORT;
    private final int THREAD_NUMBER;

    private final Sender sender;

    public final ExecutorService service;

    public SendingServer(Sender sender) {
        System.out.println("Start the sending server.");
        PORT = PropertiesUtil.getInstance().getPropertyIntValue("SendingPort", 10001);
        THREAD_NUMBER = PropertiesUtil.getInstance().getPropertyIntValue("SendingServerThreadNumber", 10);
        service = Executors.newFixedThreadPool(THREAD_NUMBER);
        this.sender = sender;
    }

    @Override
    public void run() {
        System.out.println("Sending server has been started");
        try (ServerSocket sc = new ServerSocket(PORT)){
            while (true) {
                // 接続を待ち受け続ける
                Socket socket = sc.accept();
                // 接続が合った場合はスレッド切り出し
                service.execute(new SendingServerThread(socket, sender));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Terminate the sending server.");
            service.shutdown();
            System.out.println("Sending server has been terminated.");
            Main.countDownLatch.countDown();
        }
    }
}
