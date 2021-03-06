package jonson;

import jonson.log.JonsonLog;

import java.io.*;
import java.util.Properties;

/**
 * プロパティファイルを扱うシングルトンクラス
 */
public class PropertiesUtil {
    // プロパティファイルのバス
    private static final String FILE_NAME = "/application.properties";

    private final Properties properties;

    private static final PropertiesUtil instance = new PropertiesUtil();

    private PropertiesUtil(){
        properties = new Properties();
    };

    public static PropertiesUtil getInstance() {
        return instance;
    }

    public void load() throws IOException{
        try (InputStream is = getClass().getResourceAsStream(FILE_NAME);
             BufferedReader br = new BufferedReader(new InputStreamReader(is))){
            properties.load(br);
        } catch (IOException e) {
            // ファイル読み込みに失敗
            JonsonLog.error(String.format("Failed to load the file. File name:%s", FILE_NAME), e);
        }
    }

    /**
     * プロパティ値を取得する
     *
     * @param key キー
     * @return 値
     */
    public String getProperty(final String key) {
        return properties.getProperty(key);
    }

    /**
     * プロパティ値を取得する
     *
     * @param key キー
     * @param defaultValue デフォルト値
     * @return キーが存在しない場合、デフォルト値
     *          存在する場合、値
     */
    public String getProperty(final String key, final String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * プロパティ値をint型で取得する。
     * @param key
     * @param defaultValue
     * @return キーが存在しない場合、デフォルト値
     *          存在する場合、値
     */
    public int getPropertyIntValue(final String key, final int defaultValue) {
        String property = getProperty(key);
        int value;
        try {
            value = Integer.valueOf(property);
        } catch (NumberFormatException e) {
            value = defaultValue;
        }
        return value;
    }

}
