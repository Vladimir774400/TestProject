package tests;

import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import pageObjects.MainPageObject;

import java.util.concurrent.TimeUnit;

@DisplayName("Google tests")
public class FirstTest {
    private static ChromeDriver driver;
    private static final String mainUrl = "https://www.google.com/";

    @BeforeAll
    @Step("Initializing chromeDriver")
    public static void setUp() {
        System.out.println("- Initializing");
        //init chromeDriver
        System.setProperty("webdriver.chrome.driver", "src/libs/chromedriver");
        driver = new ChromeDriver();
        driver.get(mainUrl);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    }

    @Test
    @DisplayName("Google search")
    public void firstTest() throws InterruptedException {
        MainPageObject mainPageObject = new MainPageObject(driver);
        Thread.sleep(3_000);
        mainPageObject.searchInGoogle("Java",20);
        mainPageObject.clickOnSearchResult(2);
    }

    @AfterAll
    @Step("Tear down")
    public static void tearDown() {
        System.out.println("- Tear down");
        driver.quit();
    }
}
