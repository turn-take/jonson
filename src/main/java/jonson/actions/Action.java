package jonson.actions;

import java.net.Socket;

public interface Action {

    void execute(Socket socket) throws Exception;
}
