package tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PerformanceAndLoadTest {
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
    public void testNormalLoadTime() {
        long startTime = System.currentTimeMillis();
        webDriver.get(baseUrl);
        long endTime = System.currentTimeMillis();
        long loadTime = endTime - startTime;

        System.out.println("Page load time: " + loadTime + " ms");
        assertTrue(loadTime <= 3000, "The page load time should be under 3 seconds.");
    }

    @Test
    public void testSimultaneousUsers() {
        int userCount = 5;
        ExecutorService executor = Executors.newFixedThreadPool(userCount);

        for (int i = 0; i < userCount; i++) {
            executor.execute(() -> {
                WebDriver driver = new ChromeDriver();
                try {
                    driver.get("https://www.w3schools.com");
                } finally {
                    driver.quit();
                }
            });
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
        }

        System.out.println("Load test with 100 users completed.");
    }
    @Test
    public void testNormalLoadTimeNegative() {
        long startTime = System.currentTimeMillis();
        webDriver.get(baseUrl);
        long endTime = System.currentTimeMillis();
        long loadTime = endTime - startTime;

        System.out.println("Page load time: " + loadTime + " ms");
        assertTrue(loadTime > 500, "The page load time should exceed 500");
    }

}
