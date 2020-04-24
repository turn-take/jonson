package jonson.server.receive;

import jonson.actions.Action;
import jonson.actions.ActionFactory;
import jonson.log.ReceivingLog;
import jonson.message.Message;
import jonson.net.SocketHandler;

import java.io.IOException;

/**
 * 受信側サーバーで動くスレッドクラス
 * アクションを同期実行するのでスレッドの待ち合わせは発生しない。
 */
class ReceivingServerThread implements Runnable{

    private final SocketHandler socketHandler;

    ReceivingServerThread(SocketHandler socketHandler) {
        this.socketHandler = socketHandler;
        ReceivingLog.info("connected : "
                + socketHandler.getRemoteSocketAddress());
    }

    public void run() {
        try {

            // メッセージ取得
            Message message = socketHandler.acceptMessage();

            // アクション生成
            Action action = ActionFactory.newAction(message);

            // アクション実行
            action.execute(socketHandler);

        } catch (Exception e) {
            ReceivingLog.error("Error has occurred!", e);
        } finally {
            try {
                if (!socketHandler.isSocketClosed()) {
                    socketHandler.close();
                }
            } catch (IOException e) {
                // nothing to do
            }
            ReceivingLog.info("disconnected : "
                    + socketHandler.getRemoteSocketAddress());
        }
    }

}
