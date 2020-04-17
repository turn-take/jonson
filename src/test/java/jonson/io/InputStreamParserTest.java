package jonson.io;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class InputStreamParserTest {

    @Test
    public void InputStreamから文字列への変換が出来る() throws IOException {
        String expected = "Hallo World!";
        InputStream inputStream = new ByteArrayInputStream(expected.getBytes(StandardCharsets.UTF_8));
        assertEquals(expected, InputStreamParser.parseInputStreamToString(inputStream));
    }
}
