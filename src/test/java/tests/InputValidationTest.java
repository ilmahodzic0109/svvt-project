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

public class InputValidationTest {
    private static WebDriver driver;
    private static String baseUrl;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "/Users/HP/chromedriver-win64/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        baseUrl = "https://profile.w3schools.com/signup";
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
    @Test
    public void testEmptyFields() {
        driver.get(baseUrl);

        WebElement signUpButton = driver.findElement(By.cssSelector("button[type='submit']"));
        signUpButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".SignUpForm_error_text__vt1BO")));
        assertTrue(errorMessage.isDisplayed(), "Error message is not displayed for empty fields.");
        assertTrue(errorMessage.getText().contains("Please fill in all fields"), "Error message does not contain expected text.");
    }

    @Test
    public void testInvalidEmailFormat() {
        driver.get(baseUrl);

        WebElement emailField = driver.findElement(By.cssSelector("input[placeholder='email']"));
        WebElement passwordField = driver.findElement(By.cssSelector("input[placeholder='password']"));
        WebElement firstNameField = driver.findElement(By.cssSelector("input[placeholder='first name']"));
        WebElement lastNameField = driver.findElement(By.cssSelector("input[placeholder='last name']"));
        WebElement signUpButton = driver.findElement(By.cssSelector("button[type='submit']"));

        emailField.sendKeys("invalid-email");
        passwordField.sendKeys("ValidP@ssw0rd!");
        firstNameField.sendKeys("John");
        lastNameField.sendKeys("Doe");
        signUpButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".SignUpForm_error_text__vt1BO")));
        assertTrue(errorMessage.isDisplayed(), "Error message is not displayed for invalid email format.");
        assertTrue(errorMessage.getText().contains("Please enter a valid email address"), "Error message does not contain expected text.");
    }

    @Test
    public void testMissingFirstName() {
        driver.get(baseUrl);

        WebElement emailField = driver.findElement(By.cssSelector("input[placeholder='email']"));
        WebElement passwordField = driver.findElement(By.cssSelector("input[placeholder='password']"));
        WebElement lastNameField = driver.findElement(By.cssSelector("input[placeholder='last name']"));
        WebElement signUpButton = driver.findElement(By.cssSelector("button[type='submit']"));

        emailField.sendKeys("valid.email@example.com");
        passwordField.sendKeys("ValidP@ssw0rd!");
        lastNameField.sendKeys("Doe");
        signUpButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".SignUpForm_error_text__vt1BO")));
        assertTrue(errorMessage.isDisplayed(), "Error message is not displayed for missing first name.");
        assertTrue(errorMessage.getText().contains("Please fill in all fields"), "Error message does not contain expected text.");
    }

    @Test
    public void testMissingLastName() {
        driver.get(baseUrl);

        WebElement emailField = driver.findElement(By.cssSelector("input[placeholder='email']"));
        WebElement passwordField = driver.findElement(By.cssSelector("input[placeholder='password']"));
        WebElement firstNameField = driver.findElement(By.cssSelector("input[placeholder='first name']"));
        WebElement signUpButton = driver.findElement(By.cssSelector("button[type='submit']"));

        emailField.sendKeys("valid.email@example.com");
        passwordField.sendKeys("ValidP@ssw0rd!");
        firstNameField.sendKeys("John");
        signUpButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".SignUpForm_error_text__vt1BO")));
        assertTrue(errorMessage.isDisplayed(), "Error message is not displayed for missing last name.");
        assertTrue(errorMessage.getText().contains("Please fill in all fields"), "Error message does not contain expected text.");
    }

    @Test
    public void testPasswordMissingUppercase() {
        driver.get(baseUrl);

        WebElement emailField = driver.findElement(By.cssSelector("input[placeholder='email']"));
        WebElement passwordField = driver.findElement(By.cssSelector("input[placeholder='password']"));
        WebElement firstNameField = driver.findElement(By.cssSelector("input[placeholder='first name']"));
        WebElement lastNameField = driver.findElement(By.cssSelector("input[placeholder='last name']"));
        WebElement signUpButton = driver.findElement(By.cssSelector("button[type='submit']"));

        emailField.sendKeys("test@example.com");
        passwordField.sendKeys("password123!");
        firstNameField.sendKeys("John");
        lastNameField.sendKeys("Doe");
        signUpButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".SignUpForm_error_text__vt1BO")));
        assertTrue(errorMessage.isDisplayed(), "Error message is not displayed for missing uppercase character.");
        assertTrue(errorMessage.getText().contains("Password requires at least one uppercase character"), "Error message does not contain expected text.");
    }

    @Test
    public void testPasswordMissingNumber() {
        driver.get(baseUrl);

        WebElement emailField = driver.findElement(By.cssSelector("input[placeholder='email']"));
        WebElement passwordField = driver.findElement(By.cssSelector("input[placeholder='password']"));
        WebElement firstNameField = driver.findElement(By.cssSelector("input[placeholder='first name']"));
        WebElement lastNameField = driver.findElement(By.cssSelector("input[placeholder='last name']"));
        WebElement signUpButton = driver.findElement(By.cssSelector("button[type='submit']"));

        emailField.sendKeys("test@example.com");
        passwordField.sendKeys("Password!");
        firstNameField.sendKeys("John");
        lastNameField.sendKeys("Doe");
        signUpButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".SignUpForm_error_text__vt1BO")));
        assertTrue(errorMessage.isDisplayed(), "Error message is not displayed for missing number.");
        assertTrue(errorMessage.getText().contains("Password requires at least one number"), "Error message does not contain expected text.");
    }

    @Test
    public void testPasswordMissingSpecialCharacter() {
        driver.get(baseUrl);

        WebElement emailField = driver.findElement(By.cssSelector("input[placeholder='email']"));
        WebElement passwordField = driver.findElement(By.cssSelector("input[placeholder='password']"));
        WebElement firstNameField = driver.findElement(By.cssSelector("input[placeholder='first name']"));
        WebElement lastNameField = driver.findElement(By.cssSelector("input[placeholder='last name']"));
        WebElement signUpButton = driver.findElement(By.cssSelector("button[type='submit']"));

        emailField.sendKeys("test@example.com");
        passwordField.sendKeys("Password123");
        firstNameField.sendKeys("John");
        lastNameField.sendKeys("Doe");
        signUpButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".SignUpForm_error_text__vt1BO")));
        assertTrue(errorMessage.isDisplayed(), "Error message is not displayed for missing special character.");
        assertTrue(errorMessage.getText().contains("Password requires at least one special character"), "Error message does not contain expected text.");
    }

    @Test
    public void testPasswordMissingLowercase() {
        driver.get(baseUrl);

        WebElement emailField = driver.findElement(By.cssSelector("input[placeholder='email']"));
        WebElement passwordField = driver.findElement(By.cssSelector("input[placeholder='password']"));
        WebElement firstNameField = driver.findElement(By.cssSelector("input[placeholder='first name']"));
        WebElement lastNameField = driver.findElement(By.cssSelector("input[placeholder='last name']"));
        WebElement signUpButton = driver.findElement(By.cssSelector("button[type='submit']"));

        emailField.sendKeys("test@example.com");
        passwordField.sendKeys("PASSWORD123!");
        firstNameField.sendKeys("John");
        lastNameField.sendKeys("Doe");
        signUpButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".SignUpForm_error_text__vt1BO")));
        assertTrue(errorMessage.isDisplayed(), "Error message is not displayed for missing lowercase character.");
        assertTrue(errorMessage.getText().contains("Password requires at least one lowercase character"), "Error message does not contain expected text.");
    }

    @Test
    public void testPasswordTooShort() {
        driver.get(baseUrl);

        WebElement emailField = driver.findElement(By.cssSelector("input[placeholder='email']"));
        WebElement passwordField = driver.findElement(By.cssSelector("input[placeholder='password']"));
        WebElement firstNameField = driver.findElement(By.cssSelector("input[placeholder='first name']"));
        WebElement lastNameField = driver.findElement(By.cssSelector("input[placeholder='last name']"));
        WebElement signUpButton = driver.findElement(By.cssSelector("button[type='submit']"));

        emailField.sendKeys("test@example.com");
        passwordField.sendKeys("Pas@1");
        firstNameField.sendKeys("John");
        lastNameField.sendKeys("Doe");
        signUpButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".SignUpForm_error_text__vt1BO")));
        assertTrue(errorMessage.isDisplayed(), "Error message is not displayed for too short password.");
        assertTrue(errorMessage.getText().contains("Password requires 8 characters minimum"), "Error message does not contain expected text.");
    }
}
