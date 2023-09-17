package selenium.core.actions;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DoubleClick {
    WebDriver driver;


    @Test
    void testContextAndDoubleClick() {
        driver = WebDriverManager.chromedriver().create();
        Actions actions = new Actions(driver);

        driver.get(
                "https://bonigarcia.dev/selenium-webdriver-java/dropdown-menu.html");

        WebElement dropdown2 = driver.findElement(By.id("my-dropdown-2"));
        WebElement contextMenu2 = driver.findElement(By.id("context-menu-2"));
        WebElement dropdown3 = driver.findElement(By.id("my-dropdown-3"));
        WebElement contextMenu3 = driver.findElement(By.id("context-menu-3"));

        actions.contextClick(dropdown2).build().perform();
        assertThat(contextMenu2.isDisplayed()).isTrue();

        actions.doubleClick(dropdown3).build().perform();
        assertThat(contextMenu3.isDisplayed()).isTrue();
    }
}
