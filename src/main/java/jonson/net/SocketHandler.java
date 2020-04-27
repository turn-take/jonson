package jonson.net;

import jonson.message.Message;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * ソケットのハンドラクラス
 * ソケットを通したデータの送受信を行う。
 * AutoCloseable実装なのでtry-with-resource分で容易にcloseができる。
 */
public class SocketHandler implements AutoCloseable{

    // このインスタンス内で扱うソケット
    private final Socket socket;

    public SocketHandler(Socket socket) {
        this.socket = socket;
    }

    /**
     * String型のデータを送信する。
     * @param data 送信データ
     * @throws IOException 送信に失敗した場合
     */
    public void send(String data) throws IOException{
        try(DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {

            dos.writeUTF(data);
            dos.flush();
        } catch (IOException e) {
            throw new IOException("Failed to send data.",e);
        }
    }

    /**
     * long型のデータを送信する。
     * @param data 送信データ
     * @throws IOException 送信に失敗した場合
     */
    public void send (long data) throws IOException{
        try(DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {

            dos.writeLong(data);
            dos.flush();
        } catch (IOException e) {
            throw new IOException("Failed to send data.",e);
        }
    }

    /**
     * ソケットからMessageオブジェクトを取得する。
     * @return message メッセージ
     * @throws IOException
     */
    public Message acceptMessage() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
            // 入力をMessageオブジェクトにパースして返す(失敗したら例外発生)
            return (Message) ois.readObject();
        }
    }

    /**
     * ソケットをクローズする。
     * try-with-resource対応
     */
    @Override
    public void close() throws IOException {
        try {
            socket.close();
        } catch (IOException e) {
            throw new IOException("Failed to close the socket.");
        }
    }


    /**
     * ソケットの出力ストリームを取得する。
     * @return InputStream ソケットのインプットストリーム
     * @throws IOException 出力ストリーム取得時に例外が発生した場合
     */
    public InputStream getInputStream() throws IOException {
        return socket.getInputStream();
    }
}
