package jonson.actions;

import jonson.message.LoginMessage;
import jonson.net.SocketHandler;
import jonson.session.Session;
import jonson.session.SessionManager;

import java.io.IOException;

/**
 * ログイン時のアクション
 * セッションを作成してセッションIDをクライアントに通知する
 * エラー発生時とかセッションがすでにある場合とかは面倒なのでスルーしておく
 */
public class LoginAction extends Action{

    private final LoginMessage loginMessage;

    public LoginAction(LoginMessage loginMessage) {
        this.loginMessage = loginMessage;
    }

    @Override
    public void executeSub(SocketHandler socketHandler) throws Exception{

        // セッションの生成
        Session session = SessionManager.createSession(socketHandler, loginMessage);

        long sessionId = session.getSessionID();

        // セッションIDの送信
        socketHandler.send(sessionId);

    }
}
