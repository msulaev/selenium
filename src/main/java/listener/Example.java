package listener;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.events.WebDriverListener;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Example {
    WebDriver driver;

    @BeforeEach
    void setup() {
        WebDriver originalDriver = WebDriverManager.chromedriver().create();
        HighlighterListener eventListener = new HighlighterListener();
        driver =  new EventFiringDecorator(eventListener).decorate(originalDriver);

    }

    @Test
    void testEventListener() throws InterruptedException {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        assertThat(driver.getTitle())
                .isEqualTo("Hands-On Selenium WebDriver with Java");
        driver.findElement(By.linkText("Web form")).click();
    }
}
