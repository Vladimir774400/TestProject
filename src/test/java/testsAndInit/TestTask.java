package testsAndInit;

import org.junit.Test;
import pageObjects.MainPageObject;



public class TestTask extends testsAndInit.Initialize{
    @Test
    public void testErrFieldsAppears() {
        MainPageObject mainPageObject = new MainPageObject(driver);
        mainPageObject.waitElementContainsTextAndClick("Continue to checkout");
        mainPageObject.allFieldsErrVisibilityCheck();
    }

    @Test
    public void testCartSuccessfullyLoad() {
        MainPageObject mainPageObject = new MainPageObject(driver);
        mainPageObject.waitCartLoad();
    }

    @Test
    public void testPromoCodeCalculatedCorrectly() {
        MainPageObject mainPageObject = new MainPageObject(driver);
        mainPageObject.waitCartLoad();
        mainPageObject.checkTotalAfterApplyingPromoCode("1234567891");
    }

    @Test
    public void testOrderSubmitCheck() {
        MainPageObject mainPageObject = new MainPageObject(driver);
        mainPageObject.waitCartLoad();
        mainPageObject.fillAllFields();
        mainPageObject.successTextAppears();
    }

    @Test
    public void testApiCorrectDiscountAmount() {
        MainPageObject mainPageObject = new MainPageObject(driver);
        mainPageObject.correctDisc("promocode");
    }

    @Test
    public void testApiErrorWithCartNumLenth() {
        MainPageObject mainPageObject = new MainPageObject(driver);
        mainPageObject.incorrectCardNumLenghtCheck("123456789");
    }
}
