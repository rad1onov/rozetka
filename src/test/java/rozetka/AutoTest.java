package rozetka;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AutoTest {

    @Test
    public void search () throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\webdrivers\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://rozetka.com.ua/");
        driver.manage().window().maximize(); //разворачиваем браузре на весь экран

        driver.findElement(By.name("search")).click(); //находим поиск
        driver.findElement(By.name("search")).sendKeys("iphone"); //вводим iphone
        driver.findElement(By.name("search")).sendKeys(Keys.ENTER); //нажимаем энтер

        WebDriverWait wait = new WebDriverWait(driver, 10);
        //ожидание в 10 секунд для поиска эдемента
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[href=\"/mobile-phones/c80003/producer=apple/\"]")));
        //убеждаемся что на странице есть фильт эпл(только так получилось)

       ExpectedConditions.visibilityOfElementLocated(By.linkText("Iphone"));
       //убеждаемся в наличие поискового запроса по айфонам

        driver.findElement(By.xpath("//app-root//rz-category//ctg-catalog//section/ctg-grid/ul[@class='catalog-grid']/li[1]")).click();
        //кликаем на первый вариант с результата поиска , где [1] это обозначения варианта , так же работает и с другими числами .

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("product__title")));
        //убеждаемся что открылась страница с товаром

        Assert.assertNotNull(driver.findElement(By.className("product-photo__picture")));
        //убеждаемся что есть фото товара

        WebElement tabs = driver.findElement(By.className("product-tabs__list"));
        //находим элемент в котором содержится кнопка Отзывы
        tabs.findElement(By.partialLinkText("Отзывы")).click();
        //переходим на кнопку Отзывы.Но если отзывов нет , то тест упадет , т.к там будет пункт"Оставить отзывы"

        Assert.assertNotNull(driver.findElement(By.className("product-tabs__heading")));
        //убеждаемся что есть отзывы

        tabs.findElement(By.partialLinkText("Все о товаре")).click();
        //переходим обратно на пунк "Все о товаре "

        driver.findElement(By.xpath("/html/body/app-root//app-rz-product//product-tab-main//product-main-info//app-buy-button[@class='product__buy']/button[@type='button']")).click();
        //нажимаем на "Купить "

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("cart-modal__calc")));
        //убеждаемся что открылось всплывающее окно Корзина (может страный способ )

        driver.findElement(By.xpath("/html//app-root//cart-modal/modal-window[@class='js-cart-header']//div[@class='modal-inner']/div/button[@type='button']")).click();
        //закрываем окно
        Thread.sleep(1000); //делаем таймаут (без него иногда падал тест)

        driver.findElement(By.cssSelector("[href='https\\:\\/\\/my\\.rozetka\\.com\\.ua\\/profile\\/cart']")).click();
        //переходим в корзину

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("cart-modal__item")));
        //убеждаемся что товар был добавлен в корзину

        driver.quit(); // закрываем браузер
    }
}
