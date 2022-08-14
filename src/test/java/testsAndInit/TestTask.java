package testsAndInit;

import org.junit.Test;
import pageObjects.MainPageObject;



public class TestTask extends testsAndInit.Initialize{
    //All mandatory empty fields should have error description displayed after ‘Continue to checkout’ is pressed
    @Test
    public void testErrFieldsAppears() {
        MainPageObject mainPageObject = new MainPageObject(driver);
        mainPageObject.waitElementContainsTextAndClick("Continue to checkout");
        mainPageObject.allFieldsErrVisibilityCheck();
    }

    //‘Cart’ successfully loaded when user opens the page;
    @Test
    public void testCartSuccessfullyLoad() {
        MainPageObject mainPageObject = new MainPageObject(driver);
        mainPageObject.waitCartLoad();
    }

    //Promo code correctly changes ‘Total’ field
    @Test
    public void testPromoCodeCalculatedCorrectly() {
        MainPageObject mainPageObject = new MainPageObject(driver);
        mainPageObject.waitCartLoad();
        mainPageObject.checkTotalAfterApplyingPromoCode("1234567891");
    }

    //‘Your order was placed’ displayed after form submission
    @Test
    public void testOrderSubmitCheck() {
        MainPageObject mainPageObject = new MainPageObject(driver);
        mainPageObject.waitCartLoad();
        mainPageObject.fillAllFields();
        mainPageObject.successTextAppears();
    }

    //API correctly calculates discount percent
    @Test
    public void testApiCorrectDiscountAmount() {
        MainPageObject mainPageObject = new MainPageObject(driver);
        mainPageObject.correctDisc("promocode");
    }

    //API returns error when credit card number length not equals 16
    @Test
    public void testApiErrorWithCartNumLenth() {
        MainPageObject mainPageObject = new MainPageObject(driver);
        mainPageObject.incorrectCardNumLenghtCheck("123456789");
    }
}
