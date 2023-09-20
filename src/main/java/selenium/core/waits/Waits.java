package selenium.core.waits;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Waits {

    WebDriver driver = WebDriverManager.chromedriver().create();
    @Test
    public void testExplicitWait() {
        /*
        * Explicit wait is used to wait for a certain condition to occur before proceeding further in the code.
        * */
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/loading-images.html");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement landscape = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("landscape")));

        assertThat(landscape.getAttribute("src")).containsIgnoringCase("landscape");
    }

    @Test
    void testFluentWait() {
        /*
        * Fluent wait is used to wait for a condition to occur with a polling frequency.
        * */
        driver.get(
                "https://bonigarcia.dev/selenium-webdriver-java/loading-images.html");
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);

        WebElement landscape = wait.until(ExpectedConditions
                .presenceOfElementLocated(By.id("landscape")));
        assertThat(landscape.getAttribute("src"))
                .containsIgnoringCase("landscape");
    }

    @Test
    void testImplicitWait() {
        /*
        * Implicit wait is used to wait for a certain amount of time before proceeding further in the code.
         */
        driver.get(
                "https://bonigarcia.dev/selenium-webdriver-java/loading-images.html");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        WebElement landscape = driver.findElement(By.id("landscape"));
        assertThat(landscape.getAttribute("src"))
                .containsIgnoringCase("landscape");
    }

}
