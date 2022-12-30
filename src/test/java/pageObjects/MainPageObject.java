package pageObjects;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
            CART_LOADING = "[id='loading']",
            TOTAL_AMOUNT = "[id='totalAmount']",
            PROMO_CODE_FIELD = "[id='promoCode']",
            FIRST_NAME_FIELD = "[name='firstName']",
            LAST_NAME_FIELD = "[name='lastName']",
            CART_NAME_FIELD = "[name='cc-name']",
            CART_NUM_FIELD = "[name='cc-number']",
            CART_EXP_FIELD = "[name='cc-expiration']",
            CART_CCV_FIELD = "[name='cc-cvv']",
            SUCCESS_MESS = "[id='success']";


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
}
