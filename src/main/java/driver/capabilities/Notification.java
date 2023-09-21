package driver.capabilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Notification {
    WebDriver driver;


    /*
    * profile.default_content_setting_values.notifications is used to set the notification permission for the browser.
    * */
    @Test
    public void testNotifications() {
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 1);
        options.setExperimentalOption("prefs", prefs);

        driver = WebDriverManager.chromedriver().capabilities(options).create();

        driver.get(
                "https://bonigarcia.dev/selenium-webdriver-java/notifications.html");
        JavascriptExecutor js = (JavascriptExecutor) driver;

        String script = String.join("\n",
                "const callback = arguments[arguments.length - 1];",
                "const OldNotify = window.Notification;",
                "function newNotification(title, options) {",
                "    callback(title);",
                "    return new OldNotify(title, options);",
                "}",
                "newNotification.requestPermission = OldNotify.requestPermission.bind(OldNotify);",
                "Object.defineProperty(newNotification, 'permission', {",
                "    get: function() {",
                "        return OldNotify.permission;",
                "    }",
                "});",
                "window.Notification = newNotification;",
                "document.getElementById('notify-me').click();");
        System.out.println("Executing the following script asynchronously:\n{} " + script);

        Object notificationTitle = js.executeAsyncScript(script);
        assertThat(notificationTitle).isEqualTo("This is a notification");
    }

}

