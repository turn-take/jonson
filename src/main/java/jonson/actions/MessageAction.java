package jonson.actions;

import jonson.message.Message;
import jonson.message.OnLoginMessage;
import jonson.queue.MessageQueue;
import jonson.session.SessionManager;

import java.net.Socket;

/**
 * メッセージ受信時のアクション
 * ログイン済みかを判断してログイン済みならメッセージをキューに保管する。
 * 未ログインなら何もしない。
 */
public class MessageAction implements Action{

    private final OnLoginMessage message;
    // DI的に注入される
    private final MessageQueue messageQueue;

    public MessageAction(OnLoginMessage message, MessageQueue messageQueue) {
        this.message = message;
        this.messageQueue = messageQueue;
    }

    @Override
    public void execute(Socket socket) throws Exception {

        // セッションが無ければ何もしない
        if(!SessionManager.isLogin(message.getSessionId())) {
            return;
        }

        messageQueue.offer(message);
        socket.close();

    }
}
