package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MainPageObject {
    protected ChromeDriver driver;
    public MainPageObject(ChromeDriver driver) {
        this.driver = driver;
    }

    //��������
    private static final String
            ELEMENT_CONTAINS_TEXT = "//*[contains(text(), '{SUBSTRING}')]",
            URL_STARTS_WITH = "a[href^='{SUBSTRING}']",
            SEARCH_PAGES = "[aria-label='Page {PAGE_NUM}']";

    //����� �������� ��������
    public WebElement waitForElementPresent(By by, String err_mes, long timeoutInSec) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSec);
        wait.withMessage(err_mes + "\n");

        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    //����� ����� � ��������� ������ google ������������� ������
    public void waitForElementAndTypeText(String text, By by, String err_mes, long timeoutInSec) {
        WebElement field = waitForElementPresent(by, err_mes, timeoutInSec);
        field.clear();
        field.sendKeys(text);
    }

    //����� ������� �� ������
    public void waitForElementPresentAndClick(By by, String err_mes, long timeoutInSec) {
        waitForElementPresent(by, err_mes, timeoutInSec).click();
    }

    //����� ������ � ����� �������� ����������� �����
    public void waitElementContainsTextAndClick(String text)
    {
        String searchedText = ELEMENT_CONTAINS_TEXT.replace("{SUBSTRING}",text);
        WebElement element = waitForElementPresent(By.xpath(searchedText), "�� ������� ��������� � ������� "+text,15);
        element.click();
    }

    //����� ������� ��� �������� c URL ������������ ��
    public List<WebElement> findAllElementsWithUrlStartsWith(String text)
    {
        String searchedUrl = URL_STARTS_WITH.replace("{SUBSTRING}",text);
        List <WebElement> elements = driver.findElements(By.cssSelector(searchedUrl));
        return elements;
    }

    //����� �������� �� ������������ �������� ������
    public void goToPage(Integer pageNum)
    {
        String num = pageNum.toString();
        String pageXpath = SEARCH_PAGES.replace("{PAGE_NUM}",num);
        WebElement element = waitForElementPresent(By.cssSelector(pageXpath), "�� ������� ������ �������� '"+pageNum+"'",15);
        element.click();

    }

}
