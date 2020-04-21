package jonson.session;

import lombok.Getter;

/**
 * immutableなセッションクラス
 */
public final class Session {

    @Getter
    private final long sessionID;

    public Session(long sessionID) {
        this.sessionID = sessionID;
    }
}
