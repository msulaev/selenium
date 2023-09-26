package driver.cdp;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v114.network.Network;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CDPEmulation {
    WebDriver driver;

    @Test
    public void emulation() {
        driver = WebDriverManager.chromedriver().create();
        DevTools devTools = ((ChromeDriver) driver)
                .getDevTools();

        String userAgent = "Mozilla/5.0 (iPhone; CPU iPhone OS 8_0 like Mac OS X) "
                + "AppleWebKit/600.1.3 (KHTML, like Gecko) "
                + "Version/8.0 Mobile/12A4345d Safari/600.1.4";

        //todo: why is this not working?
        devTools.send(Network.setUserAgentOverride(userAgent, Optional.empty(),
                Optional.empty(), Optional.empty()));

        Map<String, Object> deviceMetrics = new HashMap<>();
        deviceMetrics.put("width", 375);
        deviceMetrics.put("height", 667);
        deviceMetrics.put("mobile", true);
        deviceMetrics.put("deviceScaleFactor", 2);
        ((ChromeDriver) driver).executeCdpCommand(
                "Emulation.setDeviceMetricsOverride", deviceMetrics);

        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        assertThat(driver.getTitle()).contains("Selenium WebDriver");
    }
}
