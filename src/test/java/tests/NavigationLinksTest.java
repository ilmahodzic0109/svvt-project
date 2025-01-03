package tests;

import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class NavigationLinksTest {
    private static WebDriver webDriver;
    private static String baseUrl;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "/Users/Ilma Hodžić/Desktop/chromedriver-win64/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        webDriver = new ChromeDriver(options);
        baseUrl = "https://www.w3schools.com/";
    }

    @AfterAll
    public static void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
    @Test
    public void testSequentialNavigationLinks() {
        webDriver.get(baseUrl);
        String[][] navigationSteps = {
                {"//*[@id=\"subtopnav\"]/a[1]", "HTML Tutorial"},
                {"//*[@id=\"subtopnav\"]/a[2]", "CSS Tutorial"},
                {"//*[@id=\"subtopnav\"]/a[3]", "JavaScript Tutorial"},
                {"//*[@id=\"subtopnav\"]/a[4]", "SQL Tutorial"},
                {"//*[@id=\"subtopnav\"]/a[5]", "Python Tutorial"},
                {"//*[@id=\"subtopnav\"]/a[6]", "Java Tutorial"},
                {"//*[@id=\"subtopnav\"]/a[7]", "PHP Tutorial"},
                {"//*[@id=\"subtopnav\"]/a[8]", "How TO"},
                {"//*[@id=\"subtopnav\"]/a[9]", "W3.CSS Home"},
                {"//*[@id=\"subtopnav\"]/a[10]", "C Tutorial"},
                {"//*[@id=\"subtopnav\"]/a[11]", "C++ Tutorial"},
                {"//*[@id=\"subtopnav\"]/a[12]", "C# Tutorial"},
                {"//*[@id=\"subtopnav\"]/a[13]", "Bootstrap"},

        };

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        for (String[] step : navigationSteps) {
            String linkXPath = step[0];
            String expectedTitle = step[1];

            WebElement navigationLink = webDriver.findElement(By.xpath(linkXPath));
            navigationLink.click();

            wait.until(ExpectedConditions.titleContains(expectedTitle));

            String pageTitle = webDriver.getTitle();
            assertTrue(pageTitle.contains(expectedTitle), "Expected title not found. Current title: " + pageTitle);
        }
    }
    @Test
    public void testScrollButtonFunctionality() {
        webDriver.get(baseUrl);

        WebDriverWait wait = new WebDriverWait(webDriver, java.time.Duration.ofSeconds(10));
        WebElement scrollButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"scroll_right_btn\"]/span")));

        JavascriptExecutor jsExecutor = (JavascriptExecutor) webDriver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", scrollButton);

        jsExecutor.executeScript("scrollmenow(1);");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testTeacherNavigation() throws InterruptedException {
        webDriver.get(baseUrl);

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(15));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='pagetop']/div[3]/a[7]")));
        button.click();
        wait.until(ExpectedConditions.urlToBe("https://www.w3schools.com/academy/teachers/index.php"));
        assertEquals(webDriver.getCurrentUrl(), "https://www.w3schools.com/academy/teachers/index.php");
        Thread.sleep(2000);
    }
    @Test
    public void testSpaceNavigation() throws InterruptedException {
        webDriver.get(baseUrl);

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(15));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"pagetop\"]/div[3]/a[6]")));
        button.click();
        wait.until(ExpectedConditions.urlToBe("https://www.w3schools.com/spaces/index.php"));
        assertEquals(webDriver.getCurrentUrl(), "https://www.w3schools.com/spaces/index.php");
        Thread.sleep(2000);
    }
    @Test
    public void testCampusNavigation() throws InterruptedException {
        webDriver.get(baseUrl);
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(15));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"pagetop\"]/div[3]/a[5]")));
        button.click();
        String originalWindow = webDriver.getWindowHandle();
        for (String windowHandle : webDriver.getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                webDriver.switchTo().window(windowHandle);
                break;
            }
        }
        wait.until(ExpectedConditions.urlToBe("https://campus.w3schools.com/collections/course-catalog"));
        assertEquals(webDriver.getCurrentUrl(), "https://campus.w3schools.com/collections/course-catalog");
        Thread.sleep(2000);
        webDriver.close();
        webDriver.switchTo().window(originalWindow);
    }

    @Test
    public void testForBrokenLinks() {
        webDriver.get(baseUrl);
        String[] linkXpaths = {
                "//*[@id=\"subtopnav\"]/a[1]", "//*[@id=\"subtopnav\"]/a[2]",
                "//*[@id=\"subtopnav\"]/a[3]", "//*[@id=\"subtopnav\"]/a[4]",
                "//*[@id=\"subtopnav\"]/a[5]", "//*[@id=\"subtopnav\"]/a[6]",
                "//*[@id=\"subtopnav\"]/a[7]", "//*[@id=\"subtopnav\"]/a[8]",
                "//*[@id=\"subtopnav\"]/a[9]", "//*[@id=\"subtopnav\"]/a[10]",
                "//*[@id=\"subtopnav\"]/a[11]", "//*[@id=\"subtopnav\"]/a[12]",
                "//*[@id=\"subtopnav\"]/a[13]"
        };

        for (String linkXPath : linkXpaths) {
            WebElement navigationLink = webDriver.findElement(By.xpath(linkXPath));
            navigationLink.click();

            String currentUrl = webDriver.getCurrentUrl();
            int statusCode = getHttpResponseCode(currentUrl);

            assertEquals(200, statusCode, "Broken link found: " + currentUrl);
        }
    }
    @Test
    public void testNegativeNavigation() {
        webDriver.get(baseUrl);
        String invalidXPath = "//*[@id=\"subtopnav\"]/a[9999]";
        try {
            WebElement invalidLink = webDriver.findElement(By.xpath(invalidXPath));
            invalidLink.click();
            fail("Expected NoSuchElementException for invalid link");
        } catch (Exception e) {
            assertInstanceOf(NoSuchElementException.class, e, "Expected NoSuchElementException but got: " + e.getClass().getSimpleName());
        }
    }
    private int getHttpResponseCode(String url) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());

            return response.statusCode();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}

