package pageObjects;

import io.restassured.RestAssured;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class MainPageObject {
    protected ChromeDriver driver;

    public MainPageObject(ChromeDriver driver) {
        this.driver = driver;
    }

    //Локаторы
    private static final String
            ELEMENT_CONTAINS_TEXT = "//*[contains(text(), '{SUBSTRING}')]",
            URL_STARTS_WITH = "a[href^='{SUBSTRING}']",
            SEARCH_WITH_PARAM = "[{PARAM}='{VALUE}']",
            SEARCH_PAGES = "[aria-label='Page {PAGE_NUM}']",
            FIELDS_WITH_ERROR = "[class='invalid-feedback']",
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


    //API returns error when credit card number length not equals 16 method
    public void incorrectCardNumLenghtCheck(String cart_num)
    {
        RestAssured.baseURI = "https://fg1ap986e9.execute-api.eu-west-1.amazonaws.com/Dev";
        given()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"firstName\": \"Vlad\",\n" +
                        "    \"lastName\": \"etdfs\",\n" +
                        "    \"paymentMethod\": \"dsfd\",\n" +
                        "    \"cc-name\": \"dsfdf sdfsf\",\n" +
                        "    \"cc-number\": \""+cart_num+"\",\n" +
                        "    \"ccexpiration\": \"21.12\",\n" +
                        "    \"cc-cvv\": \"121\"\n" +
                        "}")
                .when()
                .post("/checkout")
                .then()
                .assertThat()
                .body("message", equalTo("Invalid Card Number"));
    }

    //API correctly calculates discount percent method
    public void correctDisc(String promo_code) {
        int discount_amount = promo_code.length();
        Assert.assertTrue("Promo code should be less than 100 symbols",promo_code.length()<100);
        RestAssured.baseURI = "https://fg1ap986e9.execute-api.eu-west-1.amazonaws.com/Dev";
        given()
                .header("Content-Type", "application/json")
                .when()
                .post("/coupon?coupon=" + promo_code)
                .then()
                .assertThat()
                .body("discount", equalTo(discount_amount));
    }

    //Fill all fields with correct data
    public void fillAllFields() {
        waitForElementAndTypeText("Johny", By.cssSelector(FIRST_NAME_FIELD), "First name field NOT appears", 15);
        waitForElementAndTypeText("Depp", By.cssSelector(LAST_NAME_FIELD), "Last name field NOT appears", 15);
        waitForElementAndTypeText("Johny Depp", By.cssSelector(CART_NAME_FIELD), "Cart name field NOT appears", 15);
        waitForElementAndTypeText("1234567891011121", By.cssSelector(CART_NUM_FIELD), "Cart number field NOT appears", 15);
        waitForElementAndTypeText("08.25", By.cssSelector(CART_EXP_FIELD), "Cart exp date field NOT appears", 15);
        waitForElementAndTypeText("123", By.cssSelector(CART_CCV_FIELD), "CCV field NOT appears", 15);
    }

    //Success text appears check after form submission method
    public void successTextAppears() {
        waitForElementPresent(By.cssSelector(SUCCESS_MESS), "Success text NOT appears", 15);
    }

    //Promo code changes Total check
    public void checkTotalAfterApplyingPromoCode(String promo_code) {
        int discount_amount = promo_code.length();
        String b = driver.findElement(By.cssSelector(TOTAL_AMOUNT)).getText();
        waitForElementAndTypeText(promo_code, By.cssSelector(PROMO_CODE_FIELD), "Promo code field was not detected", 15);
        waitElementContainsTextAndClick("Redeem");
        String a = driver.findElement(By.cssSelector(TOTAL_AMOUNT)).getText();

        float before_apply = Float.parseFloat(b);
        float after_apply = Float.parseFloat(a);
        float exp_float = before_apply - ((before_apply * discount_amount) / 100);
        Assert.assertTrue("Total amount after promo code apply should be '" + exp_float + "'", exp_float == after_apply);
    }

    //Fields error message visibility method
    public void allFieldsErrVisibilityCheck() {
        List<WebElement> errs = new ArrayList<>();
        errs = driver.findElements(By.cssSelector(FIELDS_WITH_ERROR));
        for (int i = 0; i < errs.size() - 1; i++) {
            Assert.assertTrue("Not all field has an error message", errs.get(i).isDisplayed());
        }
    }

    //Wait until cart load method
    public void waitCartLoad() {
        waitForElementNotPresent(By.cssSelector(CART_LOADING), "Cart was not loaded within 15 seconds", 15);
    }


    //Метод ожидания элемента
    public WebElement waitForElementPresent(By by, String err_mes, long timeoutInSec) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSec);
        wait.withMessage(err_mes + "\n");

        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    //Метод ожидания исчезания элемента
    public boolean waitForElementNotPresent(By by, String err_mes, long timeoutInSec) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSec);
        wait.withMessage(err_mes + "\n");

        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    //Метод ввода в поисковую строку google определенного текста
    public void waitForElementAndTypeText(String text, By by, String err_mes, long timeoutInSec) {
        WebElement field = waitForElementPresent(by, err_mes, timeoutInSec);
        field.clear();
        field.sendKeys(text);
    }

    //Метод ввода в поле с плейсхолдером определенного текста
    public void waitForElemenWithTextAndTypeText(String searched_text, String type_text) {
        WebElement element = waitForElementPresent(By.id("searchBox"), "Не найден элемент поиска", 15);
        element.click();
        element.clear();
        element.sendKeys(type_text);
    }

    //Метод нажатия на кнопку
    public void waitForElementPresentAndClick(By by, String err_mes, long timeoutInSec) {
        waitForElementPresent(by, err_mes, timeoutInSec).click();
    }

    //Метод поиска и клика элемента содержащего текст
    public void waitElementContainsTextAndClick(String text) {
        String searchedText = ELEMENT_CONTAINS_TEXT.replace("{SUBSTRING}", text);
        WebElement element = waitForElementPresent(By.xpath(searchedText), "Не найдено элементов с текстом " + text, 15);
        element.click();
    }

    //Метод поиска элемента содержащего текст
    public void waitElementContainsText(String text) {
        String searchedText = ELEMENT_CONTAINS_TEXT.replace("{SUBSTRING}", text);
        WebElement element = waitForElementPresent(By.xpath(searchedText), "Не найдено элементов с текстом " + text, 15);
    }

    //Метод поиска элемента содержащего текст
    public boolean waitElementContainsTextAssertion(String text) {
        String searchedText = ELEMENT_CONTAINS_TEXT.replace("{SUBSTRING}", text);
        WebElement element = waitForElementPresent(By.xpath(searchedText), "Не найдено элементов с текстом " + text, 15);
        return element.isDisplayed();
    }

    //Метод поиска элемента определенный параметр и значение
    public void searchWithParam(String param, String value) {
        String loc = SEARCH_WITH_PARAM.replace("{PARAM}", param).replace("{VALUE}", value);
        WebElement element = waitForElementPresent(By.cssSelector(loc), "Не найдено элементов с параметром '" + param + "' и значением '" + value + "'", 15);
    }

    //Метод возврата появления элемента содержащего текст
    public boolean searchWithParamAssertion(String param, String value) {
        String loc = SEARCH_WITH_PARAM.replace("{PARAM}", param).replace("{VALUE}", value);
        WebElement element = waitForElementPresent(By.cssSelector(loc), "Не найдено LIVE рейсов", 15);
        return element.isDisplayed();
    }

    //Метод поиска элемента по классу
    public void waitElementByClass(String class_name) {
        WebElement element = waitForElementPresent(By.cssSelector(class_name), "Не найдено элементов с class " + class_name, 15);
    }

    //Метод возврата элемента содержащего текст
    public WebElement returnElementContainsText(String text) {
        String searchedText = ELEMENT_CONTAINS_TEXT.replace("{SUBSTRING}", text);
        WebElement element = waitForElementPresent(By.xpath(searchedText), "Не найдено элементов с текстом " + text, 15);
        return element;
    }

    //Метод находит все элементы c URL начинающимся на
    public List<WebElement> findAllElementsWithUrlStartsWith(String text) {
        String searchedUrl = URL_STARTS_WITH.replace("{SUBSTRING}", text);
        List<WebElement> elements = driver.findElements(By.cssSelector(searchedUrl));
        return elements;
    }

    //Метод перехода на определенную страницу поиска
    public void goToPage(Integer pageNum) {
        String num = pageNum.toString();
        String pageXpath = SEARCH_PAGES.replace("{PAGE_NUM}", num);
        WebElement element = waitForElementPresent(By.cssSelector(pageXpath), "Не найдено номера страницы '" + pageNum + "'", 15);
        element.click();
    }

    //Метод перехода к мониторингу определенного live рейса
    public void goToLiveFlight(String param, String value) {
        String loc = SEARCH_WITH_PARAM.replace("{PARAM}", param).replace("{VALUE}", value);
        WebElement element = waitForElementPresent(By.cssSelector(loc), "Не найдено элементов с параметром '" + param + "' и значением '" + value + "'", 15);
        if (element.isDisplayed()) {
            List<WebElement> lis = driver.findElements(By.cssSelector(loc));
            lis.get(0).click();
        }
    }

    //Метод поиска определенного live рейса на flightRadar
    public void searchForFlight(String flt) {
        waitElementContainsTextAndClick("Flightradar24: Live Flight Tracker");
        if (waitElementContainsTextAssertion("Accept Cookies")) {
            waitElementContainsTextAndClick("Accept Cookies");
        }
        System.out.println("Ищу рейс '" + flt + "'");
        waitForElemenWithTextAndTypeText("Search", flt);
        boolean is_live = searchWithParamAssertion("data-testid", "search__result-live__go-to-live");
        Assert.assertTrue("Рейс '" + flt + "' не в воздухе", is_live);
        goToLiveFlight("data-testid", "search__result-live__go-to-live");
    }

}
