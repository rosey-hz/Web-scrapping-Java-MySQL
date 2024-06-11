package utility;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;

public class Utils {
    public static WebElement findElementWithRetry(WebDriver driver, By locator, int maxAttempts) {
        int attempts = 0;
        while (attempts < maxAttempts) {
            try {
                return driver.findElement(locator);
            } catch (StaleElementReferenceException e) {
                // Element is stale, retry the operation
                System.out.println("Element is stale, retrying...");
            }
            attempts++;
        }
        throw new NoSuchElementException("Element not found after " + maxAttempts + " attempts");
    }
}

