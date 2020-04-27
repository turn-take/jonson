package jonson.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import jonson.message.Message;

import java.io.IOException;

/**
 * Messageオブジェクトのマッパークラス
 */
public class MessageMapper {

    private final static ObjectMapper mapper = new ObjectMapper();

    /**
     * Messageオブジェクト　→　JSON文字列
     * @param message
     * @return JSON文字列
     * @throws IOException JSON文字列への変換中に例外が発生した場合
     */
    public static String toJson(Message message) throws IOException {
        try {
            return mapper.writeValueAsString(message);
        }catch (IOException e) {
            throw new IOException("Failed to convert to Json.");
        }

    }
}
