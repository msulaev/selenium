package selenium.core.jsExecutor;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class JSExecutor {
    WebDriver driver;

    @Test
    public void scrollBy() {
        driver = WebDriverManager.chromedriver().create();
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/long-page.html");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 1000)");
    }

    @Test
    public void scrollIntoView() {
        driver = WebDriverManager.chromedriver().create();
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/long-page.html");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement lastElement = driver.findElement(By.cssSelector("p:last-child"));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", lastElement);
    }

    @Test
    public void infiniteScroll() {
        driver = WebDriverManager.chromedriver().create();

        driver.get(
                "https://bonigarcia.dev/selenium-webdriver-java/infinite-scroll.html");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        By pLocator = By.tagName("p");
        List<WebElement> paragraphs = wait.until(
                ExpectedConditions.numberOfElementsToBeMoreThan(pLocator, 0));
        int initParagraphsNumber = paragraphs.size();

        WebElement lastParagraph = driver.findElement(
                By.xpath(String.format("//p[%d]", initParagraphsNumber)));
        String script = "arguments[0].scrollIntoView();";
        js.executeScript(script, lastParagraph);

        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(pLocator,
                initParagraphsNumber));
    }

    @Test
    public void asyncJS() {
       /*
       * executeAsyncScript() is used to execute JavaScript asynchronously. It returns a promise. You can use it to wait for a certain amount of time before proceeding further in the code.
       * */
        driver = WebDriverManager.chromedriver().create();
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        JavascriptExecutor js = (JavascriptExecutor) driver;

        Duration pause = Duration.ofSeconds(2);
        String script = "const callback = arguments[arguments.length - 1];"
                + "window.setTimeout(callback, " + pause.toMillis() + ");";

        long initMillis = System.currentTimeMillis();
        js.executeAsyncScript(script);
        Duration elapsed = Duration
                .ofMillis(System.currentTimeMillis() - initMillis);
        System.out.println("The script took " + elapsed.toMillis() + " ms to be executed");
        assertThat(elapsed).isGreaterThanOrEqualTo(pause);
    }

    @Test
    void testPinnedScripts() {
        /*
        * pin() is used to pin a script to the browser. It returns a key that can be used to execute the script later.
        * unpin() is used to unpin a script from the browser.
        * ScriptKey is a class that represents a key to a pinned script.
        * */
        driver = WebDriverManager.chromedriver().create();
        String initPage = "https://bonigarcia.dev/selenium-webdriver-java/";
        driver.get(initPage);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        ScriptKey linkKey = js
                .pin("return document.getElementsByTagName('a')[2];");
        ScriptKey firstArgKey = js.pin("return arguments[0];");

        Set<ScriptKey> pinnedScripts = js.getPinnedScripts();
        assertThat(pinnedScripts.size()).isEqualTo(2);

        WebElement formLink = (WebElement) js.executeScript(linkKey);
        formLink.click();
        assertThat(driver.getCurrentUrl()).isNotEqualTo(initPage);

        String message = "Hello world!";
        String executeScript = (String) js.executeScript(firstArgKey, message);
        assertThat(executeScript).isEqualTo(message);

        js.unpin(linkKey);
        assertThat(js.getPinnedScripts().size()).isEqualTo(1);
    }
}
