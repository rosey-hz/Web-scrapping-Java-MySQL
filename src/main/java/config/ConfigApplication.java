package config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ConfigApplication {
    private static WebDriver driver;

    static {
        System.setProperty("webdriver.chrome.driver", "/Users/rosey/Documents/chromedriver-mac-x64/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(120));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(120));
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static WebDriverWait getWebDriverWait(Duration timeout) {
        if (driver == null) {
            throw new IllegalStateException("WebDriver is not initialized. Please initialize it before calling getWebDriverWait.");
        }
        return new WebDriverWait(driver, timeout);
    }
}
















//package config;
//
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//import java.time.Duration;
//
//public class ConfigApplication {
//    private static WebDriver driver;
//
//    static {
//        System.setProperty("webdriver.chrome.driver", "/Users/rosey/Documents/chromedriver-mac-x64/chromedriver");
//        // Update path
//        driver = new ChromeDriver();
//    }
//
//    public static WebDriver getDriver() {
//        return driver;
//    }
//
//    public static WebDriverWait getWebDriverWait(Duration timeout) {
//        if (driver == null) {
//            throw new IllegalStateException("WebDriver is not initialized. Please initialize it before calling getWebDriverWait.");
//        }
//        return new WebDriverWait(driver, timeout);
//    }
//
//
//    public static boolean elementExists(WebElement element, Duration timeout) {
//        try {
//            WebDriverWait wait = new WebDriverWait(driver, timeout);
//            wait.until(ExpectedConditions.visibilityOf(element));
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    public static void waitElement(Duration timeout, WebElement element) {
//        new WebDriverWait(driver, timeout).until(ExpectedConditions.visibilityOf(element));
//    }
//}
//
//
//
//
//
//
//
//
//
//
//















