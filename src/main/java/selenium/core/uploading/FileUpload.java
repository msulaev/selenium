package selenium.core.uploading;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

public class FileUpload {
    WebDriver driver;
    static final Logger log = getLogger(lookup().lookupClass());

    @Test
    public void uploadTest() throws IOException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
        WebElement fileInput = driver.findElement(By.name("my-file"));
        Path fileName = File.createTempFile("test", ".txt").toPath();
        String absolutePath = fileName.toAbsolutePath().toString();
        fileInput.sendKeys(absolutePath);
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        assertThat(driver.findElement(By.xpath("//h1[@class='display-6']")).isDisplayed()).isTrue();
    }

}
