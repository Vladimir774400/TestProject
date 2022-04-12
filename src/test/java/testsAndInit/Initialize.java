package testsAndInit;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.chrome.ChromeDriver;

public class Initialize {
    protected ChromeDriver driver;

    @Before
    public void setUp() {
        //ѕуть до chromeDriver
        System.setProperty("webdriver.chrome.driver", "C:\\cygwin64\\home\\vnartov\\testProject\\src\\libs\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://www.google.com/");
        driver.manage().window().maximize();
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
