package jonson.actions;

import jonson.message.OnLoginMessage;
import jonson.net.SocketHandler;
import jonson.queue.MessageQueue;
import jonson.session.SessionManager;

/**
 * メッセージ受信時のアクション
 * ログイン済みかを判断してログイン済みならメッセージをキューに保管する。
 * 未ログインなら何もしない。
 */
public class MessageAction extends Action{

    private final OnLoginMessage message;

    public MessageAction(OnLoginMessage message) {
        this.message = message;
    }

    @Override
    public void executeSub(SocketHandler socketHandler) {

        // セッションが無ければ何もしない
        if(!SessionManager.isLogin(message.getSessionId())) {
            return;
        }

        MessageQueue.getInstance().offer(message);
    }
}
