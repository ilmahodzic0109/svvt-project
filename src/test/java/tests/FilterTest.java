package tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
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

public class FilterTest {
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
    public void testCompleteFilterFlow() throws InterruptedException {
        webDriver.get(baseUrl);
        Thread.sleep(2000);

        WebElement filterButton = webDriver.findElement(By.xpath("//*[@id='navbtn_tutorials']"));
        filterButton.click();
        Thread.sleep(2000);
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        WebElement filterInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='filter-tutorials-input']")));
        filterInput.sendKeys("html");
        Thread.sleep(2000);

        WebElement tutorialHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"tutorials_html_css_links_list\"]/h3")));
        Assertions.assertTrue(tutorialHeader.getText().contains("HTML and CSS"), "Header should contain 'HTML and CSS' after typing 'html'.");

        WebElement clearButton = webDriver.findElement(By.xpath("//*[@id='tutorials_list']/div[1]/div[2]/div/div"));
        clearButton.click();
        WebElement tutorialButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='tutorials_html_css_links_list']/div[1]/a[2]")));
        Assertions.assertTrue(tutorialButton.isEnabled(), "The button should be clickable.");
        Thread.sleep(2000);
        Assertions.assertEquals("", filterInput.getAttribute("value"), "The filter input should be cleared after clicking the clear button.");

        filterInput.sendKeys("ilma");
        Thread.sleep(2000);

        WebElement noMatchMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='no-match']")));
        Assertions.assertTrue(noMatchMessage.isDisplayed(), "The 'No match found' message should be visible when typing 'ilma'.");

    }
}
