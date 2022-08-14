package testsAndInit;

import org.junit.Test;
import pageObjects.SearchPageObject;

public class TestGooglePlay extends testsAndInit.Initialize {
//      Сценарий
//    - неавторизованный пользователь заходит в https://www.google.com/
//    - ищет ivi
//    - на первых 5 страницах находит ссылки на приложение ivi в play.google.com
//    - убеждается, что рейтинг приложения на кратком контенте страницы совпадает с рейтингом при переходе

    @Test
    public void testGooglePlay() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.getSomeAttrByClass("lnXdpd", "src");
    }
}
