package jonson.server.receive;

import jonson.actions.Action;
import jonson.actions.ActionFactory;
import jonson.message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * 受信側サーバーで動くスレッドクラス
 */
class ReceivingServerThread implements Runnable{
    private final Socket socket;

    ReceivingServerThread(Socket socket) {
        this.socket = socket;
        System.out.println("connected : "
                + socket.getRemoteSocketAddress());
    }

    public void run() {
        try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())){

            // 入力をMessageオブジェクトにパース(失敗したら例外発生)
            Message message = (Message) ois.readObject();

            // アクション生成
            Action action = ActionFactory.newAction(message);

            // アクション実行
            action.execute(socket);

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Problem has occurred in SocketHandler.");
            e.printStackTrace();
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
