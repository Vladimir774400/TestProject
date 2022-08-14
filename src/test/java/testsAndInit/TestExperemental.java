package testsAndInit;

import org.junit.Test;
import pageObjects.MainPageObject;
import pageObjects.SearchPageObject;

public class TestExperemental extends testsAndInit.Initialize{
    @Test
    public void testExperemental() {
        MainPageObject mainPageObject = new MainPageObject(driver);
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.searchInGoogle("flightradar24");
        mainPageObject.searchForFlight("AFL042");

    }
}
