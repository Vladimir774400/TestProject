package testsAndInit;

import org.junit.Test;
import pageObjects.SearchPageObject;

public class TestGooglePlay extends testsAndInit.Initialize {
//      ��������
//    - ���������������� ������������ ������� � https://www.google.com/
//    - ���� ivi
//    - �� ������ 5 ��������� ������� ������ �� ���������� ivi � play.google.com
//    - ����������, ��� ������� ���������� �� ������� �������� �������� ��������� � ��������� ��� ��������

    @Test
    public void testGooglePlay() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.getSomeAttrByClass("lnXdpd", "src");
    }
}
