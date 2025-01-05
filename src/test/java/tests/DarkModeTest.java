package tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DarkModeTest {
    private static WebDriver driver;
    private static String baseUrl;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "/Users/HP/chromedriver-win64/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        baseUrl = "https://www.w3schools.com/";
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testToggleDarkMode() throws InterruptedException {
        driver.get(baseUrl);

        WebElement darkModeToggle = driver.findElement(By.id("tnb-dark-mode-toggle-btn"));

        darkModeToggle.click();
        Thread.sleep(2000);

        WebElement body = driver.findElement(By.tagName("body"));
        String bodyClass = body.getAttribute("class");
        assertTrue(bodyClass.contains("darktheme"), "Dark mode was not applied!");

        darkModeToggle.click();
        Thread.sleep(2000);

        bodyClass = body.getAttribute("class");
        assertTrue(!bodyClass.contains("darktheme"), "Dark mode was not disabled!");
    }

    @Test
    public void testDarkModePersistsAcrossPages() throws InterruptedException {
        driver.get(baseUrl);

        WebElement darkModeToggle = driver.findElement(By.id("tnb-dark-mode-toggle-btn"));
        darkModeToggle.click();
        Thread.sleep(2000);

        driver.get("https://www.w3schools.com/html/default.asp");
        Thread.sleep(2000);

        WebElement body = driver.findElement(By.tagName("body"));
        String bodyClass = body.getAttribute("class");
        assertTrue(bodyClass.contains("darktheme"), "Dark mode did not persist across pages!");
    }

    @Test
    public void testDarkModeAccessibility() throws InterruptedException {
        driver.get(baseUrl);

        WebElement darkModeToggle = driver.findElement(By.id("tnb-dark-mode-toggle-btn"));
        darkModeToggle.click();
        Thread.sleep(2000);

        WebElement body = driver.findElement(By.tagName("body"));
        String backgroundColor = body.getCssValue("background-color");
        String textColor = body.getCssValue("color");

        assertTrue(!backgroundColor.equals(textColor), "Contrast between background and text is insufficient!");
    }
}
