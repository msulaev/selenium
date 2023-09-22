package driver.capabilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Localization {
    WebDriver driver;

    @Test
    void testAcceptLang() throws InterruptedException {
        String lang = "en-US";
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--lang=" + lang);

        driver = WebDriverManager.chromedriver().capabilities(options).create();
        driver.get(
                "https://bonigarcia.dev/selenium-webdriver-java/multilanguage.html");
        Thread.sleep(10000);
    }
}
