package selenium.core.cookies;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Cookies {
    WebDriver driver;

    @BeforeEach
    public void setupClass() {
        driver = WebDriverManager.chromedriver().create();
    }

    /*
    * Cookies are small pieces of data stored in the browser. They are used to store information about the user.
    * WebDriver.Options interface is used to manage cookies. It can be accessed using the manage() method of WebDriver interface.
    * You could add, read, edit and delete cookies using the WebDriver.Options interface.
    * */

    @Test
    void addCookies() {
        driver.get(
                "https://bonigarcia.dev/selenium-webdriver-java/cookies.html");

        WebDriver.Options options = driver.manage();
        Cookie newCookie = new Cookie("new-cookie-key", "new-cookie-value");
        options.addCookie(newCookie);
        String readValue = options.getCookieNamed(newCookie.getName())
                .getValue();
        assertThat(newCookie.getValue()).isEqualTo(readValue);

        driver.findElement(By.id("refresh-cookies")).click();
    }

    @Test
    void readCookies() {
        driver.get(
                "https://bonigarcia.dev/selenium-webdriver-java/cookies.html");

        WebDriver.Options options = driver.manage();
        Set<Cookie> cookies = options.getCookies();
        assertThat(cookies.size()).isEqualTo(2);

        Cookie username = options.getCookieNamed("username");
        assertThat(username.getValue()).isEqualTo("John Doe");
        assertThat(username.getPath()).isEqualTo("/");

        driver.findElement(By.id("refresh-cookies")).click();
    }

    @Test
    void eEditCookie() {
        driver.get(
                "https://bonigarcia.dev/selenium-webdriver-java/cookies.html");

        WebDriver.Options options = driver.manage();
        Cookie username = options.getCookieNamed("username");
        Cookie editedCookie = new Cookie(username.getName(), "new-value");
        options.addCookie(editedCookie);

        Cookie readCookie = options.getCookieNamed(username.getName());
        assertThat(editedCookie).isEqualTo(readCookie);

        driver.findElement(By.id("refresh-cookies")).click();
    }

    @Test
    void testDeleteCookies() {
        driver.get(
                "https://bonigarcia.dev/selenium-webdriver-java/cookies.html");

        WebDriver.Options options = driver.manage();
        Set<Cookie> cookies = options.getCookies();
        Cookie username = options.getCookieNamed("username");
        options.deleteCookie(username);

        assertThat(options.getCookies().size()).isEqualTo(cookies.size() - 1);

        driver.findElement(By.id("refresh-cookies")).click();
    }

}
