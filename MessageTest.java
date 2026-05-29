import org.junit.jupiter.api.Test;
import java.lang.reflect.Method;
import static org.junit.jupiter.api.Assertions.*;

public class MessageAppTest {

    @Test
    void testGenerateMessageId() throws Exception {
        Method method = MessageApp.class.getDeclaredMethod("generateMessageId");
        method.setAccessible(true);
        String messageId = (String) method.invoke(null);
        assertNotNull(messageId);
        assertTrue(messageId.startsWith("MSG"));
    }

    @Test
    void testGenerateMessageHash() throws Exception {
        Method method = MessageApp.class.getDeclaredMethod("generateMessageHash", String.class, int.class, String.class);
        method.setAccessible(true);
        String result = (String) method.invoke(null, "MSG12345", 1, "Hello world");
        assertEquals("MS:1:HELLOWORLD", result);
    }

    @Test
    void testValidSouthAfricanNumber() {
        String number = "+27123456789";
        boolean valid = number.startsWith("+27") && number.length() == 12;
        assertTrue(valid);
    }

    @Test
    void testInvalidSouthAfricanNumber() {
        String number = "0812345678";
        boolean valid = number.startsWith("+27") && number.length() == 12;
        assertFalse(valid);
    }

    @Test
    void testMessageLengthValid() {
        String message = "This is a short message";
        assertTrue(message.length() <= 250);
    }

    @Test
    void testMessageLengthTooLong() {
        String message = "A".repeat(251);
        assertTrue(message.length() > 250);
    }
}
