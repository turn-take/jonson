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
     * @throws IOException
     */
    public static String toJson(Message message) throws IOException {
        return mapper.writeValueAsString(message);
    }
}