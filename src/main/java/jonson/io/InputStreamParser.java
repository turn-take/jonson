package jonson.io;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class InputStreamParser {

    /**
     * InputStreamからStringに変換
     * @param is
     * @return 文字列
     * @throws IOException
     */
    public static String parseInputStreamToString(InputStream is) throws IOException {
        // 入力をバイト配列に格納
        byte[] bytes = new byte[1024];
        is.read(bytes);

        // バイト配列から有効範囲を算出
        int len = 0;
        for(byte b : bytes) {
            if(b != 0) len++;
        }

        // バイト配列をUTF-8文字列に変換
        String result = new String(bytes, 0, len, StandardCharsets.UTF_8);
        return result;

    }
}
