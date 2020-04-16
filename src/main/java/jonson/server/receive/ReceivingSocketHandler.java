package jonson.server.receive;

import jonson.message.Message;
import jonson.queue.MessageQueue;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * 受信側のソケットハンドリングクラス
 */
class ReceivingSocketHandler implements Runnable{
    private final Socket socket;

    ReceivingSocketHandler(Socket socket) {
        this.socket = socket;
        System.out.println("connected : "
                + socket.getRemoteSocketAddress());
    }

    public void run() {
        try (ObjectInputStream os = new ObjectInputStream(socket.getInputStream())){

            // Messageオブジェクトに変換
            Message message = (Message) os.readObject();


            handleMessage(message);

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Problem has occurred in SocketHandler.");
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

    /**
     * 受信したMessageオブジェクトをキューに追加する。
     * @param message
     */
    private void handleMessage(Message message) {
        System.out.println("Message has been received.");

        System.out.println(message);

        // キューに追加
        MessageQueue.offer(message);
    }
}
