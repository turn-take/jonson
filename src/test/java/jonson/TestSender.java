package jonson;

import jonson.message.Message;
import jonson.message.SimpleMessage;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * テスト用の送信クラス
 * 受信サーバーを起動してから実行すること
 */
public class TestSender {
    public static void main(String[] args) throws InterruptedException {
//        ExecutorService service = Executors.newCachedThreadPool();
//        while(true) {
//            service.execute(new TestClient());
//            Thread.sleep(500);
//        }
        Thread t = new Thread(new TestClient());
        t.start();
    }

    static class TestClient implements Runnable{
        public void run() {

            try (Socket s = new Socket("localhost", 10000);
                 ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream())) {
                os.writeObject(new SimpleMessage("test",Thread.currentThread().getName()));
                os.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


