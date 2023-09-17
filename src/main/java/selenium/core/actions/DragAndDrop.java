package selenium.core.actions;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DragAndDrop {

    WebDriver driver;

    @Test
    void testDragAndDrop() {
        driver = WebDriverManager.chromedriver().create();
        Actions actions = new Actions(driver);

        driver.get(
                "https://bonigarcia.dev/selenium-webdriver-java/drag-and-drop.html");

        WebElement draggable = driver.findElement(By.id("draggable"));
        WebElement target = driver.findElement(By.id("target"));

        actions.dragAndDrop(draggable, target).build().perform();
        assertThat(target.getLocation()).isEqualTo(draggable.getLocation());
    }
}
