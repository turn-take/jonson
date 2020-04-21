package jonson;

import jonson.message.LoginMessage;
import jonson.message.SimpleOnLoginMessage;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * テスト用の送信クラス
 * 受信サーバーを起動してから実行すること
 */
public class TestSender {
    public static void main(String[] args) throws InterruptedException {
        try{
            TestSender testSender = new TestSender();
            long sessionId = testSender.login();
            testSender.send(sessionId);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * ログインを実行し、セッションIDを返す
     * @return
     * @throws Exception
     */
    public long login() throws Exception{

        try (Socket s = new Socket("localhost", 10000);
             ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
             DataInputStream dis = new DataInputStream(s.getInputStream())) {
            os.writeObject(new LoginMessage());
            os.flush();

            long sessionId = dis.readLong();

            return sessionId;

        } catch (Exception e) {
            throw e;
        }
    }

    public void send(long sessionId) {
        try(Socket socket = new Socket("localhost", 10000);
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream())
        ) {
            os.writeObject(new SimpleOnLoginMessage("test", sessionId, "This is a test."));
            os.flush();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


