package steps.asserts;

import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.*;

public class Gui {

    public static void shouldBeNotEquals(String expected, String actual) {

        assertNotEquals(expected, actual);
    }

    public static void shouldBeEquals(String expected, String actual) {

        assertEquals(expected, actual);
    }

    public static void shouldBeAbsent(WebDriver driver) {
        boolean absent;
        try {
            driver.switchTo().alert();
            absent = false;
        } catch (NoAlertPresentException e) {
            absent = true;
        }

        assertTrue(absent, "Ожидалось, что уведомление закроется, но оно осталось открытым");
    }
}
