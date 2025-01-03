package tests;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegistrationTest {
    private static WebDriver webDriver;
    private static String baseUrl;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "/Users/Ilma Hodžić/Desktop/chromedriver-win64/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        webDriver = new ChromeDriver(options);
        baseUrl = "https://profile.w3schools.com/signup?redirect_url=https%3A%2F%2Fwww.w3schools.com%2F";
    }

    @AfterAll
    public static void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
    @Test
    public void testRegistrationFormValid() throws InterruptedException {
        webDriver.get(baseUrl);
        webDriver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div/div[1]/div/div[2]/div/div[5]/div/form/div[1]/input[1]")).sendKeys("test10@stu.ibu.edu.ba");
        sleep(2000);
        webDriver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div/div[1]/div/div[2]/div/div[5]/div/form/div[1]/input[2]")).sendKeys("$u2TRtvvCP22SA");
        sleep(2000);
        webDriver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div/div[1]/div/div[2]/div/div[5]/div/form/div[1]/input[3]")).sendKeys("Ilma");
        sleep(2000);
        webDriver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div/div[1]/div/div[2]/div/div[5]/div/form/div[1]/input[4]")).sendKeys("Hodzic");
        sleep(2000);
        webDriver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div/div[1]/div/div[2]/div/div[5]/div/form/div[3]/button")).click();
        sleep(10000);
        WebElement verifyEmailMessage = webDriver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div/div[1]/div/div[2]/div/div[3]/div/div/div/div[1]"));
        sleep(2000);
        boolean isMessageDisplayed = verifyEmailMessage.isDisplayed();
        String messageText = verifyEmailMessage.getText();

        assertTrue(isMessageDisplayed, "The 'Please verify your email' message is not displayed.");
        assertTrue(messageText.contains("Please verify your email"), "The displayed message does not contain the expected text.");
    }
    @Test
    public void testRegistrationFormInvalid() throws InterruptedException {
        webDriver.get(baseUrl);
        webDriver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div/div[1]/div/div[2]/div/div[5]/div/form/div[1]/input[1]")).sendKeys("test@stu.ibu.edu.ba");
        sleep(2000);
        webDriver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div/div[1]/div/div[2]/div/div[5]/div/form/div[1]/input[2]")).sendKeys("$u2TRtvvCP22SA");
        sleep(2000);
        webDriver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div/div[1]/div/div[2]/div/div[5]/div/form/div[3]/button")).click();
        sleep(2000);
        WebElement errorMessage = webDriver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div/div[1]/div/div[2]/div/div[5]/div/form/div[2]"));
        sleep(2000);

        boolean isMessageDisplayed = errorMessage.isDisplayed();
        String messageText = errorMessage.getText();

        assertTrue(isMessageDisplayed, "The 'Please fill in all fields' message is not displayed.");
        assertTrue(messageText.contains("Please fill in all fields"), "The displayed message does not contain the expected text.");
    }

    @Test
    public void testRegistrationFormExistingUser() throws InterruptedException {
        webDriver.get(baseUrl);
        webDriver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div/div[1]/div/div[2]/div/div[5]/div/form/div[1]/input[1]")).sendKeys("ilmahodzic7@gmail.com");
        sleep(2000);
        webDriver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div/div[1]/div/div[2]/div/div[5]/div/form/div[1]/input[2]")).sendKeys("$u2TRtvvCP22SA");
        sleep(2000);
        webDriver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div/div[1]/div/div[2]/div/div[5]/div/form/div[1]/input[3]")).sendKeys("Ilma");
        sleep(2000);
        webDriver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div/div[1]/div/div[2]/div/div[5]/div/form/div[1]/input[4]")).sendKeys("Hodzic");
        sleep(2000);
        webDriver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div/div[1]/div/div[2]/div/div[5]/div/form/div[3]/button")).click();
        sleep(10000);
        WebElement verifyEmailMessage = webDriver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div/div[1]/div/div[2]/div/div[5]/div/form/div[2]"));
        sleep(2000);
        boolean isMessageDisplayed = verifyEmailMessage.isDisplayed();
        String messageText = verifyEmailMessage.getText();

        assertTrue(isMessageDisplayed, "The 'Looks like you already have a user. Did you try logging in?' message is not displayed.");
        assertTrue(messageText.contains("Looks like you already have a user. Did you try logging in?"), "The displayed message does not contain the expected text.");
    }
    @Test
    public void testRegistrationFormInvalidEmailFormat() throws InterruptedException {
        webDriver.get(baseUrl);
        webDriver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div/div[1]/div/div[2]/div/div[5]/div/form/div[1]/input[1]")).sendKeys("invalidEmailFormat");
        sleep(2000);
        webDriver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div/div[1]/div/div[2]/div/div[5]/div/form/div[1]/input[2]")).sendKeys("$u2TRtvvCP22SA");
        sleep(2000);
        webDriver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div/div[1]/div/div[2]/div/div[5]/div/form/div[1]/input[3]")).sendKeys("Ilma");
        sleep(2000);
        webDriver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div/div[1]/div/div[2]/div/div[5]/div/form/div[1]/input[4]")).sendKeys("Hodzic");
        sleep(2000);
        webDriver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div/div[1]/div/div[2]/div/div[5]/div/form/div[3]/button")).click();
        sleep(2000);
        WebElement errorMessage = webDriver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div/div[1]/div/div[2]/div/div[5]/div/form/div[2]"));
        sleep(2000);

        boolean isMessageDisplayed = errorMessage.isDisplayed();
        String messageText = errorMessage.getText();

        assertTrue(isMessageDisplayed, "The 'Please enter a valid email address' message is not displayed.");
        assertTrue(messageText.contains("Please enter a valid email address"), "The displayed message does not contain the expected text.");
    }
    @Test
    public void testRegistrationFormInvalidPasswordFormat() throws InterruptedException {
        webDriver.get(baseUrl);
        webDriver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div/div[1]/div/div[2]/div/div[5]/div/form/div[1]/input[1]")).sendKeys("test@gmail.com");
        sleep(2000);
        webDriver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div/div[1]/div/div[2]/div/div[5]/div/form/div[1]/input[2]")).sendKeys("test");
        sleep(2000);
        webDriver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div/div[1]/div/div[2]/div/div[5]/div/form/div[1]/input[3]")).sendKeys("Ilma");
        sleep(2000);
        webDriver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div/div[1]/div/div[2]/div/div[5]/div/form/div[1]/input[4]")).sendKeys("Hodzic");
        sleep(2000);
        webDriver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div/div[1]/div/div[2]/div/div[5]/div/form/div[3]/button")).click();
        sleep(2000);
        WebElement errorMessage = webDriver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div/div[1]/div/div[2]/div/div[5]/div/form/div[2]"));
        sleep(2000);

        boolean isMessageDisplayed = errorMessage.isDisplayed();
        String messageText = errorMessage.getText();

        assertTrue(isMessageDisplayed, "The 'Password requires at least one uppercase character' message is not displayed.");
        assertTrue(messageText.contains("Password requires at least one uppercase character"), "The displayed message does not contain the expected text.");
    }
}
