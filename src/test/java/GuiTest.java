import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;

import static steps.asserts.Gui.*;
import static utils.DataHelper.getChromeDriver;


public class GuiTest {

    @BeforeEach
    public void setProperty() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
    }

    @Test
    @DisplayName("Проверка новой вкладки")
    @Description("Новая вкладка открывается, содержит текст 'New Page' и закрывается")
    public void checksNewWindow() {

        WebDriver driver = getChromeDriver();

        driver.get("http://the-internet.herokuapp.com/windows");
        String handleCurPage = driver.getWindowHandle();

        try {

            driver.findElement(By.linkText("Click Here"))
                    .click();

            for (String handle : driver.getWindowHandles()) {
                if (!handleCurPage.equals(handle)) {
                    driver.switchTo().window(handle);
                }
            }

            String handleNewPage = driver.getWindowHandle();

            shouldBeNotEquals(handleCurPage, handleNewPage);

            String actualTextPage = driver.findElement(By.className("example")).getText();
            String expectedTextPage = "New Page";

            shouldBeEquals(expectedTextPage, actualTextPage);

            driver.close();

            driver.switchTo().window(handleCurPage);

            shouldBeEquals(handleCurPage, driver.getWindowHandle());

        } catch (NoSuchElementException e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }

    }

    @Test
    @DisplayName("Проверка уведомлений")
    public void checksAlerts() {

        WebDriver driver = getChromeDriver();

        driver.get("https://the-internet.herokuapp.com/javascript_alerts");

        try {

            driver.findElement(By.cssSelector("button[onclick='jsAlert()']"))
                    .click();

            Alert jsAlert = driver.switchTo().alert();

            shouldBeEquals("I am a JS Alert", jsAlert.getText());

            jsAlert.accept();

            shouldBeAbsent(driver);

            driver.findElement(By.cssSelector("button[onclick='jsConfirm()']"))
                    .click();

            driver.switchTo().alert().dismiss();

            shouldBeAbsent(driver);

            driver.findElement(By.cssSelector("button[onclick='jsPrompt()']"))
                    .click();

            Alert alert = driver.switchTo().alert();
            String inputText = "Hello World";

            alert.sendKeys(inputText);

            alert.accept();

            String result = driver.findElement(By.id("result")).getText();

            shouldBeEquals("You entered: " + inputText, result);

        } catch (NoSuchElementException e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }

    }

}
