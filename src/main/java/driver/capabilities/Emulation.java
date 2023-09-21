package driver.capabilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Emulation {
    WebDriver driver;

    @Test
    public void testDeviceEmulation() {
        //Todo Try another realization

        ChromeOptions options = new ChromeOptions();
        Map<String, Object> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceName", "iPhone 6/7/8");
        options.setExperimentalOption("mobileEmulation", mobileEmulation);

        driver = WebDriverManager.chromedriver().capabilities(options).create();

        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        assertThat(driver.getTitle()).contains("Selenium WebDriver");
    }

}
