package tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

public class ThirdPartyIntegrationsTest {
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
    public void testLoginWithGoogle() throws InterruptedException {
        driver.get(baseUrl);

        WebElement googleLoginButton = driver.findElement(By.xpath("//div[contains(text(), 'Google')]"));
        googleLoginButton.click();

        Thread.sleep(35000);

        assertEquals(driver.getCurrentUrl(), "https://pathfinder.w3schools.com/", "Google login did not redirect to the expected page.");
    }

    @Test
    public void testLoginWithFacebook() throws InterruptedException {
        driver.get(baseUrl);

        WebElement facebookLoginButton = driver.findElement(By.xpath("//div[contains(text(), 'Facebook')]"));
        facebookLoginButton.click();

        Thread.sleep(35000);

        assertEquals(driver.getCurrentUrl(), "https://pathfinder.w3schools.com/", "Facebook login did not redirect to the expected page.");
    }

    @Test
    public void testLoginWithGithub() throws InterruptedException {
        driver.get(baseUrl);

        WebElement githubLoginButton = driver.findElement(By.xpath("//div[contains(text(), 'Github')]"));
        githubLoginButton.click();

        Thread.sleep(35000);

        assertEquals(driver.getCurrentUrl(), "https://pathfinder.w3schools.com/", "GitHub login did not redirect to the expected page.");
    }

}
