package jonson;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ServerThread implements Runnable{
    private final Socket socket;

    public ServerThread(Socket socket) {
        this.socket = socket;
        System.out.println("connected : "
                + socket.getRemoteSocketAddress());
    }

    public void run() {
        try (DataInputStream dis = new DataInputStream(socket.getInputStream())){

            // 入力をバイト配列に格納
            byte[] b = new byte[1024];
            dis.read(b);

            // バイト配列をUTF-8文字列に変換
            String result = new String(b, StandardCharsets.UTF_8);
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                // nothing to do
            }
            System.out.println("disconnected : "
                    + socket.getRemoteSocketAddress());
        }
    }
}
