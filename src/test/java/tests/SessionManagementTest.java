package tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SessionManagementTest {
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
    public void testSessionPersistenceOnPageRefresh() throws InterruptedException {
        driver.get(baseUrl);

        WebElement emailField = driver.findElement(By.cssSelector("input[name='email']"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));

        emailField.sendKeys("emina.omercevic@stu.ibu.edu.ba");
        passwordField.sendKeys("Eminaemina123!");
        loginButton.click();

        Thread.sleep(5000);

        String sessionIdBefore = driver.manage().getCookieNamed("userSession").getValue();

        driver.navigate().refresh();

        String sessionIdAfter = driver.manage().getCookieNamed("userSession").getValue();

        assertTrue(sessionIdBefore.equals(sessionIdAfter), "Session ID changed after page refresh.");
    }

    @Test
    public void testSessionTimeout() throws InterruptedException {
        driver.get(baseUrl);

        WebElement emailField = driver.findElement(By.cssSelector("input[name='email']"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));

        emailField.sendKeys("emina.omercevic@stu.ibu.edu.ba");
        passwordField.sendKeys("Eminaemina123!");
        loginButton.click();

        Thread.sleep(5000);

        String sessionId = driver.manage().getCookieNamed("userSession").getValue();

        Thread.sleep(2000000);

        String newSessionId = driver.manage().getCookieNamed("userSession").getValue();

        assertTrue(!sessionId.equals(newSessionId), "Session did not expire as expected after timeout.");
    }
}
