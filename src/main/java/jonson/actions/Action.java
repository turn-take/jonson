package jonson.actions;

import jonson.log.JonsonLog;
import jonson.net.SocketHandler;

import java.io.IOException;

/**
 * アクションの基底クラス
 */
public abstract class Action {

    /**
     * サブクラスのアクションを実行するテンプレートメソッド
     * @param socketHandler
     */
    public void execute(SocketHandler socketHandler) throws Exception{
        executeSub(socketHandler);
    }

    protected abstract void executeSub(SocketHandler socketHandler) throws Exception;
}
