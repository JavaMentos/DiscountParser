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

    {
        Ozon ozon = new Ozon();
        ozon.setUrlGoods("https://www.ozon.ru/product/trenazher-dlya-doma-900-corength-decathlon-639989756/?oos_search=false&sh=4zzF_RL_dQ");
        listOzons.add(ozon);

        Ozon ozon1 = new Ozon();
        ozon1.setUrlGoods("https://www.ozon.ru/product/mikrovolnovaya-pech-pioneer-20-l-s-led-displeem-8-avtomaticheskih-programm-5-urovney-357362808/?avtc=1&avte=4&avts=1680034586&sh=5ovwXqiMgA");
        listOzons.add(ozon1);

        Ozon ozon2 = new Ozon();
        ozon2.setUrlGoods("https://www.ozon.ru/search/?deny_category_prediction=true&from_global=true&text=%D0%A1%D1%82%D0%B5%D0%BF%D0%BF%D0%B5%D1%80&product_id=185621697");
        listOzons.add(ozon2);

        Ozon ozon3 = new Ozon();
        ozon3.setUrlGoods("https://www.ozon.ru/product/robot-pylesos-pioneer-vc701r-chernyy-268661572/?advert=DoZ7ASy48O13FEmtST7Cf8WT7FJ6Q-myXSqlitGtJJRPbPB1Pv4GsC_MfvyrGVAz-LFrvj1CknChq9MQ8RMohcrmbwmdGA2naee4I_fKxl7gANVBFx-W3wdeg8Kv4OvHjUFg0-Qq5YjGYB-6JwLO27EbJawh99iBDLt4LW6i3NZi-zUEUSBtX11UWo4&avtc=1&avte=2&avts=1680035945&sh=5ovwXlzWmQ");
        listOzons.add(ozon3);

    }

    public void checkAvailabilityOfGoods() {
        Iterator<Ozon> iterator = OzonParser.listOzons.iterator();
        while (iterator.hasNext()) {

            selenium.runSelenium();

            Ozon ozon = iterator.next();

            selenium.insertUrlInDriver(ozon.getUrlGoods());

            selenium.timeUnitSleep(5);

            boolean AvailableGoods = isGoodsUnavailable();

            if (!AvailableGoods) {
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
            selenium.getElementByClassName("zm9");
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }

    }

    private File takeScreenshot() {
        try {
            WebElement element = selenium.getElementByXpath("//*[@id='layoutPage']/div[1]/div[5]/div[3]/div[1]/div[1]");
//            WebElement element = selenium.getElementByXpath("//*[@id='layoutPage']/div[1]/div[5]/div[3]/div[1]/div[1]/div[1]/div/div[2]/div/div[1]/div[1]/div/img");
            return element.getScreenshotAs(OutputType.FILE);
        } catch (TimeoutException | NoSuchElementException e) {
            selenium.terminateSelenium();
            return null;
        }
    }

}
