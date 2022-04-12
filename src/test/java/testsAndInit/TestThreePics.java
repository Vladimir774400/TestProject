package testsAndInit;

import org.junit.Test;
import pageObjects.SearchPageObject;

public class TestThreePics extends testsAndInit.Initialize {
//      —ценарий
//    - неавторизованный пользователь заходит в https://www.google.com/
//    - ищет ivi
//    - переходит в картинки
//    - выбирает большие
//    - убеждаетс€, что не менее 3 картинок в выдаче ведут на сайт ivi.ru

    @Test
    public void testThreePics() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.searchInGoogle("ivi");
        searchPageObject.goToPicturesTab();
        searchPageObject.selectBigPics("Ѕольшой");
        searchPageObject.asserThatThreePicsContainsUrl();
    }
}
