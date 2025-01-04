package tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

public class SecurityComplianceTest {
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
    public void testHttpsEnforcement() {
        webDriver.get(baseUrl);
        String currentUrl = webDriver.getCurrentUrl();
        assertTrue(currentUrl.startsWith("https://"), "The website should enforce HTTPS.");
    }

    @Test
    public void testSqlInjection() throws InterruptedException {
        webDriver.get("https://profile.w3schools.com/login?redirect_url=https%3A%2F%2Fwww.w3schools.com%2F");
        WebElement usernameField = webDriver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div/div[1]/div/div[2]/div/div[5]/div/form/div[1]/input[1]"));
        WebElement passwordField = webDriver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div/div[1]/div/div[2]/div/div[5]/div/form/div[1]/input[2]"));

        usernameField.sendKeys("admin' OR '1'='1");
        passwordField.sendKeys("password");
        WebElement loginButton = webDriver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div/div[1]/div/div[2]/div/div[5]/div/form/div[3]/button[2]"));
        loginButton.click();
        Thread.sleep(2000);
        WebElement errorMessage = webDriver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div/div[1]/div/div[2]/div/div[5]/div/form/div[2]"));
        assertTrue(errorMessage.isDisplayed(), "SQL Injection should not succeed.");
    }
    @Test
    public void testCORSHeaders() {
        webDriver.get(baseUrl);
        JavascriptExecutor jsExecutor = (JavascriptExecutor) webDriver;
        String corsHeaders = (String) jsExecutor.executeScript(
                "return document.querySelector('meta[http-equiv=\"Access-Control-Allow-Origin\"]')?.content;");
        assertNull(corsHeaders, "The website should not allow CORS from unauthorized origins.");
    }
}