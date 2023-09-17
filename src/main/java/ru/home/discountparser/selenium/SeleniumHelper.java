package ru.home.discountparser.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Класс-помощник для работы с Selenium WebDriver. Предоставляет методы для
 * инициализации и управления WebDriver, а также для взаимодействия с элементами
 * на странице.
 */
@Service
@Log4j2
public class SeleniumHelper {

    private ChromeOptions options;
    private WebDriver driver;
    @Value("${selenium.headless.mode}")
    private boolean headlessMode;
    private final int timeoutInSeconds = 10;
    private final int dimensionWidth = 1600;
    private final int dimensionHeight = 1200;

    public WebDriver getDriver() {
        return driver;
    }

    /**
     * Инициализирует и запускает WebDriver с заданными параметрами.
     */
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

        // Установка размера окна
        Dimension dim = new Dimension(dimensionWidth, dimensionHeight);
        driver.manage().window().setSize(dim);
    }

    /**
     * Переходит на указанный URL.
     *
     * @param url адрес страницы для перехода.
     */
    public void navigate(String url) {
        driver.get(url);
    }

    /**
     * Получает элемент на странице по XPath.
     *
     * @param xpath XPath-выражение для поиска элемента.
     * @return найденный элемент на странице.
     */
    public WebElement getElementByXpath(String xpath) {
        return driver.findElement(By.xpath(xpath));
    }

    /**
     * Получает элемент на странице по имени класса.
     *
     * @param className имя класса элемента для поиска.
     * @return найденный элемент на странице.
     */
    public WebElement getElementByClassName(String className) {
        return driver.findElement(By.className(className));
    }

    /**
     * Завершает работу WebDriver.
     */
    public void terminateSelenium() {
        driver.quit();
    }

    /**
     * Задерживает выполнение текущего потока на указанное количество секунд.
     *
     * @param second количество секунд для задержки.
     */
    public void sleep(int second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            log.error(e.getMessage(),e);
        }
    }
}
