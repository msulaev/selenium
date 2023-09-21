package driver.capabilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;


import java.time.Duration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PageLoadingStrategy {
    WebDriver driver;
    PageLoadStrategy pageLoadStrategy;

    /*
    * pageLoadStrategy is used to set the page loading strategy for the browser.
    * PageLoadStrategy.NORMAL: This is the default strategy. It waits for the HTML content to be loaded and parsed before moving on to the next step.
    * PageLoadStrategy.EAGER: It waits for the DOM to be loaded and does not wait for the resources to finish loading.
    * PageLoadStrategy.NONE: It does not wait for the DOM to be loaded or the resources to be loaded.
    * */
    @Test
    public void testPageLoad() {
        ChromeOptions options = new ChromeOptions();
        pageLoadStrategy = PageLoadStrategy.NORMAL;
        options.setPageLoadStrategy(pageLoadStrategy);

        driver = WebDriverManager.chromedriver().capabilities(options).create();
        long initMillis = System.currentTimeMillis();
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        Duration elapsed = Duration
                .ofMillis(System.currentTimeMillis() - initMillis);

        Capabilities capabilities = ((RemoteWebDriver) driver)
                .getCapabilities();
        Object pageLoad = capabilities
                .getCapability(CapabilityType.PAGE_LOAD_STRATEGY);
        String browserName = capabilities.getBrowserName();
        System.out.println("The page took {} ms to be loaded using a '{}' strategy in {}"
                + elapsed.toMillis() + pageLoad + browserName);


        assertThat(pageLoad).isEqualTo(pageLoadStrategy.toString());
    }

}
