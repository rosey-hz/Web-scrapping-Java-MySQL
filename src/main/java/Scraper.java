import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Scraper {
    private static WebDriverWait wait;
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/area";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "Rosey@123";
    private static Set<String> uniqueRecords = new HashSet<>();

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "/Users/rosey/Documents/chromedriver-mac-x64/chromedriver");
        WebDriver driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.eximguru.com/traderesources/pincode.aspx");
        scrapeMultiplePages(driver);
        driver.quit();
    }

    private static void scrapeMultiplePages(WebDriver driver) {
        for (int pageNum = 1; pageNum <= 10759; pageNum++) {
            scrapePage(driver);
            if (pageNum < 10760) {
                navigateToNextPage(driver);
            }
        }
    }


    private static void scrapePage(WebDriver driver) {
        try {
            WebElement tableElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//table[@class='table table-bordered table-striped'])[2]")));

            List<WebElement> rows = tableElement.findElements(By.xpath("//tr[@class='sliststyle10' or @class='sliststyle6']"));

            for (WebElement row : rows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));
                if (cells.size() == 4) {
                    String pinCode = cells.get(0).getText();
                    String district = cells.get(1).getText();
                    String state = cells.get(2).getText();
                    String area = cells.get(3).getText();

                    String record = pinCode + "-" + district + "-" + state + "-" + area;
                    if (!uniqueRecords.contains(record)) {
                        saveDataToDatabase(pinCode, district, state, area);
                        uniqueRecords.add(record);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void navigateToNextPage(WebDriver driver) {
        try {
            System.out.println("Navigating to the next page...");
            WebElement pagination = driver.findElement(By.id("uxContentPlaceHolder_ResourceAndGuideUserControl_ResourceAndGuideGrid_myGridView_bottomPagerLinks")); // Locate the pagination element by ID
            List<WebElement> pageLinks = pagination.findElements(By.tagName("a")); // Find all <a> elements within the pagination
            System.out.println("Total page links found: " + pageLinks.size()); // Print the count of page links

            boolean nextPageClicked = false;
            for (WebElement link : pageLinks) {
                String linkText = link.getText();
                System.out.println("Attempting to click on page link: " + linkText);
                if (linkText.equals(">>")) {
                    link.click(); // Click the link if its text is ">>" (representing the "Next" button)
                    System.out.println("Clicked on the next page link.");
                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                    wait.until(ExpectedConditions.urlContains("pincode")); // Wait until the URL changes
                    System.out.println("Next page loaded successfully.");
                    nextPageClicked = true;
                    break; // Exit the loop after clicking the next page link
                }
            }

            if (!nextPageClicked) {
                System.out.println("No 'Next' link found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void saveDataToDatabase(String pinCode, String district, String state, String area) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "INSERT INTO AreaInformation (PinCode, District, State, Area) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, pinCode);
                stmt.setString(2, district);
                stmt.setString(3, state);
                stmt.setString(4, area);
                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Data saved to the database successfully! - PinCode: " + pinCode + ", District: " + district + ", State: " + state + ", Area: " + area);
                } else {
                    System.out.println("Failed to insert data for - PinCode: " + pinCode + ", District: " + district + ", State: " + state + ", Area: " + area);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}