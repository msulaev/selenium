package driver.capabilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.slf4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

public class Logs {
    WebDriver driver;

    /*
     * LoggingPreferences is used to set the logging preferences for the browser.
     * enable(LogType.BROWSER, Level.ALL) is used to enable the browser logs and set the log level to ALL.
     * LogType.Browser means that we are enabling the browser logs.
     * Level.ALL means that we are setting the log level to ALL.
     * */
    @Test
    void testBrowserLogs() {
        LoggingPreferences logs = new LoggingPreferences();
        logs.enable(LogType.BROWSER, Level.ALL);

        ChromeOptions options = new ChromeOptions();
        options.setCapability(ChromeOptions.LOGGING_PREFS, logs);

        driver = WebDriverManager.chromedriver().capabilities(options).create();

        driver.get(
                "https://bonigarcia.dev/selenium-webdriver-java/console-logs.html");

        LogEntries browserLogs = driver.manage().logs().get(LogType.BROWSER);
        Assertions.assertThat(browserLogs.getAll()).isNotEmpty();
        browserLogs.forEach(l -> System.out.println("{} " + l));
    }

    @Test
    public void testDockerGatherLogsChrome() {
        final Logger log = getLogger(lookup().lookupClass());

        WebDriverManager wdm = WebDriverManager.chromedriver().browserInDocker()
                .watch();
        driver = wdm.create();
        driver.get(
                "https://bonigarcia.dev/selenium-webdriver-java/console-logs.html");

        List<Map<String, Object>> logMessages = wdm.getLogs();

        assertThat(logMessages.size()).isEqualTo(5);

        logMessages.forEach(map -> log.debug("[{}] [{}] {}",
                map.get("datetime"),
                String.format("%1$-14s",
                        map.get("source").toString().toUpperCase() + "."
                                + map.get("type").toString().toUpperCase()),
                map.get("message")));
    }

}
