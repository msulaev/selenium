package driver.cdp;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.NetworkInterceptor;
import org.openqa.selenium.remote.http.Contents;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.http.Route;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Network {
    WebDriver driver;

    @Test
    public void testNetworkInterceptor() throws Exception {
        driver = WebDriverManager.chromedriver().create();

        Path img = Paths
                .get(ClassLoader.getSystemResource("tools.png").toURI());
        byte[] bytes = Files.readAllBytes(img);

        try (NetworkInterceptor interceptor = new NetworkInterceptor(driver,
                Route.matching(req -> req.getUri().endsWith(".png"))
                        .to(() -> req -> new HttpResponse()
                                .setContent(Contents.bytes(bytes))))) {
            driver.get("https://bonigarcia.dev/selenium-webdriver-java/");

            int width = Integer.parseInt(driver.findElement(By.tagName("img"))
                    .getAttribute("width"));
            assertThat(width).isGreaterThan(80);
        }
    }
}
