package listener;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Example {
    WebDriver driver;

    @BeforeEach
    void setup() {
        driver = WebDriverManager.chromedriver().create();
        HighlighterListener eventListener = new HighlighterListener(driver);
        EventFiringWebDriver eventDriver = new EventFiringWebDriver(driver);
        eventDriver.register(eventListener);
    }

    @Test
    void testEventListener() throws InterruptedException {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        assertThat(driver.getTitle())
                .isEqualTo("Hands-On Selenium WebDriver with Java");
        driver.findElement(By.linkText("Web form")).click();
    }
}
