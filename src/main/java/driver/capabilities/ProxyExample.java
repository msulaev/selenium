package driver.capabilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ProxyExample {

    WebDriver driver;

     /*
     * Proxy class is used to set the proxy configuration for the browser. It's used to set the HTTP and SSL proxy for the browser.
     * In this example, we are setting the proxy for both HTTP and SSL to the same value.
     * We use setAcceptInsecureCerts(true) to accept insecure certificates.
     * */
    @Test
    void testProxy() {
        Proxy proxy = new Proxy();
        String proxyStr = "proxy:port";
        proxy.setHttpProxy(proxyStr);
        proxy.setSslProxy(proxyStr);

        ChromeOptions options = new ChromeOptions();
        options.setAcceptInsecureCerts(true);
        options.setCapability(CapabilityType.PROXY, proxy);

        driver = WebDriverManager.chromedriver().capabilities(options).create();

        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        assertThat(driver.getTitle()).contains("Selenium WebDriver");
    }

}
