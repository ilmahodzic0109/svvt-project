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

public class QuizExerciseTest {
    private static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "/Users/HP/chromedriver-win64/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testQuizExerciseCorrectAnswer() {
        driver.get("https://www.w3schools.com/java/exercise.asp?x=xrcise_output1");

        WebElement correctOption = driver.findElement(By.cssSelector("input#quizoption0"));
        correctOption.click();

        WebElement submitButton = driver.findElement(By.id("answerbutton"));
        submitButton.click();

        WebElement correctFeedback = driver.findElement(By.cssSelector(".correctanswer"));
        assertTrue(correctFeedback.isDisplayed(), "Correct feedback is not displayed.");
        assertTrue(correctFeedback.getText().contains("Correct Answer!"), "Correct feedback text is incorrect.");
    }

    @Test
    public void testQuizExerciseWrongAnswer() {
        driver.get("https://www.w3schools.com/java/exercise.asp?x=xrcise_output1");

        WebElement wrongOption = driver.findElement(By.cssSelector("input#quizoption1"));
        wrongOption.click();

        WebElement submitButton = driver.findElement(By.id("answerbutton"));
        submitButton.click();

        WebElement wrongFeedback = driver.findElement(By.cssSelector(".wronganswer"));
        assertTrue(wrongFeedback.isDisplayed(), "Wrong feedback is not displayed.");
        assertTrue(wrongFeedback.getText().contains("Wrong Answer!"), "Wrong feedback text is incorrect.");
    }

    @Test
    public void testNoOptionSelected() {
        driver.get("https://www.w3schools.com/java/exercise.asp?x=xrcise_output1");

        WebElement submitButton = driver.findElement(By.id("answerbutton"));
        submitButton.click();

        WebElement errorFeedback = driver.findElement(By.cssSelector(".wronganswer"));
        assertTrue(errorFeedback.isDisplayed(), "No feedback is displayed when no option is selected.");
    }

}

