package selenium.core.listener;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Example {
    WebDriver driver;
    /*
    * EventFiringDecorator class is used to register an event listener to WebDriver. You need to pass the event listener to the constructor of EventFiringDecorator class. And implement the WebDriverListener interface in the event listener class.
    */

    @BeforeEach
    void setup() {
        WebDriver originalDriver = WebDriverManager.chromedriver().create();
        HighlighterListener eventListener = new HighlighterListener();
        driver = new EventFiringDecorator(eventListener).decorate(originalDriver);

    }

    @Test
    void testEventListener() throws InterruptedException {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        assertThat(driver.getTitle())
                .isEqualTo("Hands-On Selenium WebDriver with Java");
        driver.findElement(By.linkText("Web form")).click();
    }
}
