package jonson.log;

/**
 * 送信側の共通ログクラス
 */
public class SendingLog {

    public static void info(String text) {
        System.out.println("Sending Process[INF]: " + text);
    }

    /**
     * テキストとエラーのスタックトレースを出力
     * @param text
     * @param e
     */
    public static void error(String text, Exception e) {
        System.err.println("Sending Process[ERR]: " + text);
        e.printStackTrace();
    }
}
