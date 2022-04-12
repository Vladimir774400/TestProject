package pageObjects;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import pageObjects.MainPageObject;

import java.util.List;

public class SearchPageObject extends MainPageObject {
    protected ChromeDriver driver;

    public SearchPageObject(ChromeDriver driver) {
        super(driver);
        this.driver = driver;
    }

    //��������
    private static final String
            SEARCH_FIELD = "/html/body/div[1]/div[3]/form/div[1]/div[1]/div[1]/div/div[2]/input",
            SEARCH_BTN = "/html/body/div[1]/div[3]/form/div[1]/div[1]/div[2]/div[2]/div[5]/center/input[1]",
            RESULTS_PAGE = "//*[@id='result-stats']",
            PICTURES_DIV = "//*[@id='islrg']",
            APP_STORE_RATING = "/html/body/div[5]/main/div[2]/section[1]/div/div[2]/header/ul[1]/li[2]/ul/li/figure/figcaption",
            WIKI_CONTENT_CONTAINER = "mw-content-text";

    //������
    //����� ������ ����� google
    public void searchInGoogle(String text) {
        waitForElementAndTypeText(text, By.xpath(SEARCH_FIELD), "���� ����� �� �������", 10);
        waitForElementPresentAndClick(By.xpath(SEARCH_BTN), "������ '�����' �� �������", 10);
        waitForElementPresent(By.xpath(RESULTS_PAGE), "���������� �� ������� '" + text + "' �� �������", 10);
    }

    //����� ��������� � ��������� "��������"
    public void goToPicturesTab() {
        waitElementContainsTextAndClick("��������");
        waitForElementPresent(By.xpath(PICTURES_DIV), "���� �������� �� ������������", 10);
    }

    //����� �������� ������ ������� ��������
    public void selectBigPics(String size) {
        waitElementContainsTextAndClick("�����������");
        waitElementContainsTextAndClick("������");
        waitElementContainsTextAndClick(size);
        waitForElementPresent(By.xpath(PICTURES_DIV), "���� �������� �� ������������", 10);
    }

    //����� ��������� ��� �� ����� 3� �������� ����� �� ���� ivi.ru
    public void asserThatThreePicsContainsUrl() {
        String url = "https://www.ivi.ru";
        List<WebElement> elemsWithIvi = findAllElementsWithUrlStartsWith(url);
        System.out.println("������� " + elemsWithIvi.size() + " ��������(��) �� ������� '" + url + "'");
        Assert.assertTrue("��������� ������� �� ����� ivi ������ ����", elemsWithIvi.size() > 3);
    }

    //����� ���� ������ �� appStore �� ������ ���� ��������� � ���������� �������
    public void searchAppStoreAndCheckRate() {
        // ���� ������������ �������
        for (int i = 2; i <= 5; i++) {
            // ������� �������� � appStore
            String ratingFromAppStore;
            // ������� �������� ������� ������������ � ������
            String ratingFromSearchPage;
            // ������� ���� ������� ����� ������ �� ���������
            String url = "https://apps.apple.com/ru/app/ivi";
            //� ������� ������ findAllElementsWithUrlStartsWith ������� ��� �������� �� ������� �� ivi � �������� � List
            List<WebElement> elemsWithUrl = findAllElementsWithUrlStartsWith(url);
            // ������ ��� ���������� ������� ������� ������ �� ������ �� 5 �������
            System.out.println("������� " + elemsWithUrl.size() + " ��������(��) �� ������� '" + url + "' �� �������� '" + i + "'");
            // ���� ����� ���� �� �������� ������ � ���� ��� ������� �������� �����
            if (elemsWithUrl.size() > 0) {
                for (WebElement element : elemsWithUrl) {
                    // ����������� ���� �� DOM ���� ���� ������� �������� ������� � �������� ����� �� ������� � ��������� ��� �������� � ratingFromSearchPage
                    ratingFromSearchPage = elemsWithUrl
                            .get(0)
                            .findElement(By.xpath("..//..//..//span[contains(text(), '�������')]"))
                            .getText()
                            .replace("�������: ", "");
                    // ������� �� ������
                    element.click();
                    // ���� ���� ����������� ������� � ��������� � appStore
                    waitForElementPresent(By.xpath(APP_STORE_RATING), "�� ������ ������� �������� � appStore", 10);
                    // �� ��� ����� ���� � ����, ����� �������� ���� ������� ����� ���������, ������ �������� ������ �� 3 � ����� 0 �������
                    String[] arr = driver.findElement(By.xpath(APP_STORE_RATING)).getText().replaceAll("(.{3})", "$1 ").split(" ");
                    // ���������� ��� �������� � ratingFromAppStore
                    ratingFromAppStore = arr[0];
                    // ����������� ratingFromAppStore � ratingFromSearchPage
                    Assert.assertTrue("�������� � appStore: '" + ratingFromAppStore + "' ������� �� �������� ������ '" + ratingFromSearchPage + "' �� ���������!"
                            , ratingFromAppStore.equals(ratingFromSearchPage));
                    // ������� �����
                    driver.navigate().back();
                }
            }
            // ���� �� ���� �������� � ������ ��� ��� �����
            goToPage(i);
        }
    }

    //����� ���� ������ �� appStore �� ������ ���� ��������� � ���������� �������
    public void searchWikiPageAboutIvi() {
        // ���� ������������ �������
        for (int i = 2; i <= 5; i++) {
            String url = "https://ru.wikipedia.org/wiki/Ivi.ru";
            //� ������� ������ findAllElementsWithUrlStartsWith ������� ��� �������� �� ������� �� �������� wiki � �������� � List
            List<WebElement> elemsWithUrl = findAllElementsWithUrlStartsWith(url);
            // ������ ��� ���������� ������� ������� ������ �� ������ �� 5 �������
            System.out.println("������� " + elemsWithUrl.size() + " ��������(��) �� ������� '" + url + "' �� �������� '" + i + "'");
            if (elemsWithUrl.size() > 0) {
                for (WebElement element : elemsWithUrl) {
                    try {
                        element.click();
                    } catch (org.openqa.selenium.ElementClickInterceptedException e) {
                        // � ������ ���� ������� ��������� ������ (��� ��� ��������� � ����), JS ������ �������� ������� �� ��������
                        String scrollElementIntoMiddle = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
                                + "var elementTop = arguments[0].getBoundingClientRect().top;"
                                + "window.scrollBy(0, elementTop-(viewPortHeight/2));";

                        ((JavascriptExecutor) driver).executeScript(scrollElementIntoMiddle, element);
                        element.click();
                    }
                    waitForElementPresent(By.id(WIKI_CONTENT_CONTAINER), "������� ������ �� ������������", 10);
                    List<WebElement> elemsWithIviUrl = findAllElementsWithUrlStartsWith("https://www.ivi.ru");
                    Assert.assertTrue("�� �������� wiki �� ������� ������ �� ivi", elemsWithIviUrl.size() > 0);
                    driver.navigate().back();
                }
            }
            goToPage(i);
        }
    }
}
