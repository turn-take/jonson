package jonson.actions;

import jonson.message.LoginMessage;
import jonson.session.Session;
import jonson.session.SessionManager;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * ログイン時のアクション
 * セッションを作成してセッションIDをクライアントに通知する
 */
public class LoginAction implements Action{

    private final LoginMessage loginMessage;

    public LoginAction(LoginMessage loginMessage) {
        this.loginMessage = loginMessage;
    }

    public void execute(Socket socket) throws Exception{

        // セッションの生成
        Session session = SessionManager.createSession(socket, loginMessage);

        long sessionId = session.getSessionID();

        try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream())){

            dos.writeLong(sessionId);
            dos.flush();

        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            socket.close();
        }

    }
}
