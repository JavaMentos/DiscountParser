package ru.home.discountparser.ozon;

import java.io.File;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import org.openqa.selenium.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.home.discountparser.selenium.SeleniumHelper;

/**
 * Класс OzonParser отвечает за проверку наличия товаров на сайте Ozon
 * с использованием Selenium.
 */
@Component
public class OzonParser {
    public static CopyOnWriteArrayList<Ozon> ozonProducts = new CopyOnWriteArrayList<>();
    @Autowired
    private SeleniumHelper seleniumHelper;

    /**
     * Проверяет наличие товаров на сайте Ozon и сохраняет состояние наличия
     * и скриншоты для товаров, которые стали доступными.
     */
    public void checkAvailabilityOfGoods() {
        if (OzonParser.ozonProducts.size() == 0) return;

        Iterator<Ozon> iterator = OzonParser.ozonProducts.iterator();
        while (iterator.hasNext()) {
            seleniumHelper.runSelenium();

            Ozon ozon = iterator.next();

            seleniumHelper.navigate(ozon.getProductUrl());

            seleniumHelper.sleep(5);

            boolean isProductAvailable = isProductUnavailable();

            if (isProductAvailable) {
                File screenshot = takeScreenshot();
                if (screenshot != null) {
                    ozon.setScreenShot(screenshot);
                    ozon.setAvailable(true);
                }
            }
            seleniumHelper.terminateSelenium();
        }
    }

    /**
     * Проверяет, закончился ли товар на сайте Ozon.
     *
     * @return true, если товар доступен; false, если товар закончился
     */
    private boolean isProductUnavailable() {
        try {
            // Поиск элемента, который отображается, когда товар закончился
            seleniumHelper.getElementByXpath("//h2[1][contains(text(),'Этот товар закончился')]");
            return false;
        } catch (NoSuchElementException e) {
            return true;
        }
    }

    /**
     * Делает скриншот элемента товара на сайте Ozon.
     *
     * @return файл скриншота или null, если скриншот не может быть сделан
     */
    private File takeScreenshot() {
        try {
            // Поиск элемента товара для создания скриншота
            WebElement element = seleniumHelper.getElementByXpath("//div[1][@data-widget='stickyContainer']");
            return element.getScreenshotAs(OutputType.FILE);
        } catch (TimeoutException | NoSuchElementException e) {
            seleniumHelper.terminateSelenium();
            return null;
        }
    }

}
