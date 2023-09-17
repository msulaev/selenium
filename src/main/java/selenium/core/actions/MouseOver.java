package selenium.core.actions;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.locators.RelativeLocator;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MouseOver {
    WebDriver driver;

    @Test
    public void testMouseOver() {
        driver = WebDriverManager.chromedriver().create();
        Actions actions = new Actions(driver);
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/mouse-over.html");

        List<String> imageList = Arrays.asList("compass", "calendar", "award",
                "landscape");

        imageList.stream().forEach(imageName -> {
            String xpath = String.format("//img[@src='img/%s.png']", imageName);
            WebElement image = driver.findElement(By.xpath(xpath));
            actions.moveToElement(image).build().perform();

            WebElement caption = driver.findElement(
                    RelativeLocator.with(By.tagName("div")).near(image));

            assertThat(caption.getText()).containsIgnoringCase(imageName);
        });
    }
}
