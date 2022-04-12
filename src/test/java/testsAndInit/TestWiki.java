package testsAndInit;

import org.junit.Test;
import pageObjects.SearchPageObject;

public class TestWiki extends testsAndInit.Initialize {
//      Сценарий
//    - неавторизованный пользователь заходит в https://www.google.com/
//    - ищет ivi
//    - на первых 5 страницах находит ссылку на статью в wikipedia об ivi
//    - убеждается, что в статье есть ссылка на официальный сайт ivi.ru

    @Test
    public void testWiki() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.searchInGoogle("ivi");
        searchPageObject.searchWikiPageAboutIvi();
    }
}

