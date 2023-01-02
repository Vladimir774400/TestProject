package tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import pageObjects.MainPageObject;
import utils.InitClass;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static pageObjects.MainPageObject.AMAZON_DESC;
import static pageObjects.MainPageObject.GOOGLE_SEARCH_PAGES;

@DisplayName("Google tests")
public class FirstTest extends InitClass {
    MainPageObject mainPageObject = new MainPageObject(setUpChromeDriver());

    @Test
    @DisplayName("Google search test")
    public void googleSearchUiTest() throws InterruptedException {
        openMainUrl();
        mainPageObject.searchInGoogle("Java", 20);
        mainPageObject.scrollCustomTimes(2);
//        mainPageObject.clickOnSearchResult(2);
        Thread.sleep(3_000);
    }

    @Test
    @DisplayName("Max page amount test")
    public void maxPageCountTest() throws InterruptedException {
        openMainUrl();
        mainPageObject.openCustomPage("https://www.amazon.com/Beats-Studio-Cancelling-Earbuds-Built-Bluetooth-Headphones/dp/B09WQ1FB5R?ref_=Oct_DLandingS_D_f56073f1_66");
        mainPageObject.soutElementText(By.cssSelector(AMAZON_DESC));
        Thread.sleep(3_000);
    }

    @Test
    @DisplayName("On this day events test")
    public void googleSearchApiTest() {
        Calendar calendar = Calendar.getInstance();
        String year = new SimpleDateFormat("yyyy").format(calendar.getTime());
        String month = new SimpleDateFormat("MM").format(calendar.getTime());
        String day = new SimpleDateFormat("dd").format(calendar.getTime());

        Response resp = mainPageObject.sendGetRequest("https://en.wikipedia.org/api/rest_v1/feed/featured/"+year+"/"+month+"/"+day+"", 200);
//        resp.body().print();
        Map<Integer,String> onThisDay = new HashMap<>();
        List<Integer> years = resp.body().path("onthisday.year");
        List<String> texts = resp.body().path("onthisday.text");

        for (int i = 0; i<years.size(); i++) {
            onThisDay.put(years.get(i),texts.get(i));
        }

        System.out.println("On this day:");
        System.out.println(onThisDay);
    }
}
