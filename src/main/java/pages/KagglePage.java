package pages;

import config.ConfigApplication;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class KagglePage {
    private static final Logger logger = Logger.getLogger(KagglePage.class.getName());
    private WebDriver driver;
    private WebDriverWait wait;

    public KagglePage(WebDriver driver) {
        this.driver = driver;
        this.wait = ConfigApplication.getWebDriverWait(Duration.ofSeconds(60));
        PageFactory.initElements(driver, this);
    }


    public List<String[]> scrapePincodeData() {
        List<String[]> data = new ArrayList<>();

        try {
            // Wait for the target element to be present on the page
            WebElement targetElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//tr[@class='sliststyle10' or @class='sliststyle6']")));
            logger.info("Target element is present. Proceeding to scrape data.");

            // Locate all rows containing pincode data
            List<WebElement> rows = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//tr[@class='sliststyle10' or @class='sliststyle6']")));

            if (rows.isEmpty()) {
                logger.warning("No rows found. Check the XPath or ensure the page has loaded correctly.");
            } else {
                logger.info("Number of rows found: " + rows.size());
                for (WebElement row : rows) {
                    List<WebElement> cells = row.findElements(By.tagName("td"));
                    if (cells.size() >= 3) {
                        try {
                            int pincode = Integer.parseInt(cells.get(0).getText());
                            String district = cells.get(1).getText();
                            String state = cells.get(2).getText();
                            String area = cells.get(3).getText();
                            data.add(new String[]{String.valueOf(pincode), district, state, area});
                        } catch (NumberFormatException e) {
                            logger.warning("Invalid pincode format: " + cells.get(0).getText());
                        }
                    } else {
                        logger.warning("Row does not have enough cells: " + row.getText());
                    }
                }

                // Logging to verify the data retrieved
                for (String[] row : data) {
                    logger.info("Pincode: " + row[0] + ", District: " + row[1] + ", State: " + row[2]);
                }
            }
        } catch (Exception e) {
            logger.severe("An error occurred while scraping pincode data: " + e.getMessage());
        }

        return data;
    }

}
