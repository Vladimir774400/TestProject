package testsAndInit;

import org.junit.Test;
import pageObjects.SearchPageObject;

public class TestWiki extends testsAndInit.Initialize {
//      Ñöåíà?èé
//    - íåàâòî?èçîâàííûé ïîëüçîâàòåëü çàõîäèò â https://www.google.com/
//    - èùåò ivi
//    - íà ïå?âûõ 5 ñò?àíèöàõ íàõîäèò ññûëêó íà ñòàòü? â wikipedia îá ivi
//    - óáåæäàåòñÿ, ÷òî â ñòàòüå åñòü ññûëêà íà îôèöèàëüíûé ñàéò ivi.ru

    @Test
    public void testWiki() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.searchInGoogle("ivi");
        searchPageObject.searchWikiPageAboutIvi();
    }
}

