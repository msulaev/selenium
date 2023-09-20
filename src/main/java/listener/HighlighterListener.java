package listener;

import org.openqa.selenium.*;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.openqa.selenium.support.events.WebDriverListener;

public class HighlighterListener implements WebDriverListener {

    private void highlightElement(WebElement element, WebDriver driver) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        String originalStyle = element.getAttribute("style");
        String newStyle = "border: 2px solid red; background-color: yellow;";

        jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, newStyle);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, originalStyle);
    }

    @Override
    public void afterFindElement(WebDriver driver, By locator, WebElement result) {
        highlightElement(result, driver);
    }
}