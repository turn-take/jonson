package jonson.actions;

import jonson.message.LoginMessage;
import jonson.message.Message;
import jonson.message.OnLoginMessage;
import jonson.queue.MessageQueue;

import java.io.IOException;

/**
 * アクションを生成する簡易的なファクトリクラス
 */
public class ActionFactory {

    // インスタンス化禁止
    private ActionFactory(){}

    /**
     * MessageからAction生成
     * @param message
     * @return
     */
    public static Action newAction(Message message) {
        Action action = null;

        if(message instanceof LoginMessage) {
            action = new LoginAction((LoginMessage) message);
        }else if(message instanceof OnLoginMessage) {
            action = new MessageAction((OnLoginMessage) message);
        }

        return action;

    }
}
