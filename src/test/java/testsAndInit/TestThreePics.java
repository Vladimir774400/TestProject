package testsAndInit;

import org.junit.Test;
import pageObjects.SearchPageObject;

public class TestThreePics extends testsAndInit.Initialize {
//      ��������
//    - ���������������� ������������ ������� � https://www.google.com/
//    - ���� ivi
//    - ��������� � ��������
//    - �������� �������
//    - ����������, ��� �� ����� 3 �������� � ������ ����� �� ���� ivi.ru

    @Test
    public void testThreePics() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.searchInGoogle("ivi");
        searchPageObject.goToPicturesTab();
        searchPageObject.selectBigPics("�������");
        searchPageObject.asserThatThreePicsContainsUrl();
    }
}
