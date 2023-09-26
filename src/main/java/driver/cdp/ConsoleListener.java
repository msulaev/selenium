package driver.cdp;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.events.ConsoleEvent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ConsoleListener {
    WebDriver driver;



    @Test
    public void testConsoleListener() throws ExecutionException, InterruptedException, TimeoutException {
        driver = WebDriverManager.chromedriver().create();
        DevTools devTools = ((ChromeDriver) driver)
                .getDevTools();
        devTools.createSession();
        CompletableFuture<ConsoleEvent> futureEvents = new CompletableFuture<>();
        devTools.getDomains().events().addConsoleListener(futureEvents::complete);

        CompletableFuture<JavascriptException> futureJS = new CompletableFuture<>();
        devTools.getDomains().events().addJavascriptExceptionListener(futureJS::complete);

        driver.get("https://bonigarcia.dev/selenium-webdriver-java/console-logs.html");
        ConsoleEvent consoleEvent = futureEvents.get(5, TimeUnit.SECONDS);
        System.out.println("Console event: {} " + consoleEvent);
        JavascriptException js = futureJS.get(5, TimeUnit.SECONDS);
        System.out.println("JS exception: {} " + js);
    }
}
