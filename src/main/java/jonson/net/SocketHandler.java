package jonson.net;

import jonson.log.SendingLog;
import jonson.message.Message;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * ソケットのハンドラクラス
 */
public class SocketHandler {

    private final Socket socket;
    private boolean socketClosed;

    public SocketHandler(Socket socket) {
        this.socket = socket;
        socketClosed = false;
    }

    /**
     * String型のデータを送信する。
     * @param data 送信データ
     */
    public void send(String data) {
        if(socketClosed) return;
        try(DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {

            dos.writeUTF(data);
            dos.flush();
        } catch (IOException e) {
            SendingLog.error("Failed to send data.", e);
        }
    }

    /**
     * long型のデータを送信する。
     * @param data 送信データ
     */
    public void send (long data) {
        if (socketClosed) return;
        try(DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {

            dos.writeLong(data);
            dos.flush();
        } catch (IOException e) {
            SendingLog.error("Failed to send data.", e);
        }
    }

    /**
     * ソケットからMessageオブジェクトを取得する。
     * @return message メッセージ
     */
    public Message acceptMessage() throws IOException, ClassNotFoundException {
        if(socketClosed) throw new IOException("Socket is already closed.");
        try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
            // 入力をMessageオブジェクトにパースして返す(失敗したら例外発生)
            return (Message) ois.readObject();
        }
    }

    /**
     * ソケットをクローズする。
     */
    public void close() throws IOException {
        try {
            if(!socketClosed) {
                socket.close();
            }
        } catch (IOException e) {
            throw new IOException("Failed to close the socket.");
        }
        socketClosed = true;
    }

    /**
     * ソケットがクローズ済みかを返す。
     * @return boolean
     */
    public boolean isSocketClosed() {
        return socketClosed;
    }

    /**
     * ソケットのリモートアドレスを取得する。
     * @return ソケットのリモートアドレス
     */
    public SocketAddress getRemoteSocketAddress() {
        return socket.getRemoteSocketAddress();
    }

    /**
     * ソケットの出力ストリームを取得する。
     * @return InputStream ソケットのインプットストリーム
     * @throws IOException
     */
    public InputStream getInputStream() throws IOException {
        if(socketClosed) throw new IOException("Socket is already closed.");
        return socket.getInputStream();
    }
}
