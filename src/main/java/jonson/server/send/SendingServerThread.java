package jonson.server.send;

import jonson.io.InputStreamParser;
import jonson.log.SendingLog;
import jonson.net.SocketHandler;

import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

/**
 * 送信側のサーバーで動くスレッドクラス
 * ソケットからトピックの購読者を作成し、購読完了までスレッドを待機させる。
 */
class SendingServerThread implements Runnable{
    private final Socket socket;
    private final Sender sender;

    private final CountDownLatch countDownLatch = new CountDownLatch(1);

    SendingServerThread(Socket socket, Sender sender) {
        this.socket = socket;
        this.sender = sender;
        SendingLog.info("connected : "
                + socket.getRemoteSocketAddress());
    }

    public void run() {
        try (SocketHandler socketHandler = new SocketHandler(socket)){

            String topicName = InputStreamParser.parseToString(socketHandler.getInputStream());

            SendingLog.info("topic : " + topicName);

            // 購読者生成
            Subscriber subscriber = new Subscriber(socketHandler, topicName, countDownLatch);

            // 購読者登録
            sender.register(subscriber);

            // 購読完了まで待機する
            countDownLatch.await();

            // 購読者を削除
            sender.remove(subscriber);

            // 例外ハンドリング
            handleException(subscriber);

        } catch (Exception e) {
            SendingLog.error(e.getMessage() , e);
        } finally {
            SendingLog.info("disconnected : "
                    + socket.getRemoteSocketAddress());
        }
    }

    /**
     * 例外のハンドリングを行う。
     * 引数の購読者が例外を保持している場合は例外をスローする。保持していない場合は何もしない。
     * @param subscriber 購読者
     * @throws Exception 購読者が例外を保持している場合
     */
    private void handleException(Subscriber subscriber) throws Exception{
        Optional<Exception> optionalException = subscriber.getException();
        if(optionalException.isPresent()) {
            throw optionalException.get();
        }
    }
}

