package tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.*;

public class SearchTest {
    private static WebDriver webDriver;
    private static String baseUrl;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "/Users/Ilma Hodžić/Desktop/chromedriver-win64/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        webDriver = new ChromeDriver(options);
        baseUrl = "https://www.w3schools.com/";
    }

    @AfterAll
    public static void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }

    @Test
    public void testSearchRedirect() throws InterruptedException {
        webDriver.get(baseUrl);

        WebElement searchField = webDriver.findElement(By.xpath("//*[@id=\"tnb-google-search-input\"]"));
        searchField.sendKeys("PHP");
        Thread.sleep(2000);

        WebElement searchButton = webDriver.findElement(By.xpath("//*[@id=\"tnb-google-search-icon\"]"));
        searchButton.click();
        Thread.sleep(5000);

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        WebElement suggestionBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"___gcse_0\"]/div/div/div[1]")));

        assertTrue(suggestionBox.isDisplayed(), "The search suggestion box is not displayed.");
    }

    @Test
    public void testInvalidSearchInput() throws InterruptedException {
        webDriver.get(baseUrl);

        WebElement searchField = webDriver.findElement(By.xpath("//*[@id=\"tnb-google-search-input\"]"));
        searchField.sendKeys("baklava");
        Thread.sleep(2000);

        WebElement searchButton = webDriver.findElement(By.xpath("//*[@id=\"tnb-google-search-icon\"]"));
        searchButton.click();
        Thread.sleep(5000);

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        WebElement noResultMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"___gcse_0\"]/div/div/div[1]/div[6]/div[2]/div/div/div[1]/div[1]/div/div")));

        assertTrue(noResultMessage.isDisplayed(), "The 'No result' message is not displayed.");
    }

    @Test
    public void testEmptySearchInput() throws InterruptedException {
        webDriver.get(baseUrl);

        WebElement searchField = webDriver.findElement(By.xpath("//*[@id=\"tnb-google-search-input\"]"));
        searchField.clear();

        WebElement searchButton = webDriver.findElement(By.xpath("//*[@id=\"tnb-google-search-icon\"]"));
        searchButton.click();
        Thread.sleep(3000);

        String currentUrl = webDriver.getCurrentUrl();
        assertNotEquals(currentUrl, baseUrl, "Page redirected unexpectedly on empty search.");
    }
}

