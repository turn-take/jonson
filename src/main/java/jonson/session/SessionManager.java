package jonson.session;

import jonson.log.JonsonLog;
import jonson.message.Message;
import jonson.net.SocketHandler;

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
     * @param socketHandler
     * @param message
     * @return
     */
    public static Session createSession(SocketHandler socketHandler, Message message) {
        // セッションIDの生成は適当
        long sessionId = 31 * socketHandler.hashCode();
        sessionId *= message.hashCode();
        Session session = new Session(sessionId);

        JonsonLog.info("New Session created. sessionID = " + sessionId);

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
