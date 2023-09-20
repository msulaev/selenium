package storage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.html5.SessionStorage;
import org.openqa.selenium.html5.WebStorage;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LocalStorage {

    WebDriver driver;

    /*
    * WebStorage interface is used to manage local and session storage. It can be accessed using the manage() method of WebDriver interface.
    * LocalStorage and SessionStorage interfaces are used to manage local and session storage respectively.
    * You could add, read, edit and delete items from local and session storage using these interfaces.
    * */
    @Test
    void testWebStorage() {
        driver = WebDriverManager.chromedriver().create();
        driver.get(
                "https://bonigarcia.dev/selenium-webdriver-java/web-storage.html");
        WebStorage webStorage = (WebStorage) driver;

        var localStorage = webStorage.getLocalStorage();
        System.out.println("Local storage elements: " + localStorage.size());

        SessionStorage sessionStorage = webStorage.getSessionStorage();
        sessionStorage.keySet()
                .forEach(key ->
                                System.out.println("Session storage: " + key + "="
                                        + sessionStorage.getItem(key)));
        assertThat(sessionStorage.size()).isEqualTo(2);

        sessionStorage.setItem("new element", "new value");
        assertThat(sessionStorage.size()).isEqualTo(3);

        driver.findElement(By.id("display-session")).click();
    }
}
