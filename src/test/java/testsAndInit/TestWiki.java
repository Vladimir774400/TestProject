package testsAndInit;

import org.junit.Test;
import pageObjects.SearchPageObject;

public class TestWiki extends testsAndInit.Initialize {
//      ��������
//    - ���������������� ������������ ������� � https://www.google.com/
//    - ���� ivi
//    - �� ������ 5 ��������� ������� ������ �� ������ � wikipedia �� ivi
//    - ����������, ��� � ������ ���� ������ �� ����������� ���� ivi.ru

    @Test
    public void testWiki() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.searchInGoogle("ivi");
        searchPageObject.searchWikiPageAboutIvi();
    }
}

