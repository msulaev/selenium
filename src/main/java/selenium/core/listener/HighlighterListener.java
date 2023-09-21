package selenium.core.listener;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;

public class HighlighterListener implements WebDriverListener {
    /*
     * You need to implement the WebDriverListener interface in the event listener class. You can override the methods that you want to use.
     * In this example, we are overriding and afterFindElement() methods for highlighting the element by js.
     */
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