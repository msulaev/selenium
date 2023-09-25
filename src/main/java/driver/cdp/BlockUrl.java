package driver.cdp;

import com.google.common.collect.ImmutableList;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v114.network.Network;
import org.openqa.selenium.devtools.v114.network.model.BlockedReason;


import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BlockUrl {
    WebDriver driver;


    /*
    * DevTools is used to send commands to the browser. It works with CDP (Chrome DevTools Protocol).
    * We used Network.enable() to enable the network.
    * After that we used Network.setBlockedURLs() to block the URL.
    * And addListener() to listen to the events. With loadingFailed() we are listening to the loading failed events.
    * */
    @Test
    public void testBlockUrl() {
        driver = WebDriverManager.chromedriver().create();
        DevTools devTools = ((ChromeDriver) driver)
                .getDevTools();
        devTools.send(Network.enable(Optional.empty(), Optional.empty(),
                Optional.empty()));

        String urlToBlock = "https://bonigarcia.dev/selenium-webdriver-java/img/hands-on-icon.png";
        devTools.send(Network.setBlockedURLs(ImmutableList.of(urlToBlock)));

        devTools.addListener(Network.loadingFailed(), loadingFailed -> {
            BlockedReason reason = loadingFailed.getBlockedReason().get();
            System.out.println("Blocking reason: {} " + reason);
            assertThat(reason).isEqualTo(BlockedReason.INSPECTOR);
        });

        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        assertThat(driver.getTitle()).contains("Selenium WebDriver");
    }
}
