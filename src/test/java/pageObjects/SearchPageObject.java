package pageObjects;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import pageObjects.MainPageObject;

import java.util.List;

public class SearchPageObject extends MainPageObject {
    protected ChromeDriver driver;

    public SearchPageObject(ChromeDriver driver) {
        super(driver);
        this.driver = driver;
    }

    //Локаторы
    private static final String
            SEARCH_FIELD = "/html/body/div[1]/div[3]/form/div[1]/div[1]/div[1]/div/div[2]/input",
            SEARCH_BTN = "/html/body/div[1]/div[3]/form/div[1]/div[1]/div[2]/div[2]/div[5]/center/input[1]",
            RESULTS_PAGE = "//*[@id='result-stats']",
            PICTURES_DIV = "//*[@id='islrg']",
            APP_STORE_RATING = "/html/body/div[5]/main/div[2]/section[1]/div/div[2]/header/ul[1]/li[2]/ul/li/figure/figcaption",
            WIKI_CONTENT_CONTAINER = "mw-content-text";

    //Методы
    //Метод поиска через google
    public void searchInGoogle(String text) {
        waitForElementAndTypeText(text, By.xpath(SEARCH_FIELD), "Поле ввода не найдено", 10);
        waitForElementPresentAndClick(By.xpath(SEARCH_BTN), "Кнопка 'Поиск' НЕ найдена", 10);
        waitForElementPresent(By.xpath(RESULTS_PAGE), "Результаты по запросу '" + text + "' не найдены", 10);
    }

    //Метод переходит в категорию "Картинки"
    public void goToPicturesTab() {
        waitElementContainsTextAndClick("Картинки");
        waitForElementPresent(By.xpath(PICTURES_DIV), "Окно картинок не отображается", 10);
    }

    //Метод выбирает только большие картинки
    public void selectBigPics(String size) {
        waitElementContainsTextAndClick("Инструменты");
        waitElementContainsTextAndClick("Размер");
        waitElementContainsTextAndClick(size);
        waitForElementPresent(By.xpath(PICTURES_DIV), "Окно картинок не отображается", 10);
    }

    //Метод проверяет что не менее 3х картинок ведут на сайт ivi.ru
    public void asserThatThreePicsContainsUrl() {
        String url = "https://www.ivi.ru";
        List<WebElement> elemsWithIvi = findAllElementsWithUrlStartsWith(url);
        System.out.println("Найдено " + elemsWithIvi.size() + " элемента(ов) со ссылкой '" + url + "'");
        Assert.assertTrue("Элементов ведущих на сайти ivi меньше трех", elemsWithIvi.size() > 3);
    }

    //Метод ищет ссылки на appStore на первых пяти страницах и сравнивает рейтинг
    public void searchAppStoreAndCheckRate() {
        // Цикл итерирования страниц
        for (int i = 2; i <= 5; i++) {
            // Стринга рейтинга в appStore
            String ratingFromAppStore;
            // Стринга рейтинга который отображается в поиске
            String ratingFromSearchPage;
            // Стринга урла который будем искать по страницам
            String url = "https://apps.apple.com/ru/app/ivi";
            //С помощью метода findAllElementsWithUrlStartsWith соберем все локаторы со ссылкой на ivi и запихнем в List
            List<WebElement> elemsWithUrl = findAllElementsWithUrlStartsWith(url);
            // Просто для статистики выводим сколько ссылок на каждой из 5 страниц
            System.out.println("Найдено " + elemsWithUrl.size() + " элемента(ов) со ссылкой '" + url + "' на странице '" + i + "'");
            // Если линки есть на странице пойжем в цикл для каждого элемента листа
            if (elemsWithUrl.size() > 0) {
                for (WebElement element : elemsWithUrl) {
                    // Поднимаемся выше по DOM ищем спан который содержит рейтинг в найденом блоке со ссылкой и сохраняем его значение в ratingFromSearchPage
                    ratingFromSearchPage = elemsWithUrl
                            .get(0)
                            .findElement(By.xpath("..//..//..//span[contains(text(), 'Рейтинг')]"))
                            .getText()
                            .replace("Рейтинг: ", "");
                    // Кликаем по ссылке
                    element.click();
                    // Ждем пока прогрузится элемент с рейтингом в appStore
                    waitForElementPresent(By.xpath(APP_STORE_RATING), "Не найден элемент рейтинга в appStore", 10);
                    // Тк там много цифр и букв, цифры рейтинга идут первыми тремя символами, просто сплитаем строку по 3 и берем 0 элемент
                    String[] arr = driver.findElement(By.xpath(APP_STORE_RATING)).getText().replaceAll("(.{3})", "$1 ").split(" ");
                    // Запихиваем это значение в ratingFromAppStore
                    ratingFromAppStore = arr[0];
                    // Стравниваем ratingFromAppStore и ratingFromSearchPage
                    Assert.assertTrue("Рейтитнг в appStore: '" + ratingFromAppStore + "' Рейтинг на странице поиска '" + ratingFromSearchPage + "' НЕ СОВПАДАЮТ!"
                            , ratingFromAppStore.equals(ratingFromSearchPage));
                    // Жмакаем назад
                    driver.navigate().back();
                }
            }
            // Идем на след страницу и делаем все тож самое
            goToPage(i);
        }
    }

    //Метод ищет ссылки на appStore на первых пяти страницах и сравнивает рейтинг
    public void searchWikiPageAboutIvi() {
        // Цикл итерирования страниц
        for (int i = 2; i <= 5; i++) {
            String url = "https://ru.wikipedia.org/wiki/Ivi.ru";
            //С помощью метода findAllElementsWithUrlStartsWith соберем все локаторы со ссылкой на страницу wiki и запихнем в List
            List<WebElement> elemsWithUrl = findAllElementsWithUrlStartsWith(url);
            // Просто для статистики выводим сколько ссылок на каждой из 5 страниц
            System.out.println("Найдено " + elemsWithUrl.size() + " элемента(ов) со ссылкой '" + url + "' на странице '" + i + "'");
            if (elemsWithUrl.size() > 0) {
                for (WebElement element : elemsWithUrl) {
                    try {
                        element.click();
                    } catch (org.openqa.selenium.ElementClickInterceptedException e) {
                        // В случае если элемент закрывает навбар (как это произошло у меня), JS скрипт скроллит элемент на середину
                        String scrollElementIntoMiddle = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
                                + "var elementTop = arguments[0].getBoundingClientRect().top;"
                                + "window.scrollBy(0, elementTop-(viewPortHeight/2));";

                        ((JavascriptExecutor) driver).executeScript(scrollElementIntoMiddle, element);
                        element.click();
                    }
                    waitForElementPresent(By.id(WIKI_CONTENT_CONTAINER), "Контент статьи не прогружзился", 10);
                    List<WebElement> elemsWithIviUrl = findAllElementsWithUrlStartsWith("https://www.ivi.ru");
                    Assert.assertTrue("На странице wiki не найдены ссылки на ivi", elemsWithIviUrl.size() > 0);
                    driver.navigate().back();
                }
            }
            goToPage(i);
        }
    }
}
