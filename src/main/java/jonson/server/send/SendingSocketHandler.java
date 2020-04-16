package jonson.server.send;

import com.fasterxml.jackson.databind.ObjectMapper;
import jonson.message.Message;
import jonson.queue.MessageQueue;

import java.io.*;
import java.net.Socket;

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
        try (DataOutputStream ds = new DataOutputStream(socket.getOutputStream())){

            // キューからメッセージ取り出し
            Message message = MessageQueue.poll();

            // JSON形式に変換
            ObjectMapper objectMapper = new ObjectMapper();
            String output = objectMapper.writeValueAsString(message);

            // JSON返却
            ds.writeUTF(output);
            ds.flush();
        } catch (IOException e) {
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

