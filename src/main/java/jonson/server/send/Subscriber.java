package jonson.server.send;

import jonson.json.MessageMapper;
import jonson.message.Message;
import lombok.Getter;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * メッセージの購読者
 * ソケットと1:1の関係
 */
public class Subscriber {

    private final Socket socket;

    @Getter
    private final String topicName;

    public Subscriber(Socket socket, String topicName) {
        this.socket = socket;
        this.topicName = topicName;
    }

    /**
     * 通知を受信してメッセージを送信する。
     * @param message メッセージ
     */
    public void onNotify(Message message) {
        try(DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {
            String output = MessageMapper.toJson(message);

            System.out.println(output);

            // JSON送信
            dos.writeUTF(output);
            dos.flush();

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生きているかを返す
     * @return boolean
     */
    public boolean isAlive() {
        return !isDead();
    }

    /**
     * 死んでいるかを返す
     * @return boolean
     */
    public boolean isDead() {
        return socket.isClosed();
    }
}
