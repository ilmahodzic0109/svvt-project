package tests;

import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EditorTest {
    private static WebDriver webDriver;
    private static String baseUrl;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "/Users/Ilma Hodžić/Desktop/chromedriver-win64/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        webDriver = new ChromeDriver(options);
        baseUrl = "https://www.w3schools.com/java/tryjava.asp?filename=demo_helloworld";
    }

    @AfterAll
    public static void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
    @Test
    public void testDefaultCodeLoaded() {
        webDriver.get(baseUrl);
        WebElement codeMirror = webDriver.findElement(By.cssSelector(".CodeMirror"));
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        String editorContent = (String) js.executeScript("return arguments[0].CodeMirror.getValue();", codeMirror);

        assertEquals("public class Main {\n  public static void main(String[] args) {\n    System.out.println(\"Hello World\");\n  }\n}\n",
                editorContent);
    }
    @Test
    public void testJavaCodeExecution() throws InterruptedException {
        webDriver.get(baseUrl);
        WebElement runButton = webDriver.findElement(By.id("runbtn"));
        runButton.click();
        Thread.sleep(10000);
        webDriver.switchTo().frame("iframeResult");
        WebElement output= webDriver.findElement(By.xpath("/html/body/pre"));
        assertEquals("Hello World", output.getText());
    }
    @Test
    public void testModifyCodeMirrorContent() throws InterruptedException {
        webDriver.get(baseUrl);
        WebElement codeMirror = webDriver.findElement(By.cssSelector(".CodeMirror"));
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript(
                "arguments[0].CodeMirror.setValue('public class Main {\\n  public static void main(String[] args) {\\n    System.out.println(\\\"Custom Output\\\");\\n  }\\n}')",
                codeMirror
        );
        String updatedContent = (String) js.executeScript(
                "return arguments[0].CodeMirror.getValue();",
                codeMirror
        );
        WebElement runButton = webDriver.findElement(By.id("runbtn"));
        runButton.click();
        Thread.sleep(10000);
        webDriver.switchTo().frame("iframeResult");
        WebElement output= webDriver.findElement(By.xpath("/html/body/pre"));
        assertEquals("Custom Output", output.getText());

    }
    @Test
    public void testEmptyCodeExecution() throws InterruptedException {
        webDriver.get(baseUrl);
        WebElement codeMirror = webDriver.findElement(By.cssSelector(".CodeMirror"));
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("arguments[0].CodeMirror.setValue('')", codeMirror);
        WebElement runButton = webDriver.findElement(By.id("runbtn"));
        runButton.click();
        Thread.sleep(10000);
        webDriver.switchTo().frame("iframeResult");
        String output= webDriver.findElement(By.xpath("/html/body/pre")).getText();

        assertTrue(output.contains("Error: class not found: Main.java"),
                "Expected output to contain the error message, but found: " + output);
    }
}
