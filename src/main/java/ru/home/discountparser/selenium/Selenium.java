package ru.home.discountparser.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class Selenium {

    private ChromeOptions options;
    private WebDriver driver;
    @Value("${selenium.headless.mode}")
    private boolean headlessMode;
    private int timeoutInSeconds = 30;
    private int dimensionWidth = 1600;
    private int dimensionHeight = 1200;

    public void runSelenium() {
        WebDriverManager.chromedriver().setup();
        options = new ChromeOptions();
        options.setHeadless(headlessMode);

        options.setImplicitWaitTimeout(Duration.ofSeconds(timeoutInSeconds));
        options.setPageLoadTimeout(Duration.ofSeconds(timeoutInSeconds));
        options.setScriptTimeout(Duration.ofSeconds(timeoutInSeconds));

        options.addArguments("start-maximized");
        options.addArguments("disable-infobars");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
        options.addArguments("--no-sandbox");
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);

        //разрешение окна
        Dimension dim = new Dimension(dimensionWidth, dimensionHeight);

        driver.manage().window().setSize(dim);
    }

    public void insertUrlInDriver(String url) {
        driver.get(url);
    }

    public WebElement getElementByXpath(String xpath) {
        return driver.findElement(By.xpath(xpath));
    }

    public WebElement getElementByClassName(String className) {
        return driver.findElement(By.className(className));
    }

    public void terminateSelenium() {
        driver.quit();
    }

    public void timeUnitSleep(int second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
