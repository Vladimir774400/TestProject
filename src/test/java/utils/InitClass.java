package utils;

import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterAll;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class InitClass {
    private static ChromeDriver driver;
    private static final String mainUrl = "https://www.google.com/";

    @Step("Initializing and return chromeDriver")
    public static ChromeDriver setUpChromeDriver() {
        //init chromeDriver
        System.setProperty("webdriver.chrome.driver", "src/libs/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }

    @Step("Open mainUrl")
    public static void openMainUrl() {
        driver.get(mainUrl);
        driver.manage().window().maximize();
    }

    @AfterAll
    @Step("Tear down")
    public static void tearDown() {
        driver.quit();
    }
}
