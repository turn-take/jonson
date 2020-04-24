package jonson.log;

/**
 * 受信側の共通ログクラス
 */
public class ReceivingLog {

    public static void info(String text) {
        System.out.println("Receiving Process[INF]: " + text);
    }

    /**
     * テキストとエラーのスタックトレースを出力
     * @param text
     * @param e
     */
    public static void error(String text, Exception e) {
        System.err.println("Receiving Process[ERR]: " + text);
        e.printStackTrace();
    }
}
