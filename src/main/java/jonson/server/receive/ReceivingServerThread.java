package jonson.server.receive;

import jonson.actions.Action;
import jonson.actions.ActionFactory;
import jonson.log.ReceivingLog;
import jonson.message.Message;
import jonson.net.SocketHandler;

import java.net.Socket;

/**
 * 受信側サーバーで動くスレッドクラス
 * アクションを同期実行するのでスレッドの待ち合わせは発生しない。
 */
class ReceivingServerThread implements Runnable{

    private final Socket socket;

    ReceivingServerThread(Socket socket) {
        this.socket = socket;
        ReceivingLog.info("connected : "
                + socket.getRemoteSocketAddress());
    }

    public void run() {
        try (SocketHandler socketHandler = new SocketHandler(socket)){

            // メッセージ取得
            Message message = socketHandler.acceptMessage();

            // アクション生成
            Action action = ActionFactory.newAction(message);

            // アクション実行
            action.execute(socketHandler);

        } catch (Exception e) {
            ReceivingLog.error("Error has occurred!", e);
        } finally {
            ReceivingLog.info("disconnected : "
                    + socket.getRemoteSocketAddress());
        }
    }

}
