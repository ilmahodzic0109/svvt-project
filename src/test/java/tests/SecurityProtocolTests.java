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

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SecurityProtocolTests {
    private static WebDriver driver;
    private static String baseUrl;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "/Users/HP/chromedriver-win64/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        baseUrl = "https://profile.w3schools.com/login";
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testHttpsProtocol() {
        driver.get(baseUrl);

        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.startsWith("https://"), "The website is not served over HTTPS.");
    }

    @Test
    public void testSecureCookie() {
        driver.get(baseUrl);

        driver.manage().getCookies().forEach(cookie ->
                assertTrue(cookie.isSecure(), "Cookie is not marked as secure: " + cookie.getName())
        );
    }

    @Test
    public void testBruteForceProtection() throws InterruptedException {
        driver.get(baseUrl);

        WebElement emailField = driver.findElement(By.cssSelector("input[name='email']"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));

        for (int i = 0; i < 10; i++) {
            emailField.clear();
            emailField.sendKeys("invalid@example.com");
            passwordField.clear();
            passwordField.sendKeys("wrongpassword");
            loginButton.click();
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".LoginForm_error_text__4fzmN")));

        assertTrue(errorMessage.isDisplayed(), "Brute force protection is not active.");
        assertTrue(errorMessage.getText().contains("Not authorized"),
                "Brute force protection message is not as expected.");
    }
}
