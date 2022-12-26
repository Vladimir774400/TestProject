package testsAndInit;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class Initialize {
    protected ChromeDriver driver;
    static String mainUrl = "https://www.google.com/";

    @Before
    public void setUp() {
        System.out.println("- Initializing");
        //init chromeDriver
        System.setProperty("webdriver.chrome.driver", "src/libs/chromedriver");
        driver = new ChromeDriver();
        driver.get(mainUrl);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);

    }

    @After
    public void tearDown() {
        System.out.println("- Tear down");
        driver.quit();
    }
}
