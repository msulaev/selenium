package selenium.core.actions;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class ClickAndHold {
    WebDriver driver;

    @Test
    void testClickAndHold() {
        driver = WebDriverManager.chromedriver().create();
        Actions actions = new Actions(driver);

        driver.get("https://bonigarcia.dev/selenium-webdriver-java/draw-in-canvas.html");

        WebElement canvas = driver.findElement(By.tagName("canvas"));
        actions.moveToElement(canvas).clickAndHold();

        // Calculate the number of points to move
        int numPoints = 10;
        int radius = 30;

        // Move the mouse to the desired number of points
        for (int i = 0; i <= numPoints; i++) {
            double angle = Math.toRadians((double) (360 * i) / numPoints);
            double x = Math.sin(angle) * radius;
            double y = Math.cos(angle) * radius;
            actions.moveByOffset((int) x, (int) y);
        }

        actions.release(canvas).build().perform();
    }
}
