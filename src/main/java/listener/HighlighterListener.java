package listener;

import org.openqa.selenium.*;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.openqa.selenium.support.events.WebDriverListener;

public class HighlighterListener implements WebDriverListener {
    private final WebDriver driver;
    private final JavascriptExecutor jsExecutor;

    public HighlighterListener(WebDriver driver) {
        this.driver = driver;
        this.jsExecutor = (JavascriptExecutor) driver;
    }


    public void beforeFindElement(By by, WebElement element, WebDriver driver) {
        if (element != null) {
            highlightElement(element);
        }
    }

    @Override
    public void afterFindElement(By by, WebElement element, WebDriver driver) {
        if (element != null) {
            highlightElement(element);
        }
    }

    private void highlightElement(WebElement element) {
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

}