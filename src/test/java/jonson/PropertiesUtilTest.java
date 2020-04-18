package jonson;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class PropertiesUtilTest {

    @Test
    public void 設定値の取得が出来る() {

        PropertiesUtil propertiesUtil = PropertiesUtil.getInstance();

        assertDoesNotThrow(() -> propertiesUtil.load());

        String expected1 = "Hallo World!";
        String expected2 = "8080";
        int expected3 = 8080;

        assertAll(
                () -> {assertEquals(propertiesUtil.getProperty("TestValue1"), expected1);},
                () -> {assertEquals(propertiesUtil.getProperty("TestValue2", "8080"), expected2);},
                () -> {assertEquals(propertiesUtil.getPropertyIntValue("TestValue1", 8080), expected3);}
        );

    }
}
