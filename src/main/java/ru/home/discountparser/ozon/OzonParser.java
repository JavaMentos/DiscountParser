package ru.home.discountparser.ozon;

import java.io.File;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import org.openqa.selenium.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.home.discountparser.selenium.Selenium;

@Component
public class OzonParser {
    public static CopyOnWriteArrayList<Ozon> listOzons = new CopyOnWriteArrayList<>();
    @Autowired
    private Selenium selenium;

    static {
        Ozon ozon = new Ozon();
        ozon.setUrlGoods("https://www.ozon.ru/product/samokat-detskiy-mid-5-s-ruchnym-tormozom-i-podveskoy-oxelo-h-decathlon-910251634/?oos_search=false&sh=I15BOSDFqg");
        listOzons.add(ozon);

        Ozon ozon1 = new Ozon();
        ozon1.setUrlGoods("https://www.ozon.ru/product/shlem-bokserskiy-900-otkrytyy-dlya-vzroslyh-outshock-h-decathlon-chernyy-179523469/?oos_search=false&sh=I15BORL6kA");
        listOzons.add(ozon1);

        Ozon ozon2 = new Ozon();
        ozon2.setUrlGoods("https://www.ozon.ru/product/samokat-detskiy-mid-5-s-ruchnym-tormozom-i-podveskoy-oxelo-h-decathlon-910251634/?oos_search=false&sh=I15BOSDFqg");
        listOzons.add(ozon2);

        Ozon ozon3 = new Ozon();
        ozon3.setUrlGoods("https://www.ozon.ru/product/trenazher-dlya-doma-900-corength-decathlon-639989756/?oos_search=false&sh=I15BOVswUg");
        listOzons.add(ozon3);

        Ozon ozon4 = new Ozon();
        ozon3.setUrlGoods("https://www.ozon.ru/product/silovaya-stantsiya-training-station-900-domyos-h-decathlon-172946373/?oos_search=false&sh=I15BOYe1DQ");
        listOzons.add(ozon4);

        Ozon ozon5 = new Ozon();
        ozon3.setUrlGoods("https://www.ozon.ru/product/kruizer-cherno-zelenyy-yamba-900-oxelo-h-decathlon-179049667/?oos_search=false&sh=I15BOZeJZw");
        listOzons.add(ozon5);

        Ozon ozon6 = new Ozon();
        ozon3.setUrlGoods("https://www.ozon.ru/product/kruizer-cherno-zelenyy-yamba-900-oxelo-h-decathlon-179049667/?oos_search=false&sh=I15BOZeJZw");
        listOzons.add(ozon6);

        Ozon ozon7 = new Ozon();
        ozon3.setUrlGoods("https://www.ozon.ru/product/grusha-bokserskaya-i-perchatki-detskie-4-untsii-outshock-h-decathlon-175261325/?oos_search=false&sh=I15BORk8Dw");
        listOzons.add(ozon7);

        Ozon ozon8 = new Ozon();
        ozon3.setUrlGoods("https://www.ozon.ru/product/stepper-ms500-domyos-h-decathlon-185621697/?oos_search=false&sh=I15BOZK8tQ");
        listOzons.add(ozon8);

        Ozon ozon9 = new Ozon();
        ozon3.setUrlGoods("https://www.ozon.ru/product/pedali-dlya-gornogo-velosipeda-free-ride-alyuminievye-rockrider-h-dekatlon-175089029/?oos_search=false&sh=I15BOekxEA");
        listOzons.add(ozon9);
    }

    public void checkAvailabilityOfGoods() {
        if(OzonParser.listOzons.size() == 0) return;

        Iterator<Ozon> iterator = OzonParser.listOzons.iterator();
        while (iterator.hasNext()) {

            selenium.runSelenium();

            Ozon ozon = iterator.next();

            selenium.insertUrlInDriver(ozon.getUrlGoods());

            selenium.timeUnitSleep(5);

            boolean AvailableGoods = isGoodsUnavailable();

            if (AvailableGoods) {
                File file = takeScreenshot();
                if (file != null) {
                    ozon.setScreenShot(file);
                    ozon.setGoodsAvailable(true);
                }
            }
            selenium.terminateSelenium();
        }

    }

    private boolean isGoodsUnavailable() {
        try {
//            selenium.getElementByClassName("zm9");
            selenium.getElementByXpath("//h2[1][contains(text(),'Этот товар закончился')]");
            return false;
        } catch (NoSuchElementException e) {
            return true;
        }

    }

    private File takeScreenshot() {
        try {
            WebElement element = selenium.getElementByXpath("//div[1][@data-widget='stickyContainer']");
//            WebElement element = selenium.getElementByXpath("//*[@id='layoutPage']/div[1]/div[4]/div[3]/div[1]/div[1]/div[1]/div/div[2]");
//            WebElement element = selenium.getElementByXpath("//*[@id='layoutPage']/div[1]/div[5]/div[3]/div[1]/div[1]/div[1]/div/div[2]/div/div[1]/div[1]/div/img");
            return element.getScreenshotAs(OutputType.FILE);
        } catch (TimeoutException | NoSuchElementException e) {
            selenium.terminateSelenium();
            return null;
        }
    }

}
