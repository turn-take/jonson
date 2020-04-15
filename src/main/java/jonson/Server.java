package jonson;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable{
    public static final int PORT = 10000; //待ち受けポート番号

    private final ExecutorService service;

    public Server() {
        System.out.println("Start the server.");
        this.service = Executors.newFixedThreadPool(10);
    }

    //@Override
    public void run() {
        System.out.println("Server has been started.");
        try (ServerSocket sc = new ServerSocket(PORT)){
            while (true) {
                // 接続を待ち受け続ける
                Socket socket = sc.accept();
                // 接続が合った場合はスレッド切り出し
                service.execute(new ServerThread(socket));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Terminate the server.");
            service.shutdown();
            System.out.println("Server has been terminated.");
        }
    }
}
