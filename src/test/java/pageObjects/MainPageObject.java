package pageObjects;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainPageObject {
    protected ChromeDriver driver;

    public MainPageObject(ChromeDriver driver) {
        this.driver = driver;
    }

    public static final String
            ELEMENT_CONTAINS_TEXT = "//*[contains(text(), '{SUBSTRING}')]",
            URL_STARTS_WITH = "a[href^='{SUBSTRING}']",
            SEARCH_WITH_PARAM = "[{PARAM}='{VALUE}']",
            SEARCH_PAGES = "[aria-label='Page {PAGE_NUM}']",
            FIELDS_WITH_ERROR = "[class='invalid-feedback']",
            GOOGLE_SEARCH_FIELD = "[class='gLFyf']",
            GOOGLE_SEARCH_BUTTON = "[class='gNO89b']",
            GOOGLE_SEARCH_TITLES = "[class='LC20lb MBeuO DKV0Md']",
            GOOGLE_SEARCH_PAGES = "[role='navigation']",
            AMAZON_DESC = "[id='feature-bullets']";


    @Step("POST - {0}")
    public Response sendPostRequest(String url, String body, int expectedStatusCode) {
        Response resp = given()
                .contentType(ContentType.JSON)
                .body(body)
                .post(url);

        assertEquals(String.valueOf(expectedStatusCode), String.valueOf(resp.getStatusCode()), "Unexpected status code " + resp.statusCode() + ". Response was " + resp.body().asString());
        return resp;
    }

    @Step("GET - {0}")
    public Response sendGetRequest(String url, int expectedStatusCode) {
        Response resp = given()
                .contentType(ContentType.JSON)
                .get(url);

        assertEquals(String.valueOf(expectedStatusCode), String.valueOf(resp.getStatusCode()), "Unexpected status code " + resp.statusCode() + ". Response was " + resp.body().asString());
        return resp;
    }

    @Step("Open URL {0}")
    public void openCustomPage(String url) {
        driver.get(url);
        driver.manage().window().maximize();
    }

    @Step("Wait for element {0} present")
    public WebElement waitForElementPresent(By by, String err_mes, long timeoutInSec) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSec);
        wait.withMessage(err_mes + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    @Step("sout element content")
    public void soutElementText(By by) {
        WebElement element = driver.findElement(by);
        System.out.println(element.getText());
    }

    @Step("Wait for element {0} not present")
    public boolean waitForElementNotPresent(By by, String err_mes, long timeoutInSec) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSec);
        wait.withMessage(err_mes + "\n");

        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    @Step("Wait for element {1} and type text {0}")
    public void waitForElementAndTypeText(String text, By by, String err_mes, long timeoutInSec) {
        WebElement field = waitForElementPresent(by, err_mes, timeoutInSec);
        field.click();
        field.clear();
        field.sendKeys(text);
    }

    @Step("Wait for element {0} and click")
    public void waitForElementPresentAndClick(By by, String err_mes, long timeoutInSec) {
        waitForElementPresent(by, err_mes, timeoutInSec).click();
    }

    @Step("Click on {0} Google search result")
    public void clickOnSearchResult(int num) throws InterruptedException {
        List<WebElement> titlesWebEl = driver.findElements(By.cssSelector(GOOGLE_SEARCH_TITLES));
        titlesWebEl.get(num).click();
        Thread.sleep(5_000);
    }

    @Step("Search in Google method")
    public void searchInGoogle(String text, long timeoutInSec) {
        openCustomPage("https://www.google.com/");
        waitForElementAndTypeText(text, By.cssSelector(GOOGLE_SEARCH_FIELD),"Google search field not found",timeoutInSec);
        waitForElementPresentAndClick(By.cssSelector(GOOGLE_SEARCH_BUTTON),"Google search bttn not found",timeoutInSec);
        List<WebElement> titlesWebEl = driver.findElements(By.cssSelector(GOOGLE_SEARCH_TITLES));
        List<String> titles = new ArrayList<>();
        titlesWebEl.stream().forEach(title -> titles.add(title.getAttribute("innerText")));
        assertTrue(titles.stream().anyMatch(title -> title.contains(text)), "There is no relevant search results");
    }

    @Step("Wait for element contains text {0} and click")
    public void waitElementContainsTextAndClick(String text) {
        String searchedText = ELEMENT_CONTAINS_TEXT.replace("{SUBSTRING}", text);
        WebElement element = waitForElementPresent(By.xpath(searchedText), "No element with text " + text, 15);
        element.click();
    }

    @Step("Wait for element contains text {0}")
    public void waitElementContainsText(String text) {
        String searchedText = ELEMENT_CONTAINS_TEXT.replace("{SUBSTRING}", text);
        WebElement element = waitForElementPresent(By.xpath(searchedText), "No element with text " + text, 15);
    }

    @Step("Wait for element contains text {0}, boolean")
    public boolean waitElementContainsTextAssertion(String text) {
        String searchedText = ELEMENT_CONTAINS_TEXT.replace("{SUBSTRING}", text);
        WebElement element = waitForElementPresent(By.xpath(searchedText), "No element with text " + text, 15);
        return element.isDisplayed();
    }

    @Step("Search element with param {0} which equals {1}")
    public void searchWithParam(String param, String value) {
        String loc = SEARCH_WITH_PARAM.replace("{PARAM}", param).replace("{VALUE}", value);
        WebElement element = waitForElementPresent(By.cssSelector(loc), "No element with '" + param + "' and value '" + value + "'", 15);
    }

    @Step("Wait for element with class equals {0}")
    public void waitElementByClass(String class_name) {
        WebElement element = waitForElementPresent(By.cssSelector(class_name), "No element with class " + class_name, 15);
    }

    @Step("Return element contains text {0}")
    public WebElement returnElementContainsText(String text) {
        String searchedText = ELEMENT_CONTAINS_TEXT.replace("{SUBSTRING}", text);
        return waitForElementPresent(By.xpath(searchedText), "No element with text " + text, 15);
    }

    @Step("Return all elements with URL starts with {0}")
    public List<WebElement> findAllElementsWithUrlStartsWith(String text) {
        String searchedUrl = URL_STARTS_WITH.replace("{SUBSTRING}", text);
        return driver.findElements(By.cssSelector(searchedUrl));
    }

    @Step("Scroll to some element {0}")
    public void scrollToSomeElementByCss(String by) {
        WebElement pages = driver.findElement(By.cssSelector(by));
        int maxCount = 40;
        Actions actions = new Actions(driver);

        for (int i =0;i<maxCount;i++) {
            if (pages.isDisplayed()) {
                actions.sendKeys(Keys.PAGE_DOWN);
                actions.perform();
            } else {
                System.out.println("Element found after "+i+" scrols down");
                break;
            }
        }
    }

    @Step("Scroll {0} times")
    public void scrollCustomTimes(int times) {
        Actions actions = new Actions(driver);
        for (int i =0;i<times;i++) {
                actions.sendKeys(Keys.PAGE_DOWN);
                actions.perform();
        }
    }

    @Step("Is word {0} polyndrome")
    public void isPoly(String word) {
        for (int i=0;i<word.length();i++) {
            assertEquals(word.charAt(i), word.charAt(word.length() - 1 - i), "Word is not polyndrome: letter "+word.charAt(i)+" not equals " +word.charAt(word.length() - 1 - i));
        }
    }

    @Step("Is word {0} contains only letters")
    public void isLettersOnly(String word) {
        assertEquals(word.replaceAll("\\d",""),word,"Word contains digits");
    }
}
