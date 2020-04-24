package jonson.log;

/**
 * 共通ログクラス
 */
public class JonsonLog {

    public static void info(String text) {
        System.out.println("Jonson Process[INF]: " + text);
    }

    /**
     * テキストとエラーのスタックトレースを出力
     * @param text
     * @param e
     */
    public static void error(String text, Exception e) {
        System.err.println("Jonson Process[ERR]: " + text);
        e.printStackTrace();
    }
}
