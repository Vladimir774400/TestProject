package testsAndInit;

import org.junit.After;
import org.junit.Before;
import org.junit.rules.Timeout;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class Initialize {
    protected ChromeDriver driver;

    @Before
    public void setUp() {
        System.out.println("- Начинаю тест");
        //Путь до chromeDriver
        System.setProperty("webdriver.chrome.driver", "C:\\cygwin64\\home\\vnartov\\testProject\\src\\libs\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://qaautomation-test.s3-eu-west-1.amazonaws.com/index.html");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);

    }

    @After
    public void tearDown() {
        System.out.println("- Завершение теста");
        driver.quit();
    }
}
