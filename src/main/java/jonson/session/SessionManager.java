package jonson.session;

import jonson.message.Message;

import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * セッションを管理するクラス
 */
public class SessionManager {
    // インスタンス化禁止
    private SessionManager(){};

    private static final Map<Long, Session> sessionMap = new ConcurrentHashMap<>();

    /**
     * セッションを生成する
     * @param socket
     * @param message
     * @return
     */
    public static Session createSession(Socket socket, Message message) {
        long sessionId = 31 * socket.hashCode();
        sessionId *= message.hashCode();
        Session session = new Session(sessionId);

        System.out.println("New Session created. sessionID = " + sessionId);

        sessionMap.put(sessionId, session);

        return session;
    }

    public static void removeSession(long sessionId) {
        sessionMap.remove(sessionId);
    }

    /**
     * ログイン済みかを返す
     * @param sessionId
     * @return
     */
    public static boolean isLogin(long sessionId) {
        return sessionMap.containsKey(sessionId);
    }
}
