package jonson.actions;

import jonson.log.JonsonLog;
import jonson.net.SocketHandler;

import java.io.IOException;

/**
 * アクションの基底クラス
 */
public abstract class Action {

    /**
     * サブクラスのアクションを実行した後、ソケットをクローズするテンプレートメソッド
     * @param socketHandler
     */
    public void execute(SocketHandler socketHandler) {
        try {
            executeSub(socketHandler);
            close(socketHandler);
        } catch (Exception e) {
            JonsonLog.error(e.getMessage(),e);
        }
    }

    protected abstract void executeSub(SocketHandler socketHandler) throws Exception;

    /**
     * ソケットをクローズする。
     * @param socketHandler
     * @throws IOException
     */
    private void close(SocketHandler socketHandler) throws IOException {
        socketHandler.close();
    };
}
