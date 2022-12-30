package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pageObjects.MainPageObject;
import utils.InitClass;

@DisplayName("Google tests")
public class FirstTest extends InitClass {
    MainPageObject mainPageObject = new MainPageObject(setUpChromeDriver());

    @Test
    @DisplayName("Google search")
    public void firstTest() throws InterruptedException {
        openMainUrl();
        mainPageObject.searchInGoogle("Java", 20);
        mainPageObject.clickOnSearchResult(2);
        Thread.sleep(3_000);
    }
}
