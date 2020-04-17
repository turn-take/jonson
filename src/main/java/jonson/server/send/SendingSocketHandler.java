package jonson.server.send;

import com.fasterxml.jackson.databind.ObjectMapper;
import jonson.io.InputStreamParser;
import jonson.json.MessageMapper;
import jonson.message.Message;
import jonson.queue.MessageQueue;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * 送信側のソケットハンドリングクラス
 */
class SendingSocketHandler implements Runnable{
    private final Socket socket;

    SendingSocketHandler(Socket socket) {
        this.socket = socket;
        System.out.println("connected : "
                + socket.getRemoteSocketAddress());
    }

    public void run() {
        try (final DataOutputStream dos = new DataOutputStream(socket.getOutputStream())){

            String topicName = InputStreamParser.parseInputStreamToString(socket.getInputStream());

            System.out.println("topic : " + topicName);

            // キューからメッセージ取り出し
            Message message = MessageQueue.poll(topicName).get();

            // JSON形式に変換
            String output = MessageMapper.toJson(message);

            System.out.println(output);

            // JSON返却
            dos.writeUTF(output);
            dos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) {
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

