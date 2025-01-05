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

public class LoginTest {
    private static WebDriver driver;
    private static String baseUrl ;

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
    public void testInvalidLogin() throws InterruptedException {
        driver.get(baseUrl);

        WebElement emailField = driver.findElement(By.cssSelector("input[name='email']"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));

        emailField.sendKeys("invalid@example.com");
        passwordField.sendKeys("wrongpassword");
        loginButton.click();

        Thread.sleep(2000);

        WebElement errorMessage = driver.findElement(By.cssSelector(".LoginForm_error_text__4fzmN"));
        assertTrue(errorMessage.isDisplayed(), "Error message is not displayed.");
        assertTrue(errorMessage.getText().contains("Sorry, looks like that’s the wrong email or password."), "Error message does not contain expected text.");
    }

    @Test
    public void testEmptyFields() throws InterruptedException {
        driver.get(baseUrl);

        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));
        loginButton.click();

        Thread.sleep(2000);

        WebElement errorMessage = driver.findElement(By.cssSelector(".LoginForm_error_text__4fzmN"));
        assertTrue(errorMessage.isDisplayed(), "Error message is not displayed.");
        assertTrue(errorMessage.getText().contains("Please enter your email and password"), "Error message does not contain expected text.");
    }

    @Test
    public void testSuccessfulLogin() throws InterruptedException {
        driver.get(baseUrl);

        WebElement emailField = driver.findElement(By.cssSelector("input[name='email']"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));

        emailField.sendKeys("emina.omercevic@stu.ibu.edu.ba");
        passwordField.sendKeys("Eminaemina123!");
        loginButton.click();

        Thread.sleep(5000);

        String expectedUrl = "https://pathfinder.w3schools.com/";
        String actualUrl = driver.getCurrentUrl();
        assertTrue(actualUrl.equals(expectedUrl), "Redirection after successful login failed.");
    }
}
