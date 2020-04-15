package jonson;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        ExecutorService service = Executors.newSingleThreadExecutor();

        Server server = new Server();

        service.execute(server);
    }
}
