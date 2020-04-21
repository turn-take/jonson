package jonson.server.send;

import jonson.io.InputStreamParser;

import java.io.*;
import java.net.Socket;

/**
 * 送信側のサーバーで動くスレッドクラス
 */
class SendingServerThread implements Runnable{
    private final Socket socket;
    private final Sender sender;

    SendingServerThread(Socket socket, Sender sender) {
        this.socket = socket;
        this.sender = sender;
        System.out.println("connected : "
                + socket.getRemoteSocketAddress());
    }

    public void run() {
        try {

            String topicName = InputStreamParser.parseInputStreamToString(socket.getInputStream());

            System.out.println("topic : " + topicName);

            // 購読者生成
            Subscriber subscriber = new Subscriber(socket, topicName);

            // 購読者登録
            sender.register(subscriber);

            // ソケットがcloseされるまで待機する
            while(subscriber.isAlive()) {}
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                // nothing to do
            }
            System.out.println("disconnected : "
                    + socket.getRemoteSocketAddress());
        }
    }
}

