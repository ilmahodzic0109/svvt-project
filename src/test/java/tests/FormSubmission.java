package tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class FormSubmission {
    private static WebDriver webDriver;
    private static String baseUrl;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "/Users/Ilma Hodžić/Desktop/chromedriver-win64/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        webDriver = new ChromeDriver(options);
        baseUrl = "https://order.w3schools.com/academy/self-service/";
    }

    @AfterAll
    public static void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }

    @Test
    public void testFormSubmission() throws InterruptedException {
        webDriver.get(baseUrl);

        fillUserDetails("Test User", "testuser@example.com", "Bosnia and Herzegovina", "Test Organization");
        selectProductAndDetails("15", "4");
        verifySummary("Test User", "Test Organization", "testuser@example.com", "15", "4 months");
        submitForm();
    }

    private void fillUserDetails(String name, String email, String country, String organization) throws InterruptedException {
        webDriver.findElement(By.id("userName")).sendKeys(name);
        Thread.sleep(1000);

        webDriver.findElement(By.id("email")).sendKeys(email);
        Thread.sleep(1000);

        Select countrySelect = new Select(webDriver.findElement(By.id("country")));
        countrySelect.selectByVisibleText(country);
        Thread.sleep(1000);

        webDriver.findElement(By.id("organizationName")).sendKeys(organization);
        Thread.sleep(1000);

        webDriver.findElement(By.id("self-service-to-step-2-btn")).click();
        Thread.sleep(1000);
    }

    private void selectProductAndDetails(String students, String months) throws InterruptedException {
        webDriver.findElement(By.xpath("//*[@id='product-selection-form']/div[1]/div/div[2]/div/div/div")).click();
        Thread.sleep(2000);

        WebElement numberOfStudents = webDriver.findElement(By.xpath("//*[@id='nrOfStudents']"));
        numberOfStudents.click();
        numberOfStudents.sendKeys(Keys.CONTROL + "a");
        numberOfStudents.sendKeys(Keys.BACK_SPACE);
        numberOfStudents.sendKeys("15");
        Thread.sleep(1000);

        WebElement periodInMonths = webDriver.findElement(By.xpath("//*[@id='periodInMonths']"));
        periodInMonths.click();
        periodInMonths.sendKeys(Keys.CONTROL + "a");
        periodInMonths.sendKeys(Keys.BACK_SPACE);
        periodInMonths.sendKeys("4");
        Thread.sleep(1000);

        webDriver.findElement(By.xpath("//*[@id='self-service-to-step-3-btn']")).click();
        Thread.sleep(3000);
    }

    private void verifySummary(String name, String organization, String email, String students, String period) {
        Assertions.assertTrue(webDriver.findElement(By.xpath("//*[@id=\"summary-userName\"]"))
                .getText().contains(name), "Name does not match");
        Assertions.assertTrue(webDriver.findElement(By.xpath("//*[@id=\"summary-organizationName\"]"))
                .getText().contains(organization), "Organization does not match");
        Assertions.assertTrue(webDriver.findElement(By.xpath("//*[@id=\"summary-email\"]"))
                .getText().contains(email), "Email does not match");
        Assertions.assertTrue(webDriver.findElement(By.xpath("//*[@id=\"summary-nrOfStudents\"]"))
                .getText().contains(students), "Number of students does not match");
        Assertions.assertTrue(webDriver.findElement(By.xpath("//*[@id=\"summary-and-agreement-form\"]/div/div[6]/div[2]"))
                .getText().contains(period), "Period does not match");
    }

    private void submitForm() throws InterruptedException {
        webDriver.findElement(By.xpath("//*[@id='self-service-submit-btn']")).click();
        Thread.sleep(10000);
    }
    @Test
    public void testFormSubmissionWithEmptyFields() throws InterruptedException {
        webDriver.get(baseUrl);
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("self-service-to-step-2-btn")));
        nextButton.click();
        Thread.sleep(1000);

        WebElement nameError = webDriver.findElement(By.xpath("//*[@id='basic-info-form']/div[4]"));
        Assertions.assertTrue(nameError.getText().contains("Field \"Name\" is required"), "Name field error message is not displayed");
    }
    @Test
    public void testFormSubmissionWithInvalidEmail() throws InterruptedException {
        webDriver.get(baseUrl);
        fillUserDetails("Test User", "invalidemail", "Bosnia and Herzegovina", "Test Organization");
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("self-service-to-step-2-btn")));
        nextButton.click();
        Thread.sleep(1000);
        WebElement emailError = webDriver.findElement(By.xpath("//*[@id='basic-info-form']/div[4]"));
        Assertions.assertTrue(emailError.getText().contains("Field \"Email\" must be a valid email"),
                "Email format error message is not displayed");
    }
    @Test
    public void testFormSubmissionWithInvalidName() throws InterruptedException {
        webDriver.get(baseUrl);
        fillUserDetails("Test", "test@gmail.com", "Bosnia and Herzegovina", "Test Organization");
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("self-service-to-step-2-btn")));
        nextButton.click();
        Thread.sleep(1000);
        WebElement emailError = webDriver.findElement(By.xpath("//*[@id='basic-info-form']/div[4]"));
        Assertions.assertTrue(emailError.getText().contains("Field \"Name\" should contain both given name and family name"),
                "Name format error message is not displayed");
    }
    @Test
    public void testFormSubmissionWithInvalidCountry() throws InterruptedException {
        webDriver.get(baseUrl);
        fillUserDetails("Test User", "test@gmail.com", "Bosnia and Herzegovina", "");
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("self-service-to-step-2-btn")));
        nextButton.click();
        Thread.sleep(1000);
        WebElement emailError = webDriver.findElement(By.xpath("//*[@id='basic-info-form']/div[4]"));
        Assertions.assertTrue(emailError.getText().contains("Field \"Organization\" is required"),
                "Email format error message is not displayed");
    }
}
